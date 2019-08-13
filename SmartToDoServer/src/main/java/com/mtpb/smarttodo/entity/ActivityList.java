package com.mtpb.smarttodo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "ActivityList")
public class ActivityList{
	
	public ActivityList()
	{
		
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getActivityListName() {
		return activityListName;
	}

	public String getItemName() {
		return itemName;
	}

	public String getLocation() {
		return location;
	}

	public String getDueDate() {
		return dueDate;
	}

	public boolean isReminderNeeded() {
		return reminderNeeded;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="listname")
	private String activityListName;
	
	@Column(name="itemname")
	private String itemName;
	
	@Column(name="location")
	private String location;
	
	@Column(name="duedate")
	private String dueDate;
	
	@Column(name="reminderneeded")
	private boolean reminderNeeded;
}

