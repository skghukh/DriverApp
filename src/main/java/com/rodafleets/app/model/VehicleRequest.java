package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driverrequest")
public class VehicleRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="customer_id")
	private long customerId;
	
	@Column(name="vehicletype_id")
	private long vehicleTypeId;
	
	@Column(name="originlat")
	private double originLat;
	
	@Column(name="originlng")
	private double originLng;
	
	@Column(name="destinationlat")
	private double destinationLat;
	
	@Column(name="destinationlng")
	private double destinationLng;
	
	@Column(name="loadingrequired")
	private long loadingRequired;
	
	@Column(name="unloadingrequired")
	private long unloadingRequired;
	
	@Column(name="approxfareincents")
	private long approxFareInCents;
	
	@Column(name="originaddress")
	private String originAddress;
	
	@Column(name="destinationaddress")
	private String destinationAddress;

	protected VehicleRequest() {}

	public VehicleRequest(long customerId, long vehicleTypeId, double originLat, double originLng,
			double destinationLat, double destinationLng, long loadingRequired, long unloadingRequired, long approxFareInCents) {
		this.customerId = customerId;
		this.vehicleTypeId = vehicleTypeId;		
		this.originLat = originLat;
		this.originLng = originLng;
		this.destinationLat = destinationLat;
		this.destinationLng = destinationLng;
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
		return originLat;
	}

	public void setPickupPointLat(double pickupPointLat) {
		this.originLat = pickupPointLat;
	}

	public double getPickupPointLng() {
		return originLng;
	}

	public void setPickupPointLng(double pickupPointLng) {
		this.originLng = pickupPointLng;
	}

	public double getDropPointLat() {
		return destinationLat;
	}

	public void setDropPointLat(double dropPointLat) {
		this.destinationLat = dropPointLat;
	}

	public double getDropPointLng() {
		return destinationLng;
	}

	public void setDropPointLng(double dropPointLng) {
		this.destinationLng = dropPointLng;
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
	
	public long getApproxFareInCents() {
		return approxFareInCents;
	}

	public void setApproxFareInCents(long approxFareInCents) {
		this.approxFareInCents = approxFareInCents;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
}
