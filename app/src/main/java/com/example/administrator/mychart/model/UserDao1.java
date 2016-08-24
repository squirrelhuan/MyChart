package com.example.administrator.mychart.model;

import com.example.administrator.mychart.utils.PreferencesService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 用户数据操作类
 */
public class UserDao1 {

	private static String UserDaoTable = "Project41UserInfo";
	private static String UserInfoObject = "UserInfoObject";

	/**
	 * 获得用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		String username = preferences.getString("UserName", "");
		return username;
	}

	/**
	 * 获得密码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPassWord(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		String password = preferences.getString("PassWord", "");
		return password;
	}

	/**
	 * 保存用户名密码
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveUserPassWord(Context context, String password) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("PassWord", password);
		editor.commit();
	}

	/**
	 * 保存用户名密码
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveUserNamePassWord(Context context, String username,
			String password) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("UserName", username);
		editor.putString("PassWord", password);
		editor.commit();
	}

	/**
	 * 保存用户信息
	 * 
	 * @param context
	 * @param userinfo
	 */
	public static void saveUserInfo(Context context, String userinfo) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("UserInfo", userinfo);
		editor.commit();
	}

	/**
	 * 保存用户信息对象
	 * 
	 * @param context
	 * @param userinfo
	 */
	public static void saveUserInfo(Context context, LoginUser userinfo) {
		PreferencesService service = new PreferencesService(context);
		service.saveObject(UserInfoObject, userinfo);
	}

	/**
	 * 获取用户信息对象
	 * 
	 * @param context
	 * @return
	 */
	public static LoginUser getUserInfoModel(Context context) {
		LoginUser model = null;
		PreferencesService service = new PreferencesService(context);
		model = (LoginUser) service.deSerialization(UserInfoObject);
		return model;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserInfo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				UserDaoTable, Activity.MODE_PRIVATE);
		return preferences.getString("UserInfo", "");
	}
}
