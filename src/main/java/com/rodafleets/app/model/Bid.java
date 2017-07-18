package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bid {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="bidamountincents")
	private long bidAmountInCents;
	
	@Column(name="driverrequest_id")
	private long driverRequestId; 
	
	@Column(name="driver_id")
	private long driverId;
	
	@Column(columnDefinition="default 0")
	private Boolean status;

	protected Bid() {}
	
	public Bid(long driverRequestId, long bidAmountInCents) {
		this.driverRequestId = driverRequestId;
		this.bidAmountInCents = bidAmountInCents;
	}

}
