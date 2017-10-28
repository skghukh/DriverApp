package com.rodafleets.app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.rodafleets.app.dataaccess.TripRepository;
import com.rodafleets.app.dataaccess.VehicleRepository;
import com.rodafleets.app.dataaccess.VehicleRequestNotificationHistoryRepository;
import com.rodafleets.app.dataaccess.VehicleRequestsRepository;
import com.rodafleets.app.model.Bid;
import com.rodafleets.app.model.Customer;
import com.rodafleets.app.model.Driver;
import com.rodafleets.app.model.Trip;
import com.rodafleets.app.model.Vehicle;
import com.rodafleets.app.model.VehicleRequest;
import com.rodafleets.app.model.VehicleRequestNotificationHistory;
import com.rodafleets.app.response.BidResponse;
import com.rodafleets.app.response.CustomResponse;
import com.rodafleets.app.response.TripResponse;
import com.rodafleets.app.service.FCMService;
import com.rodafleets.app.util.RestUtils;

/*===================  Requests API  =================== */

@RestController // This means that this class is a Controller
@RequestMapping(path = "/" + AppConfig.API_VERSION + "/requests") // This means
																	// URL's
																	// start
																	// with
																	// /requests
																	// after API
																	// base url
public class RequestsController {

	private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
	private CustomResponse jsonResponse;

	@Autowired // This means to get the bean called RequestsRepository
	private VehicleRequestsRepository requestsRepo;

	@Autowired
	private DriverRepository driverRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private BidRepository bidRepo;

	@Autowired
	private VehicleRequestNotificationHistoryRepository notificationHistoryRepo;

	@Autowired
	private TripRepository tripRepo;

	@Autowired
	private VehicleRepository vehicleRepo;

	/*
	 * Retrive all drivers and their info
	 * 
	 * @Url: localhost:8080/drivers
	 */
	@RequestMapping(value = "/{customer_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllRequestsByCustomer(@PathVariable("customer_id") long customerId) {
		return findAllRequestsByCustomer(customerId);
	}

