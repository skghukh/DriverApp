package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="vehicletype_id")
	private long vehicleTypeId;

	@Column(name="number")
	private String number;

	@Column(name="driver_id")
	private long driverId;
	
	@Column(name="owner_id")
	private long ownerId;

	protected Vehicle() {}
	
	public Vehicle(long driverId, String number, long vehicleTypeId) {
		this.driverId = driverId;
		this.number = number;
		this.vehicleTypeId = vehicleTypeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

}
