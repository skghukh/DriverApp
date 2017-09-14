package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(columnDefinition = "default 0")
	private String gender;

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "androidregistrationid")
	private String androidRegistrationId;

	@Column(name = "iosregistrationid")
	private String iosRegistrationId;

	private String password;
	private long verified;
	private String address;

	protected Customer() {
	}

	public Customer(String phonenumber) {
		this.phoneNumber = phonenumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getVerified() {
		return verified;
	}

	public void setVerified(long verified) {
		this.verified = verified;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFullName() {
		// TODO
		return "ADITYA";
		// return firstName + " " + lastName;
	}
}
