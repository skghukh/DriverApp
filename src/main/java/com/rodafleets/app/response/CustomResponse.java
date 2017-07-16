package com.rodafleets.app.response;

public class CustomResponse {
	private int code;
	private String message;
	
	
	public int getErrorCode() {
		return code;
	}
	public void setCode(int errorCode) {
		this.code = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}