package com.rodafleets.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="vehiclerequests")
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
	
	@Column(name="expiration")
	private Date expiration;

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

	public double getOriginLat() {
		return originLat;
	}

	public void setOriginLat(double originLat) {
		this.originLat = originLat;
	}

	public double getOriginLng() {
		return originLng;
	}

	public void setOriginLng(double originLng) {
		this.originLng = originLng;
	}

	public double getDestinationLat() {
		return destinationLat;
	}

	public void setDestinationLat(double destinationLat) {
		this.destinationLat = destinationLat;
	}

	public double getDestinationLng() {
		return destinationLng;
	}

	public void setDestinationLng(double destinationLng) {
		this.destinationLng = destinationLng;
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

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	
	
//	
//    public List<VehicleRequestNotificationHistory> getDrivers() {
//        //return drivers;
//    	return null;
//    }
//	
//	public void setDrivers(List<VehicleRequestNotificationHistory> drivers) {
//        //this.drivers = drivers;
//    }
}
