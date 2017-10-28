package com.rodafleets.app.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.dataaccess.CustomerRepository;
import com.rodafleets.app.dataaccess.TripRepository;
import com.rodafleets.app.model.Customer;
import com.rodafleets.app.model.Trip;
import com.rodafleets.app.response.CustomResponse;
import com.rodafleets.app.service.FCMService;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/" + AppConfig.API_VERSION + "/trip")
public class TripController {
	private static final Logger logger = LoggerFactory.getLogger(TripController.class);
	private CustomResponse jsonResponse;

	@Autowired
	TripRepository tripRepo;

	@Autowired
	CustomerRepository customerRepo;

	@RequestMapping(value = "/{driver_id}/inprogress", method = RequestMethod.GET)
	public ResponseEntity<?> getDriversTripInProgress(@PathVariable("driver_id") int driverId) {
		Trip tripInProgress = tripRepo.getTripInProgress(driverId);
		return new ResponseEntity<Trip>(tripInProgress, HttpStatus.OK);
	}

	@RequestMapping(value = "/{trip_id}/request/{request_id}/update/{status}", method = RequestMethod.GET)
	public ResponseEntity<?> updateTripState(@PathVariable("trip_id") long tripId,
			@PathVariable("request_id") long requestId, @PathVariable("status") int status) {
		Trip tripInProgress = tripRepo.findOne(tripId);
		if (null != tripInProgress && tripInProgress.getStatus() >= 0 && tripInProgress.getStatus() < 10) {
			tripInProgress.setStatus(status);
			Trip saved = tripRepo.save(tripInProgress);
			sendTripUpdateNotificationToCustomer(tripInProgress);
			return new ResponseEntity<Trip>(saved, HttpStatus.OK);
		}
		// tripRepo.updateTrip(status, requestId);
		return new ResponseEntity<>(tripInProgress, HttpStatus.EXPECTATION_FAILED);
	}

	private void sendTripUpdateNotificationToCustomer(Trip trip) {
		Customer customer = customerRepo.findOne(trip.getCustomerId());
		try {
			String androidToken = customer.getAndroidRegistrationId();
			ArrayList<String> tokens = new ArrayList<>();
			tokens.add(androidToken);
			JSONObject infoJson = new JSONObject();
			infoJson.put("title", "TRIP_UPDATED");
			infoJson.put("body",
					trip.getDriverId() + " TRIP " + trip.getId() + " STATUS_UPDATED_TO " + trip.getStatus());
			JSONObject dataJson = new JSONObject();
			dataJson.put("driverId", trip.getDriverId());
			dataJson.put("tripId", trip.getId());
			dataJson.put("bId", trip.getBidId());
			dataJson.put("reqId", trip.getRequestId());
			dataJson.put("status", trip.getStatus());
			FCMService fcmService = new FCMService();
			fcmService.sendAndroidNotification(tokens, infoJson, dataJson);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}

	}

}
