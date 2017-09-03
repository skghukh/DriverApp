package com.rodafleets.app.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.rodafleets.app.config.AppConfig;

public class FCMService {
	private final Log logger = LogFactory.getLog(this.getClass());

	public void sendAndroidNotification(ArrayList<String> deviceToken, JSONObject infoJson, JSONObject dataJson) {
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
			con.setRequestProperty("Authorization", "key=" + AppConfig.FCM_SERVICE_KEY);
	
			JSONObject json = new JSONObject();
			json.put("registration_ids", deviceToken.toArray());
			json.put("notification", infoJson);
			json.put("data", dataJson);
			
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
