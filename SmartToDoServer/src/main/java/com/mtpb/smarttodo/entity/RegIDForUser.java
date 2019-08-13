package com.mtpb.smarttodo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "RegIdForUser")
public class RegIDForUser {
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="regid")
	private String regid;
}
