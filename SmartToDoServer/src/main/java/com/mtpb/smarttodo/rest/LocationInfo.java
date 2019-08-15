package com.mtpb.smarttodo.rest;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

public class LocationInfo {
	final private String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
	
	
	final private String API_KEY = "AIzaSyCrc3-OvdWor31c42VduhC3nmsvspb4JXs";
	
	
	public String getNearbyLocationDetails(String location, String radius)
	{
		URL url = null;
		try {
			url = new URL(BASE_URL + location + "&radius=" + radius + "&key=" + API_KEY);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setInstanceFollowRedirects(false);
			
			int status = con.getResponseCode();
			System.out.println("Response code = " + status);
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			con.disconnect();
			return content.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
	 
			
