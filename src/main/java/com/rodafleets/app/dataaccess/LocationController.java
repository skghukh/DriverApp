package com.rodafleets.app.dataaccess;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.model.Customer;
import com.rodafleets.app.model.Driverlocation;
import com.rodafleets.app.model.Trip;
import com.rodafleets.app.service.FCMService;

@Controller
@RestController // This means that this class is a Controller
@RequestMapping(path = "/" + AppConfig.API_VERSION + "/location")
public class LocationController {

	@Autowired
	DriverLocationRepository driverLocationRepo;

	@Autowired
	TripRepository tripRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@RequestMapping(value = "/{driver_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDriverLocation(@PathVariable("driver_id") int driverId) {
		Driverlocation driverLocation = driverLocationRepo.getDriverLocationByDriverId(driverId);
		if (null == driverLocation) {
			return new ResponseEntity<>(driverLocation, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(driverLocation, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{driver_id}/update/langtitude/{lang}/longtitude/{long}", method = RequestMethod.POST)
	public ResponseEntity<?> updateDriverLocation(@PathVariable("driver_id") int driverId,
			@PathVariable("lang") double langtitude, @PathVariable("long") double longtitude) {
		Driverlocation driverLocation = driverLocationRepo.getDriverLocationByDriverId(driverId);
		if (null == driverLocation) {
			return new ResponseEntity<>(driverLocation, HttpStatus.NOT_FOUND);
		} else {
			driverLocation.setLatitude(langtitude);
			driverLocation.setLongitude(longtitude);
			driverLocationRepo.save(driverLocation);
			Trip tripInProgress = tripRepo.getTripInProgress(driverId);
			if (null != tripInProgress) {
				sendLocationUpdateToConsumer(tripInProgress.getRequestId(), tripInProgress.getBidId(),
						tripInProgress.getDriverId(), tripInProgress.getCustomerId(), langtitude, longtitude);
			}
			return new ResponseEntity<>(driverLocation, HttpStatus.OK);
		}

	}

	private void sendLocationUpdateToConsumer(long requestId, long bidId, long driverId, long customerId,
			double langtitude, double longtitude) {
		Customer customer = customerRepo.findOne(customerId);

		try {
			String androidToken = customer.getAndroidRegistrationId();
			ArrayList<String> tokens = new ArrayList<>();
			tokens.add(androidToken);
			JSONObject infoJson = new JSONObject();
			infoJson.put("title", "Driver_Location_Updated");
			infoJson.put("body", driverId + " location updated");
			JSONObject dataJson = new JSONObject();
			dataJson.put("driverId", driverId);
			dataJson.put("bId", bidId);
			dataJson.put("reqId", requestId);
			dataJson.put("lat", langtitude);
			dataJson.put("lan", longtitude);
			FCMService fcmService = new FCMService();
			fcmService.sendAndroidNotification(tokens, infoJson, dataJson);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}

	}

}
