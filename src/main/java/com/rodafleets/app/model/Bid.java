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
	
	@Column(name="vehiclerequest_id")
	private long requestId; 
	
	@Column(name="driver_id")
	private long driverId;

	private int status;

	protected Bid() {}
	
	public Bid(long requestId, long driverId, long bidAmountInCents, int status) {
		this.requestId = requestId;
		this.driverId = driverId;
		this.bidAmountInCents = bidAmountInCents;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBidAmountInCents() {
		return bidAmountInCents;
	}

	public void setBidAmountInCents(long bidAmountInCents) {
		this.bidAmountInCents = bidAmountInCents;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}