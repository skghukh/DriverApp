package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vehiclerequestnotificationhistory")
public class VehicleRequestNotificationHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="driver_id")
	private long driverId;

	@ManyToOne
	@JoinColumn(name="vehiclerequest_id", nullable=false)
	private VehicleRequest vehicleRequest;
	
	private boolean status;
	
	protected VehicleRequestNotificationHistory() {}
	
	public VehicleRequestNotificationHistory(VehicleRequest vehicleRequest, long driverId) {
		this.vehicleRequest = vehicleRequest;
		this.driverId = driverId;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public VehicleRequest getVehicleRequest() {
		return vehicleRequest;
	}

	public void setVehicleRequest(VehicleRequest vehicleRequest) {
		this.vehicleRequest = vehicleRequest;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
