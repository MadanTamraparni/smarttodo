package com.mtpb.smarttodo.rest;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class Notifications {
	final String NOTIFY_URL ="https://fcm.googleapis.com/fcm/send";

	public void sendNotifications(NotificationData data)
	{
		URL url = null;
		try {
			 url = new URL(NOTIFY_URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
			
				con.setRequestMethod("POST");
			
			con.setRequestProperty("Content-Type", "application/json");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setInstanceFollowRedirects(false);
			
			JSONObject obj = new JSONObject();
			obj.put("title", "To Do Notification");
			obj.put("notificationType", "Test");
			obj.put("body", data.activityName + " can be done at " + data.placeName + " in the vicinity " + data.vicinity);
			
			JSONObject message = new JSONObject();
			message.put("data",obj);
			message.put("to", data.regID);
			
			int status = con.getResponseCode();
			System.out.println("Received response for reg id = " + status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
