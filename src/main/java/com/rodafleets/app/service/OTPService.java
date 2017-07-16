package com.rodafleets.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rodafleets.app.config.AppConfig;

public class OTPService {
	private static final Logger log = LoggerFactory.getLogger(OTPService.class);
	String url = AppConfig.SEND_OTP_SERVICE_URL + AppConfig.SEND_OTP_SERVICE_API_KEY;
	
	public String sendOTP(String phoneNumber) {
//		phoneNumber = "9886768498"; //for testing
		try {
			Random random = new Random();
			int otp = 1000 + random.nextInt(9998);
			
			url = url + "/SMS/" + phoneNumber + "/" + otp;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			log.info("\nSending 'GET' request to URL = " + url);
			log.info("Response Code = " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			log.info("sendOTP API response = " + response.toString());
			
			JSONObject jsonResponse = new JSONObject(response.toString());
			//Sample Success Response
			//{"Status":"Success","Details":"f1fd7112-37a6-11e7-8473-00163ef91450"}
			if(jsonResponse.getString("Status").equalsIgnoreCase("Success")) {
				//return session key, required to verify the OTP
				return jsonResponse.getString("Details");
			} else {
				return "0";
			}
		} catch (Exception e) {
			return "0";
		}
	}
	
	public Boolean verifyOTP(String sessionId, String otp) {
		try {
			url = url + "/SMS/VERIFY/" + sessionId + "/" + otp;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			log.info("\nSending 'GET' request to URL = " + url);
			log.info("Response Code = " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			log.info("verify OTP API response =" + response.toString());
			
			JSONObject jsonResponse = new JSONObject(response.toString());
			//Sample Success Response
			//{"Status":"Success","Details":"OTP Matched"}
			if(jsonResponse.getString("Status").equalsIgnoreCase("Success")) {
				//return session key, required to verify the OTP
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
