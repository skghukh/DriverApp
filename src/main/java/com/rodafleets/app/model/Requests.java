package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driverrequest")
public class Requests {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="customer_id")
	private long customerId;
	
	@Column(name="vehicletype_id")
	private long vehicleTypeId;
	
	@Column(name="pickuplat")
	private double pickupPointLat;
	
	@Column(name="pickuplng")
	private double pickupPointLng;
	
	@Column(name="droplat")
	private double dropPointLat;
	
	@Column(name="droplng")
	private double dropPointLng;
	
	@Column(name="loadingrequired")
	private long loadingRequired;
	
	@Column(name="unloadingrequired")
	private long unloadingRequired;
	
	@Column(name="approxfareincents")
	private long approxFareInCents;
	
	protected Requests() {}

	public Requests(long customerId, long vehicleTypeId, double pickupPointLat, double pickupPointLng,
			double dropPointLat, double dropPointLng, long loadingRequired, long unloadingRequired, long approxFareInCents) {
		this.customerId = customerId;
		this.vehicleTypeId = vehicleTypeId;		
		this.pickupPointLat = pickupPointLat;
		this.pickupPointLng = pickupPointLng;
		this.dropPointLat = dropPointLat;
		this.dropPointLng = dropPointLng;
		this.loadingRequired = loadingRequired;
		this.unloadingRequired = unloadingRequired;
		this.approxFareInCents = approxFareInCents;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public double getPickupPointLat() {
		return pickupPointLat;
	}

	public void setPickupPointLat(double pickupPointLat) {
		this.pickupPointLat = pickupPointLat;
	}

	public double getPickupPointLng() {
		return pickupPointLng;
	}

	public void setPickupPointLng(double pickupPointLng) {
		this.pickupPointLng = pickupPointLng;
	}

	public double getDropPointLat() {
		return dropPointLat;
	}

	public void setDropPointLat(double dropPointLat) {
		this.dropPointLat = dropPointLat;
	}

	public double getDropPointLng() {
		return dropPointLng;
	}

	public void setDropPointLng(double dropPointLng) {
		this.dropPointLng = dropPointLng;
	}

	public long getLoadingRequired() {
		return loadingRequired;
	}

	public void setLoadingRequired(long loadingRequired) {
		this.loadingRequired = loadingRequired;
	}

	public long getUnloadingRequired() {
		return unloadingRequired;
	}

	public void setUnloadingRequired(long unloadingRequired) {
		this.unloadingRequired = unloadingRequired;
	}
}
