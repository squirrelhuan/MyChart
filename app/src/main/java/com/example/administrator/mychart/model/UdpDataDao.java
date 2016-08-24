package com.example.administrator.mychart.model;

import java.io.Serializable;

public class UdpDataDao implements Serializable{

	private int id;
	private enums requestType;
	private Message uudpMessageDao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public enums getRequestType() {
		return requestType;
	}
	public void setRequestType(enums requestType) {
		this.requestType = requestType;
	}
	public Message getUudpMessageDao() {
		return uudpMessageDao;
	}
	public void setUudpMessageDao(Message uudpMessageDao) {
		this.uudpMessageDao = uudpMessageDao;
	}
	
	
}
