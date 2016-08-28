package com.example.administrator.mychart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mychart.event.myevent.DoorListener;
import com.example.administrator.mychart.utils.PreferencesService;
import com.example.administrator.mychart.utils.ToastUtils;
import com.example.administrator.mychart.utils.Tools;
import com.example.administrator.mychart.utils.UIProvider;
import com.example.administrator.mychart.widgets.ScreenInfo;
import com.example.administrator.mychart.widgets.WheelMain;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明:fragment 基类 
 * 编写日期:	2015-12-24
 * 作者: 魏巍
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class BaseFragment extends Fragment {

	MyselfApplication app;
	ToastUtils toastUtils;

	/** fragment所处的activity */
	protected Activity mActivity;
	/** 工具类 （单例模式） */
	protected Tools tools = Tools.getInstance();
	/** 数据保存管理类 */
	protected PreferencesService service;
	/** UIProvider */
	protected UIProvider provider;
	/** 时间选择器 */
	public WheelMain wheelMain;
	/** 当前日期 */
	protected String currentDate, currentDate1;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		/** 绑定activity */
		this.mActivity = activity;
		provider = UIProvider.getInstance();
		if(this instanceof DoorListener) {
			MyselfApplication.getManager().addDoorListener((DoorListener) this);
			Log.i("CGQ","fragment doorlistener");
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		service = new PreferencesService(mActivity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return setContentUI(inflater, container);
	}

	/** TODO 加载视图 */
	public abstract View setContentUI(LayoutInflater inflater, ViewGroup container);

	/** TODO 视图创建之后回调接口 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		app = (MyselfApplication) this.getActivity().getApplication();
		toastUtils = app.getToastUtils();
		init();
		control();
	}

	//	/** TODO 显示Toast短通知 */
	//	protected void toastShort(CharSequence msg) {
	//		showToast(msg.toString());
	////		tools.toastShort(this, (String) msg);
	//	}
	//
	//	/** TODO 显示Toast长通知 */
	//	protected void toastLong(int resID) {
	//		showToast(""+resID);
	////		tools.toastLong(mContext, resID);
	//	}
	//
	//	/** TODO 显示Toast短通知 */
	//	protected void toastShort(int resID) {
	//		showToast(""+resID);
	////		tools.toastShort(mContext, resID);
	//	}

	public void showToast(String str) {
		toastUtils.showToast(str);
	}

	/* .....比如初始化一些数据 */
	public abstract void init();

	/* .....比如初始化一些控件..... */
	public abstract void control();

	/** 创建时间选择框 */
	protected View createDateSelectAlert() {
		int[] local = provider.getDateArray();
		int year = local[0], month = local[1], day = local[2], hour = local[3], min = local[4];
		LayoutInflater inflater = LayoutInflater.from(mActivity);

		View timepickerview = inflater.inflate(R.layout.timepicker, null);
		timepickerview.setBackgroundColor(mActivity.getResources().getColor(R.color.deepblue));
		ScreenInfo screenInfo = new ScreenInfo(mActivity);

		wheelMain = new WheelMain(timepickerview, 0);
		wheelMain.screenheight = screenInfo.getHeight();
		wheelMain.initDateTimePicker(year, month, day, hour, min);

		return timepickerview;
	}

	/** 弹出时间选择框 */
	protected void showDateSelectAlert(DialogInterface.OnClickListener listener) {

		View timepickerview = createDateSelectAlert();
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setTitle("选择时间").setView(timepickerview).setPositiveButton("确定", listener)
				.setNegativeButton("取消", null);

		if (!mActivity.isFinishing()) {
			builder.show();
		}
	}

	private Dialog loadingAlert;

	/** TODO 调用加载Alert */
	protected void showLoadingAlert() {
		loadingAlert = UIProvider.getInstance().showLoadingAlert(mActivity);
		loadingAlert.show();
	}

	/** TODO 隐藏Alert */
	protected void dismissAlert() {
		if (loadingAlert != null && loadingAlert.isShowing()) {
			loadingAlert.dismiss();
		}
	}
}
