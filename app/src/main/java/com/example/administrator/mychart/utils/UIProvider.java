package com.example.administrator.mychart.utils;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mychart.R;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明:UI工具类 
 * 编写日期:	2015-12-7
 * 作者: 魏巍
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UIProvider {

	private static UIProvider helper = null;

	/** 构造器 */
	private UIProvider() {

	}

	/** 单例模式 */
	public static UIProvider getInstance() {
		if (helper == null) {
			helper = new UIProvider();
		}

		return helper;
	}

	/** 计算 listview 的每一项的高度 return int */
	public int calculateListViewItemHeight(ListView mCateListView) {

		ListAdapter listAdapter = mCateListView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		int count = listAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i, null, mCateListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		return totalHeight / count;
	}

	/** 获取手机屏幕的分辨率(用来适配大小) */
	public int[] obtain(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		/** 获得手机屏幕的宽度和高度(单位为像素PX) */
		int[] location = { dm.widthPixels, dm.heightPixels };
		return location;
	}

	/** 重启应用程序 */
	public void reload(Activity mActivity) {

		Intent intent = mActivity.getIntent();
		mActivity.overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mActivity.finish();
		mActivity.overridePendingTransition(0, 0);
		mActivity.startActivity(intent);
	}

	/** 设置应用语言类型 */
	public void switchLanguage(Activity mActivity, String language) {
		Resources resources = mActivity.getResources();
		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		if (language.equals("en")) {
			config.locale = Locale.ENGLISH;
		} else {
			config.locale = Locale.SIMPLIFIED_CHINESE;
		}
		resources.updateConfiguration(config, dm);
	}

	/** 提示对话框 */
	public void Alert(Activity mActivity, int msg,
			DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.Yes, listener);
		builder.setNegativeButton(R.string.No,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	/** 弹出正在加载dialog */
	@SuppressWarnings("deprecation")
	public Dialog showLoadingAlert(Activity mActivity) {

		View view = mActivity.getLayoutInflater().inflate(
				R.layout.loading_layout, null);
		Dialog loadingAlert = new Dialog(mActivity,
				R.style.CustomTransparentDialogStyle);
		loadingAlert.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		Window window = loadingAlert.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = mActivity.getWindowManager().getDefaultDisplay().getWidth();
		wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		/** 以下这两句是为了保证可以水平和竖直方向上满屏 */
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
		/** 设置显示位置 */
		loadingAlert.onWindowAttributesChanged(wl);
		/** 返回键无效 */
		loadingAlert.setCancelable(false);

		return loadingAlert;
	}

	/** 弹出语言选择dialog */
	public Dialog showLanguageSwitchAlert(Activity mActivity,
			OnClickListener enListener, OnClickListener chListener) {

		LayoutInflater inflater = mActivity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_select_lanuage, null);
		RelativeLayout english = (RelativeLayout) layout
				.findViewById(R.id.select_english);
		RelativeLayout chinese = (RelativeLayout) layout
				.findViewById(R.id.select_chinese);
		Dialog mDialog = new Dialog(mActivity,
				R.style.CustomTransparentDialogStyle);
		/** 设置点击屏幕外边菜单消失 */
		mDialog.setCanceledOnTouchOutside(true);
		english.setOnClickListener(enListener);
		chinese.setOnClickListener(chListener);
		mDialog.setContentView(layout);

		return mDialog;
	}

	/** 提示对话框 */
	public void Alert(Context context, String msg,
			DialogInterface.OnClickListener listener1,
			DialogInterface.OnClickListener listener2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setCancelable(false);
		builder.setPositiveButton("OK", listener1);
		builder.setNegativeButton("NO", listener2);
		if(!((Activity) context).isFinishing()){
			builder.show();
		}
	}

	/** 获取年、月、日、时、分、秒 */
	public int[] getDateArray() {
		int[] local = new int[5];
		int year, month, day, hour, min;
		Calendar calendar = Calendar.getInstance();

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		min = calendar.get(Calendar.MINUTE);

		local = new int[] { year, month, day, hour, min };

		return local;
	}

	/** 提示窗口 */
	@SuppressWarnings("deprecation")
	public void showTipAlert(Activity mActivity, int title, int msg) {

		View view = mActivity.getLayoutInflater().inflate(R.layout.tip_layout,
				null);

		final Dialog tipAlert = new Dialog(mActivity,
				R.style.CustomTransparentDialogStyle);

		tipAlert.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		TextView tip_title = (TextView) view
				.findViewById(R.id.tip_layout_title);// 提示标题

		TextView tip_message = (TextView) view
				.findViewById(R.id.tip_layout_message);// 提示信息

		Button tip_button = (Button) view.findViewById(R.id.tip_layout_button);

		tip_title.setText(title);// 设置提示标题
		tip_message.setText(msg);// 设置提示信息

		tip_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tipAlert.dismiss();
			}
		});

		Window window = tipAlert.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = mActivity.getWindowManager().getDefaultDisplay().getWidth();
		wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		/** 以下这两句是为了保证可以水平和竖直方向上满屏 */
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
		/** 设置显示位置 */
		tipAlert.onWindowAttributesChanged(wl);
		/** 返回键无效 */
		tipAlert.setCancelable(false);

		tipAlert.show();
	}

	/** 提示窗口 */
	@SuppressWarnings("deprecation")
	public void showTipAlert(Activity mActivity, String title, String msg) {

		View view = mActivity.getLayoutInflater().inflate(R.layout.tip_layout,
				null);

		final Dialog tipAlert = new Dialog(mActivity,
				R.style.CustomTransparentDialogStyle);

		tipAlert.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		TextView tip_title = (TextView) view
				.findViewById(R.id.tip_layout_title);// 提示标题

		TextView tip_message = (TextView) view
				.findViewById(R.id.tip_layout_message);// 提示信息

		Button tip_button = (Button) view.findViewById(R.id.tip_layout_button);

		tip_title.setText(title);// 设置提示标题
		tip_message.setText(msg);// 设置提示信息

		tip_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tipAlert.dismiss();
			}
		});

		Window window = tipAlert.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = mActivity.getWindowManager().getDefaultDisplay().getWidth();
		wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		/** 以下这两句是为了保证可以水平和竖直方向上满屏 */
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
		/** 设置显示位置 */
		tipAlert.onWindowAttributesChanged(wl);
		/** 返回键无效 */
		tipAlert.setCancelable(false);

		tipAlert.show();
	}
}
