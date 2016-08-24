package com.example.administrator.mychart.model;


public class UserDao{

	public static User findByID() {
		User user = new User();
		user.setId((int) (Math.random()*10));
		return user;
	}
	
}
