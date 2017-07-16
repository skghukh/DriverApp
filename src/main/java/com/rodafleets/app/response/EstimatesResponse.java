package com.rodafleets.app.response;

public class EstimatesResponse extends CustomResponse{

	private String distance;
	private String duration;
	
	private String totalFareInCents;

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTotalFareInCents() {
		return totalFareInCents;
	}

	public void setTotalFareInCents(String totalFareInCents) {
		this.totalFareInCents = totalFareInCents;
	}
}
