package com.rodafleets.app.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.service.OTPService;

public class RestUtils {

	private static final Logger log = LoggerFactory.getLogger(RestUtils.class);
	
	public JSONObject httpGetRequest(String url, String urlParameterString) {
		JSONObject jsonResponse;
		try{
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url + urlParameterString);

			// add request header
			//		request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = client.execute(request);
			
			log.info("Response HttpStatusCode = " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			log.info("GET Response : " + result.toString());
			jsonResponse = new JSONObject(result.toString());
			return jsonResponse;
			
		}catch (Exception e) {
			log.error("http GET exception" + e.toString());
			return null;
		}
	}

	public JSONObject httpPostRequest(String url, List<NameValuePair> urlParameters) {
		JSONObject jsonResponse;
		try{
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			// add header
			//post.setHeader("User-Agent", USER_AGENT);
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			log.info("Response HttpStatusCode = " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			log.info("http post response = " + result.toString());
			jsonResponse = new JSONObject(result.toString());
			return jsonResponse;
			
		} catch (Exception e) {
			log.error("http POST exception" + e.toString());
			return null;
		}
	}

	public JSONObject distance(double originLat, double originLng,
			double destinationLat, double destinationLng) {
		
		JSONObject jsonResponse;
		String urlParameterString = 
				"?origins=" + originLat + "," + originLng 
				+ "&destinations=" + destinationLat + "," + destinationLng 
				+ "&key=" + AppConfig.GOOGLE_DISTANCE_MATRIX_API_KEY;
		String url = AppConfig.GOOGLE_DISTANCE_MATRIX_API_URL + urlParameterString;
	
		try{
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);

			// add request header
			//		request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = client.execute(request);
			
			log.info("Response HttpStatusCode = " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			log.info("GET Response : " + result.toString());
			jsonResponse = new JSONObject(result.toString());
			return jsonResponse;
			
		}catch (Exception e) {
			log.error("http GET exception" + e.toString());
			return null;
		}
	}
}
