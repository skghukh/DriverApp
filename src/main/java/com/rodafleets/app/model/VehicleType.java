package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicletypes")
public class VehicleType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String name;

	@Column(name="capacityinkgs")
	private long capacityInKgs;
	
	@Column(name="dimensions")
	private String dimensions;
	
	@Column(name="basefareincents")
	private long baseFareInCents;
	
	@Column(name="basefarekms")
	private long baseFareKms;
	
	@Column(name="distancefareincents")
	private long distanceFareInCents;
	
	@Column(name="distancefarekms")
	private long distanceFareKms;
	
	@Column(name="ridetimefareincents")
	private long rideTimeFareInCents;
	
	@Column(name="ridetimefaremins")
	private String rideTimeFareMins;
	
	protected VehicleType() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCapacityInKgs() {
		return capacityInKgs;
	}

	public void setCapacityInKgs(long capacityInKgs) {
		this.capacityInKgs = capacityInKgs;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public long getBaseFareInCents() {
		return baseFareInCents;
	}

	public void setBaseFareInCents(long baseFareInCents) {
		this.baseFareInCents = baseFareInCents;
	}

	public long getBaseFareKms() {
		return baseFareKms;
	}

	public void setBaseFareKms(long baseFareKms) {
		this.baseFareKms = baseFareKms;
	}

	public long getDistanceFareInCents() {
		return distanceFareInCents;
	}

	public void setDistanceFareInCents(long distanceFareInCents) {
		this.distanceFareInCents = distanceFareInCents;
	}

	public long getDistanceFareKms() {
		return distanceFareKms;
	}

	public void setDistanceFareKms(long distanceFareKms) {
		this.distanceFareKms = distanceFareKms;
	}

	public long getRideTimeFareInCents() {
		return rideTimeFareInCents;
	}

	public void setRideTimeFareInCents(long rideTimeFareInCents) {
		this.rideTimeFareInCents = rideTimeFareInCents;
	}

	public String getRideTimeFareMins() {
		return rideTimeFareMins;
	}

	public void setRideTimeFareMins(String rideTimeFareMins) {
		this.rideTimeFareMins = rideTimeFareMins;
	}
}
