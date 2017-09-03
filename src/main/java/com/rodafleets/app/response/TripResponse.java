package com.rodafleets.app.response;

import com.rodafleets.app.model.Trip;

public class TripResponse extends CustomResponse {

	private Trip trip;
	
	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
}
