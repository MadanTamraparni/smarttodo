package com.mtpb.smarttodo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ActivityInfo {

	private String m_ActivityName;
	private String m_ActivityLocation;
	private String m_ActivityEndTime;
	private boolean m_ReminderRequired;
	
	ActivityInfo()
	{
		m_ActivityName = "Something";
		m_ReminderRequired = false;
	}
	@Override
	public String toString() {
		return "ActivityInfo [m_ActivityName=" + m_ActivityName + ", m_ActivityLocation=" + m_ActivityLocation
				+ ", m_ActivityEndTime=" + m_ActivityEndTime + ", m_ReminderRequired=" + m_ReminderRequired + "]";
	}
}

@RestController
class ActivityRestController{
	
	@RequestMapping("/activites")
	String get()
	{
		ActivityInfo activity = new ActivityInfo();
		return activity.toString();
	}
	
}

