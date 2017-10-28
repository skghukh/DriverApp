package com.rodafleets.app.config;

public class AppConfig {
	
	public static final String API_VERSION = "0.1";
	
	public static final String SEND_OTP_SERVICE_URL = "http://2factor.in/API/V1/";
	public static final String SEND_OTP_SERVICE_API_KEY = "028aa1b7-372d-11e7-8473-00163ef91450";
	
	public static final String GOOGLE_DISTANCE_MATRIX_API_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";
	public static final String GOOGLE_DISTANCE_MATRIX_API_KEY = "AIzaSyD8-tzqtt9eK1Ab_szqWW88Gw_Zn5cdTLs";
	
	public static final String FCM_SERVICE_URL = "https://fcm.googleapis.com/fcm/send";
	public static final String FCM_SERVICE_KEY = "AAAAVKCtMEU:APA91bHGbZ3qnMmOv7vk0IZLK49foRCmhl_b1i3qfEj2WSwABsiTiBO6VZdgZju0MAtRvYaM0S98z7R51c3cMDeO1q5U2MKTQaONYKnXdGrGjqVndiborFaovFejOY36G_Z9PbIa8_b9";

	public static final int REQUEST_EXPIRATION = 24;
	//VEHICLE_REQUEST STATUS CODES
	
	//BID STATUS CODES
	public static final int BID_STATUS_PENDING = 0;
	public static final int BID_STATUS_ACCEPTED = 1;
	public static final int BID_STATUS_DECLINED = 2;
	public static final int BID_STATUS_EXPIRED = 3;
	
	//TRIP STATUS CODES
	public static final int TRIP_STATUS_CONFIRMED = 0;
	public static final int TRIP_STATUS_LOADING = 1;
	public static final int TRIP_STATUS_STARTED = 2;
	public static final int TRIP_STATUS_UNLOADING = 3;
	public static final int TRIP_STATUS_PAYMENT = 4;
	public static final int TRIP_STATUS_COMPLETED = 10;
	
	// ERROR CODES
	//common
	public static final int DUPLICATE_ENTRY = 111;
	// driver api
	public static final int SENDING_OTP_FAILED = 101;
	public static final int PHONE_NOT_VERIFIED = 102;
	public static final int SESSION_ID_REQUIRED_FOR_OTP_VERIFICATION = 103;
	public static final int INVALID_OTP = 104;
	public static final int INVALID_CREDENTIALS = 105;
	public static final int INVALID_DRIVER_ID = 106;
	public static final int NO_RESULTS = 107;

	//vehicle api
	public static final int INVALID_VEHICLE_ID = 201;
	public static final int VEHICLE_TYPES_NOT_FOUND = 202;
	
	//estimates api
	public static final int INVALID_VEHICLE_TYPE_ID = 301;
	
	//requests api
	public static final int INVALID_REQUEST_ID = 401;
	public static final int INVALID_BID_ID = 402;
	
//	public static final int INTERNAL_SERVER_ERROR = 500;
}
