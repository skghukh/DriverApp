package com.rodafleets.app.response;

import com.rodafleets.app.model.Driver;

public class DriverResponse extends CustomResponse {

	private String sessionId;
	private String token;
	private Driver driver;

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
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
