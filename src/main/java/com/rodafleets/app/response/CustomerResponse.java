package com.rodafleets.app.response;

import com.rodafleets.app.model.Customer;

public class CustomerResponse extends CustomResponse {

	private String sessionId;
	private String token;
	private Customer customer;


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
