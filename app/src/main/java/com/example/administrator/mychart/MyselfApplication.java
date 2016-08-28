package com.example.administrator.mychart;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.activity.LoginActivity;
import com.example.administrator.mychart.database.DatabaseUtil;
import com.example.administrator.mychart.event.myevent.DoorManager;
import com.example.administrator.mychart.service.UdpService;
import com.example.administrator.mychart.utils.PreferencesService;
import com.example.administrator.mychart.utils.ToastUtils;

import java.util.Collection;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明:自定义application 
 * 编写日期:	2015-12-16
 * 作者: 魏巍
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class MyselfApplication extends Application {
	private static MyselfApplication mAppApplication;
	private static ToastUtils toastUtils;
	protected PreferencesService service;
	protected DatabaseUtil mDBUtil;
	public static UdpService udpService;
	public static int page;
	public static boolean isNetworkConnected;
	private static DoorManager manager = new DoorManager();
	private Collection listeners;

	@Override
	public void onCreate() {
		super.onCreate();
		mAppApplication = this;
		toastUtils = new ToastUtils(this);
		service = new PreferencesService(this);
		mDBUtil = new DatabaseUtil(this);
		Log.d("CGQ","MyselfApplication onCreate...");
	}

	/** TODO 获取Application */
	public static MyselfApplication getApp() {
		return mAppApplication;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		// 整体摧毁的时候调用这个方法
	}

	public ToastUtils getToastUtils() {
		return toastUtils;
	}

	public static DoorManager getManager(){return manager;}
}
