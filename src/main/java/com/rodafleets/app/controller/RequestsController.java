package com.rodafleets.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.dataaccess.BidRepository;
import com.rodafleets.app.dataaccess.CustomerRepository;
import com.rodafleets.app.dataaccess.DriverRepository;
import com.rodafleets.app.dataaccess.RequestsRepository;
import com.rodafleets.app.model.Bid;
import com.rodafleets.app.model.Customer;
import com.rodafleets.app.model.Driver;
import com.rodafleets.app.model.VehicleRequest;
import com.rodafleets.app.response.CustomResponse;
import com.rodafleets.app.response.DriverResponse;
import com.rodafleets.app.service.FCMService;
import com.rodafleets.app.service.OTPService;
import com.rodafleets.app.util.RestUtils;

/*===================  Requests API  =================== */

@RestController    // This means that this class is a Controller
@RequestMapping(path="/" + AppConfig.API_VERSION + "/requests") // This means URL's start with /requests after API base url
public class RequestsController {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
	private CustomResponse jsonResponse;

	@Autowired // This means to get the bean called RequestsRepository
	private RequestsRepository requestsRepo;
	
	@Autowired
	private DriverRepository driverRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private BidRepository bidRepo;
	
	/*
	 * Retrive all drivers and their info
	 * @Url: localhost:8080/drivers
	 */
	@RequestMapping(value = "/{customer_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllRequestsByCustomer(@PathVariable("customer_id") long customerId) {
		return findAllRequestsByCustomer(customerId);
	}
	
	/*
	 * Retrive all drivers and their info
	 * @Url: localhost:8080/drivers
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveRequest(
			@RequestParam(value="customer_id") long customerId,
			@RequestParam(value="vehicletype_id") long vehicleTypeId,
			@RequestParam(value="origin_lat") double originLat,
			@RequestParam(value="origin_lng") double originLng,
			@RequestParam(value="destination_lat") double destinationLat,
			@RequestParam(value="destination_lng") double destinationLng,
			@RequestParam(value="loading_required", defaultValue="0") long loadingRequired,
			@RequestParam(value="unloading_required", defaultValue="0") long unloadingRequired,
			@RequestParam(value="approx_fare_in_cents") long approxFareInCents) {
		return addRequest(customerId, vehicleTypeId, originLat, originLng, destinationLat, destinationLng, loadingRequired, unloadingRequired, approxFareInCents);
	}
	
	@RequestMapping(value="/{request_id}/bid", method = RequestMethod.POST)
	public ResponseEntity<?> saveBid(
			@PathVariable("request_id") long requestId,
			@RequestParam(value="bid_amount_in_cents") long bidAmountInCents) {
		return addBid(requestId, bidAmountInCents);
	}
	
	private ResponseEntity<?> findAllRequestsByCustomer(long customerId) {
		jsonResponse = new CustomResponse();
		List<VehicleRequest> requests = requestsRepo.findRequestsByCustomerId(customerId);
		if(requests.isEmpty()) {
			logger.info("search returned empty");
			jsonResponse.setCode(HttpStatus.NO_CONTENT.value());
			jsonResponse.setMessage("No requests from this customer");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}	    
		return new ResponseEntity<List>(requests, HttpStatus.CREATED);
	}

	private ResponseEntity<?> addRequest(long vehicleTypeId, long customerId, double originLat, double originLng,
			double destinationLat, double destinationLng, long loadingRequired, long unloadingRequired, long approxFareInCents) {
		jsonResponse = new CustomResponse();
		try {
			String distanceStr = "";
			VehicleRequest request = new VehicleRequest(customerId, vehicleTypeId, originLat, originLng, destinationLat, destinationLng, loadingRequired, unloadingRequired, approxFareInCents);
			
			RestUtils utils = new RestUtils();
			JSONObject apiResponse = utils.distance(originLat, originLng, destinationLat, destinationLng );
			if(apiResponse.getString("status").equalsIgnoreCase("OK")) {
				//return the first calculated route info for approx fare estimate
				JSONObject distanceMatrixObj = apiResponse.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
				distanceStr = distanceMatrixObj.getJSONObject("distance").getString("text");
				
				request.setOriginAddress(apiResponse.getJSONArray("origin_addresses").get(0).toString());
				request.setDestinationAddress(apiResponse.getJSONArray("destination_addresses").get(0).toString());
			}
			
			requestsRepo.save(request);
			logger.info("data saved");
			//send notification to the devices near by
			sendRequestNotificationToDrivers(request, distanceStr);
		}
		catch (Exception ex) {
			
			logger.info("here---->" + ex.getLocalizedMessage());
			ex.printStackTrace();
			
			jsonResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
			jsonResponse.setMessage(ex.getMessage());
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}

		jsonResponse.setMessage("Request info saved");	    
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}
	
	private ResponseEntity<?> addBid(long requestId, long bidAmountInCents) {
		jsonResponse = new CustomResponse();
		Bid bid = new Bid(requestId, bidAmountInCents);
		bidRepo.save(bid);
		//TODO send notification to customer
		//sendBidNotificationToCustomer();
		jsonResponse.setMessage("Bid info saved");	    
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}
	
	private void sendRequestNotificationToDrivers(VehicleRequest request, String distanceStr) {
		//get all drivers who are online. TODO Change this algorithm to pick the best driver based on rating and location
		
		String androidTokens = null;
		ArrayList<Driver> drivers = driverRepo.findActiveDrivers();
		if(!drivers.isEmpty()) {
			for (Driver driver : drivers) {
				if(!driver.getAndroidToken().equals("")) {
					androidTokens = androidTokens + driver.getAndroidToken();
				} else if(!driver.getIosToken().equals("")) {
					//TODO iOS
				}
			}
			try {
				Customer customer = customerRepo.findOne(request.getCustomerId());
				JSONObject infoJson = new JSONObject();
				infoJson.put("title", "Request");
				infoJson.put("body", "test message");
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("customer_name", customer.getFullName());
				dataJson.put("origin_lat", request.getPickupPointLat());
				dataJson.put("origin_lng", request.getPickupPointLng());
				dataJson.put("destination_lat", request.getDropPointLat());
				dataJson.put("destination_lng", request.getDropPointLng());
				dataJson.put("loading_required", request.getLoadingRequired());
				dataJson.put("unloading_required", request.getUnloadingRequired());
				dataJson.put("approx_fare_in_cents", request.getApproxFareInCents());
				dataJson.put("origin_address", request.getOriginAddress());
				dataJson.put("destination_address", request.getDestinationAddress());
				dataJson.put("distance", distanceStr);
				dataJson.put("id", request.getId());
				
				FCMService fcmService = new FCMService();
				fcmService.sendAndroidNotification(androidTokens, infoJson, dataJson);
			} catch(Exception e) {
				
			}
		} else{
			logger.info("no active drivers");
		}
	}
}
