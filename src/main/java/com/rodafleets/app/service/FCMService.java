package com.rodafleets.app.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.rodafleets.app.config.AppConfig;

public class FCMService {
	private final Log logger = LogFactory.getLog(this.getClass());
	
	public void sendAndroidNotification(String deviceToken, String message, String title) throws IOException {
		try {
			String url = AppConfig.FCM_SERVICE_URL;
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
//			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Autorization", "key=" + AppConfig.FCM_SERVICE_KEY);

			String urlParameters = "body=test message&title=test message title";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
		} catch (Exception e) {
//			return 0;
		}
	}
	
	public void sendNotification(String deviceToken) {
		logger.info("1");
		try {
			String url = AppConfig.FCM_SERVICE_URL;
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			
			con.setUseCaches(false);
		    con.setDoInput(true);
		    con.setDoOutput(true);

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Autorization", "key=" + AppConfig.FCM_SERVICE_KEY);
			
			//Create JSON Object & pass value
			JSONObject infoJson = new JSONObject();
			infoJson.put("title","Here is your notification.");
			infoJson.put("body", "test message");
	
			JSONObject json = new JSONObject();
			json.put("to", deviceToken.trim());
			json.put("notification", infoJson);
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			
			int responseCode = con.getResponseCode();
			logger.info("Response Code = " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			logger.info("FCM response = " + response.toString());
		} catch (Exception e) {
			logger.error("Exception in FCM = " + e.toString());
		}
	}
}
