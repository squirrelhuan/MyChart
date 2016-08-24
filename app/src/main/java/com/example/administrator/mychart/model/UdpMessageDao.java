package com.example.administrator.mychart.model;

import java.io.Serializable;
import java.util.Date;

public class UdpMessageDao implements Serializable{
	
	private int id;
	private UserDao sendUser;
	private UserDao reciveUser;
	private Date time = new Date();
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserDao getSendUser() {
		return sendUser;
	}
	public void setSendUser(UserDao sendUser) {
		this.sendUser = sendUser;
	}
	public UserDao getReciveUser() {
		return reciveUser;
	}
	public void setReciveUser(UserDao reciveUser) {
		this.reciveUser = reciveUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
