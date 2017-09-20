package com.rodafleets.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The persistent class for the driverlocation database table.
 * 
 */
@Entity
public class Driverlocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "driver_id")
	private int driverId;

	private Double latitude;

	private Double longitude;

	public Driverlocation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDriverId() {
		return this.driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}