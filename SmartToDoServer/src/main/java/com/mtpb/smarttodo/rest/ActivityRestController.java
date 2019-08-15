package com.mtpb.smarttodo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtpb.smarttodo.entity.ActivityList;
import com.mtpb.smarttodo.entity.RegIDForUser;
import com.mtpb.smarttodo.repository.ActivityRepository;
import com.mtpb.smarttodo.repository.registrationRepository;



@RestController
@RequestMapping("/smarttodo")
public class ActivityRestController {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private registrationRepository regRepository;
	
	final public String LOCATION_RADIUS = "500";
	
	@GetMapping("/{username}/{regid}")
    public ResponseEntity<List<ActivityList>> getActivityListById(@PathVariable(value = "username") String userName
    		, @PathVariable(value = "regid")String regid){
       
		System.out.println("Fetching activity for = " + userName);
        List<ActivityList> activities = activityRepository.findActivityByUser(userName);
        Optional<RegIDForUser> regData = regRepository.findById(userName);
        if(regData.orElse(null) == null)
        {
        	RegIDForUser regDataObj = new RegIDForUser();
        	regDataObj.setRegid(regid);
        	regDataObj.setUsername(userName);
        	regRepository.save(regDataObj);
        }
        return ResponseEntity.ok().body(activities);
    }
	
	@PostMapping("/activitylist")
	public ResponseEntity<String> createActivity(@RequestBody ActivityList activity)
	{
		System.out.println("Received post request " + activity.toString());
		activityRepository.save(activity);
		return ResponseEntity.status(HttpStatus.CREATED).build();		
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<String> UpdateActivity(@PathVariable(value = "id")long id)
	{
		Optional<ActivityList> activity = activityRepository.findById(id);
		if(activity != null)
		{
			activityRepository.deleteById(id);
		}
		if(null != activity.orElse(null))
		{
			activityRepository.save(activity.orElse(null));
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/{username}/{location}")
	public ResponseEntity<String> checkLocation(@PathVariable("username")String userName, 
			@PathVariable("location")String currLocation)
	{		
		LocationInfo location = new LocationInfo();
		String response = location.getNearbyLocationDetails(currLocation, LOCATION_RADIUS);
		if(response != null)
		{
			//JSONParser parser = new JSONParser(response);
			//try {
				JSONObject json = new JSONObject(response);
				List<ActivityList> activities = activityRepository.findActivityByUser(userName);
				if(activities.size() != 0)
				{
					Optional<RegIDForUser> regData = regRepository.findById(userName);
					List<NotificationData> notifyList = checkNearByActivites(json, activities);
					Notifications notification = new Notifications();
					for(NotificationData n : notifyList)
					{
						if(regData.orElse(null) != null)
							n.regID = (regData.orElse(null)).getRegid();
						
						notification.sendNotifications(n);
					}
				}
		//	} catch (ParseException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
		}
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteActivity(@PathVariable(value = "id")long id)
	{
		activityRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	private List<NotificationData> checkNearByActivites(JSONObject json, List<ActivityList> aList)
	{
		List<NotificationData> tempList = getNearByList(json);
		List<NotificationData> dataList = new ArrayList<NotificationData>();
		for(ActivityList a : aList)
		{
			for(NotificationData n : tempList)
			{
				if(n.placeName == a.getLocation())
				{
					n.activityName = a.getItemName();
					dataList.add(n);			
					break;
				}
			}
		}
		return dataList;
	}
	
	private List<NotificationData> getNearByList(JSONObject inJson)
	{
		List<NotificationData> dataList = new ArrayList<NotificationData>();
		JSONArray resultArray = inJson.getJSONArray("results");
		for(int i = 0; i < resultArray.length(); i++)
		{
			JSONObject obj = resultArray.getJSONObject(i);
			NotificationData data = new NotificationData();
			data.placeName = obj.getString("name");
			data.icon = obj.getString("icon");
			data.vicinity = obj.getString("vicinity");
			dataList.add(data);
		}
		return dataList;
	}
}



