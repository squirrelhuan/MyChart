package com.example.administrator.mychart.model;

import java.io.Serializable;

public class UdpData implements Serializable{

	private int id;
	private enums.requestType requestType;
	private Message message;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public enums.requestType getRequestType() {
		return requestType;
	}
	public void setRequestType(enums.requestType requestType) {
		this.requestType = requestType;
	}
	
	
}
