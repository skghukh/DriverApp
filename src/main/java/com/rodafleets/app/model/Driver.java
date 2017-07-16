package com.rodafleets.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
//@NamedQuery(name="Driver.findByPhoneNumber", query = "SELECT * FROM Driver where phonenumber = ?1")
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
	private long verified;

	protected Driver() {}

	public Driver(String phonenumber, String firstName, String lastName, String gender) {
		this.phoneNumber = phonenumber;
		this.firstname = firstName;
		this.lastname = lastName;
		this.gender = gender;
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

//	public String getPassword() {
//		return password;
//	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getVerified() {
		return verified;
	}

	public void setVerified(long verified) {
		this.verified = verified;
	}
}
