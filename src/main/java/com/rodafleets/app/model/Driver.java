package com.rodafleets.app.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "driver")
public class Driver {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="phonenumber")
	private String phoneNumber;

	private String firstname;
	private String lastname;
	
	@Column(columnDefinition="default 0")
	private String gender;
	
	private String password;
	
	@Column(name="androidregistrationid")
	private String androidRegistrationId;
	

	@Column(name="iosregistrationid")
	private String iosRegistrationId;
	
	private boolean status;
	private boolean verified;
	
	@Transient
	private ArrayList<VehicleRequest> vehicleRequests;

	protected Driver() {}

	public Driver(String phonenumber, String firstName, String lastName, String gender) {
		this.phoneNumber = phonenumber;
		this.firstname = firstName;
		this.lastname = lastName;
		this.gender = gender;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getAndroidRegistrationId() {
		return androidRegistrationId;
	}

	public void setAndroidRegistrationId(String androidRegistrationId) {
		this.androidRegistrationId = androidRegistrationId;
	}

	public String getIosRegistrationId() {
		return iosRegistrationId;
	}

	public void setIosRegistrationId(String iosRegistrationId) {
		this.iosRegistrationId = iosRegistrationId;
	}

	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastName) {
		this.lastname = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

    public ArrayList<VehicleRequest> getVehicleRequests() {
    	return vehicleRequests;
    }
    
    public void setVehicleRequests(ArrayList<VehicleRequest> vehicleRequests) {
    	this.vehicleRequests = vehicleRequests;
    }
}