	/*
	 * Retrive all drivers and their info
	 * 
	 * @Url: localhost:8080/drivers
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveRequest(@RequestParam(value = "customer_id") long customerId,
			@RequestParam(value = "vehicletype_id") long vehicleTypeId,
			@RequestParam(value = "origin_lat") double originLat, @RequestParam(value = "origin_lng") double originLng,
			@RequestParam(value = "destination_lat") double destinationLat,
			@RequestParam(value = "destination_lng") double destinationLng,
			@RequestParam(value = "loading_required", defaultValue = "0") long loadingRequired,
			@RequestParam(value = "unloading_required", defaultValue = "0") long unloadingRequired,
			@RequestParam(value = "approx_fare_in_cents") long approxFareInCents) {
		return addRequest(customerId, vehicleTypeId, originLat, originLng, destinationLat, destinationLng,
				loadingRequired, unloadingRequired, approxFareInCents);
	}

	@RequestMapping(value = "/{request_id}/bids", method = RequestMethod.POST)
	public ResponseEntity<?> saveBid(@PathVariable("request_id") long requestId,
			@RequestParam(value = "driver_id") long driverId,
			@RequestParam(value = "bid_amount_in_cents") long bidAmountInCents) {
		return addBid(requestId, driverId, bidAmountInCents);
	}

	@RequestMapping(value = "/{request_id}/reject", method = RequestMethod.POST)
	public ResponseEntity<?> rejectRequest(@PathVariable("request_id") long requestId,
			@RequestParam(value = "driver_id") long driverId) {
		return denyRequest(requestId, driverId);
	}

	@RequestMapping(value = "/{request_id}/bids/{bid_id}/accept", method = RequestMethod.POST)
	public ResponseEntity<?> acceptBid(@PathVariable("request_id") long requestId, @PathVariable("bid_id") long bidId,
			@RequestParam(value = "customer_id") long customerId) {
		return addTrip(requestId, bidId, customerId);
	}

	private ResponseEntity<?> findAllRequestsByCustomer(long customerId) {
		jsonResponse = new CustomResponse();
		List<VehicleRequest> requests = requestsRepo.findRequestsByCustomerId(customerId);
		if (requests.isEmpty()) {
			logger.info("search returned empty");
			jsonResponse.setCode(HttpStatus.NO_CONTENT.value());
			jsonResponse.setMessage("No requests from this customer");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}
		return new ResponseEntity<List>(requests, HttpStatus.CREATED);
	}

	private ResponseEntity<?> addRequest(long customerId, long vehicleTypeId, double originLat, double originLng,
			double destinationLat, double destinationLng, long loadingRequired, long unloadingRequired,
			long approxFareInCents) {
		jsonResponse = new CustomResponse();
		try {
			String distanceStr = "";
			VehicleRequest request = new VehicleRequest(customerId, vehicleTypeId, originLat, originLng, destinationLat,
					destinationLng, loadingRequired, unloadingRequired, approxFareInCents);

			RestUtils utils = new RestUtils();
			JSONObject apiResponse = utils.distance(originLat, originLng, destinationLat, destinationLng);
			if (apiResponse.getString("status").equalsIgnoreCase("OK")) {
				// if(apiResponse.getJSONArray("rows").getJSONObject(0).getJSONArray("elements"))

				JSONObject distanceMatrixObj = apiResponse.getJSONArray("rows").getJSONObject(0)
						.getJSONArray("elements").getJSONObject(0);
				if (distanceMatrixObj.getString("status").equalsIgnoreCase("NOT_FOUND")) {
					jsonResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
					jsonResponse.setMessage("Invalid origin/destination address");
					return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
				}
				// return the first calculated route info for approx fare
				// estimate
				distanceStr = distanceMatrixObj.getJSONObject("distance").getString("text");

				request.setOriginAddress(apiResponse.getJSONArray("origin_addresses").get(0).toString());
				request.setDestinationAddress(apiResponse.getJSONArray("destination_addresses").get(0).toString());
			}

			// set request expiration to 24Hours
			Date currentDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			c.add(Calendar.HOUR, AppConfig.REQUEST_EXPIRATION);

			Date expiration = c.getTime();
			request.setExpiration(expiration);

			requestsRepo.save(request);
			logger.info("data saved");
			// send notification to the devices near by
			sendRequestNotificationToDrivers(request, distanceStr);
		} catch (Exception ex) {
			// logger.info("Exception = " + ex.getLocalizedMessage());
			// ex.printStackTrace();
			jsonResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
			jsonResponse.setMessage(ex.getMessage());
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}

		jsonResponse.setMessage("Request info saved");
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}

	private ResponseEntity<?> addBid(long requestId, long driverId, long bidAmountInCents) {
		BidResponse jsonResponse = new BidResponse();
		Bid bid = new Bid(requestId, driverId, bidAmountInCents, AppConfig.BID_STATUS_PENDING);
		bidRepo.save(bid);
		sendBidNotificationToCustomer(requestId, bid.getId(), driverId, bidAmountInCents);
		jsonResponse.setMessage("Bid info saved");
		jsonResponse.setBid(bid);
		return new ResponseEntity<BidResponse>(jsonResponse, HttpStatus.CREATED);
	}

	private ResponseEntity<?> denyRequest(long requestId, long driverId) {
		CustomResponse jsonResponse = new CustomResponse();
		VehicleRequestNotificationHistory notificationHistory = notificationHistoryRepo
				.findOneByVehicleRequestIdAndDriverId(requestId, driverId);

		if (notificationHistory != null) {
			notificationHistory.setStatus(true);
			notificationHistoryRepo.save(notificationHistory);
		} else {
			jsonResponse.setCode(AppConfig.INVALID_REQUEST_ID);
			jsonResponse.setMessage("Invalid request id");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		jsonResponse.setMessage("Request rejected");
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}

	private ResponseEntity<?> addTrip(long requestId, long bidId, long customerId) {
		TripResponse jsonResponse = new TripResponse();
		Bid bid = bidRepo.findOne(bidId);
		if (bid != null) {
			// update bid status
			bid.setStatus(AppConfig.BID_STATUS_ACCEPTED);
			bidRepo.save(bid);

			// create new trip
			Trip trip = new Trip(requestId, bidId, customerId, (int) bid.getDriverId(),
					AppConfig.TRIP_STATUS_CONFIRMED);
			tripRepo.save(trip);

			// TODO send notification to driver
			sendTripNotificationToDriver(bid.getDriverId(), trip.getId(), requestId);

			jsonResponse.setMessage("trip confirmed");
			jsonResponse.setTrip(trip);
			return new ResponseEntity<TripResponse>(jsonResponse, HttpStatus.CREATED);
		} else {
			jsonResponse.setCode(AppConfig.INVALID_BID_ID);
			jsonResponse.setMessage("Invalid bid id");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
	}

	private void sendBidNotificationToCustomer(long requestId, long bidId, long driverId, long bidAmountInCents) {
		Driver driver = driverRepo.findOne(driverId);
		VehicleRequest request = requestsRepo.findOne(requestId);
		Customer customer = null;
		if (null != request) {
			long customerId = request.getCustomerId();
			if (customerId > 0) {
				customer = customerRepo.findOne(customerId);
			}
		}
		if (null != driver && null != customer) {
			try {
				String androidToken = customer.getAndroidRegistrationId();
				ArrayList<String> tokens = new ArrayList<>();
				tokens.add(androidToken);
				JSONObject infoJson = new JSONObject();
				infoJson.put("title", "Request_Accepted");
				infoJson.put("body",
						driver.getFirstName() + " has accepted request " + requestId + "Charges " + bidAmountInCents);
				JSONObject dataJson = new JSONObject();
				dataJson.put("driverId", driver.getId());
				dataJson.put("driverName", driver.getFirstName());
				dataJson.put("driverContact", driver.getPhoneNumber());
				dataJson.put("driverRating", "4.2");
				dataJson.put("driverDistance", "6.5km");
				dataJson.put("requestId", requestId);
				dataJson.put("bid", bidId);
				List<Vehicle> vehicleListOfDriver = vehicleRepo.findByDriverId(driverId);
				if (vehicleListOfDriver.isEmpty()) {
					logger.error("No vehicle owned by driver");
					return;
				}
				dataJson.put("vehicleRegId", vehicleListOfDriver.get(0).getNumber());
				dataJson.put("Amount", bidAmountInCents);
				FCMService fcmService = new FCMService();
				fcmService.sendAndroidNotification(tokens, infoJson, dataJson);
			} catch (Exception e) {
				System.out.println("Exception " + e);
			}
		}

	}

	private void sendRequestNotificationToDrivers(VehicleRequest request, String distanceStr) {
		// get all drivers who are online. TODO Change this algorithm to pick
		// the best driver based on rating and location
		ArrayList<Driver> drivers = driverRepo.findActiveDrivers();
		if (!drivers.isEmpty()) {
			ArrayList<String> androidTokens = new ArrayList<>();
			for (Driver driver : drivers) {
				if (!driver.getAndroidRegistrationId().equals("")) {
					androidTokens.add(driver.getAndroidRegistrationId());
				} else if (!driver.getIosRegistrationId().equals("")) {
					// //TODO iOS
				}

				// make an entry to db as well
				VehicleRequestNotificationHistory h = new VehicleRequestNotificationHistory(request, driver.getId());
				h.setStatus(false);
				notificationHistoryRepo.save(h);
			}

			try {
				Customer customer = customerRepo.findOne(request.getCustomerId());
				JSONObject infoJson = new JSONObject();
				infoJson.put("title", "Request");
				infoJson.put("body",
						customer.getFullName() + " is requesting vehicle at " + request.getDestinationAddress());

				JSONObject dataJson = new JSONObject();
				dataJson.put("customerName", customer.getFullName());
				dataJson.put("originLat", request.getOriginLat());
				dataJson.put("originLng", request.getOriginLng());
				dataJson.put("destinationLat", request.getDestinationLat());
				dataJson.put("destinationLng", request.getDestinationLng());
				dataJson.put("loadingRequired", request.getLoadingRequired());
				dataJson.put("unloadingRequired", request.getUnloadingRequired());
				dataJson.put("approxFareInCents", request.getApproxFareInCents());
				dataJson.put("originAddress", request.getOriginAddress());
				dataJson.put("destinationAddress", request.getDestinationAddress());
				dataJson.put("distance", distanceStr);
				dataJson.put("id", request.getId());

				// request.
				FCMService fcmService = new FCMService();
				fcmService.sendAndroidNotification(androidTokens, infoJson, dataJson);
			} catch (Exception e) {

			}
		} else {
			logger.info("no active drivers");
		}
	}

	private void sendTripNotificationToDriver(long driverId, long tripId, long requestId) {
		Driver driver = driverRepo.findOne(driverId);
		ArrayList<String> androidTokens = new ArrayList<>();
		if (driver != null) {
			try {
				androidTokens.add(driver.getAndroidRegistrationId());

				JSONObject notificationJson = new JSONObject();
				notificationJson.put("title", "Accept");
				notificationJson.put("body", "Bid Accepted");
				//
				JSONObject dataJson = new JSONObject();
				dataJson.put("tripId", tripId);
				dataJson.put("requestId", requestId);
				FCMService fcmService = new FCMService();
				fcmService.sendAndroidNotification(androidTokens, notificationJson, dataJson);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
