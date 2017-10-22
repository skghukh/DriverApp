package com.rodafleets.app.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.model.Driverlocation;

@Controller
@RestController // This means that this class is a Controller
@RequestMapping(path = "/" + AppConfig.API_VERSION + "/location")
public class LocationController {

	@Autowired
	DriverLocationRepository driverLocationRepo;

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
			return new ResponseEntity<>(driverLocation, HttpStatus.OK);
		}

	}

}
