package com.example.administrator.mychart.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginUser implements Serializable {

	private String Id;
	private String RealName;
	private String Url;
	private int CanView;
	private String RoleId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public int getCanView() {
		return CanView;
	}

	public void setCanView(int canView) {
		CanView = canView;
	}

	public String getRoleId() {
		return RoleId;
	}

	public void setRoleId(String roleId) {
		RoleId = roleId;
	}

}
