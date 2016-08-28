package com.example.administrator.mychart;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.event.myevent.DoorListener;
import com.example.administrator.mychart.popupwindow.ConversationPopupWindow;
import com.example.administrator.mychart.popupwindow.LoginPopupWindow;
import com.example.administrator.mychart.popupwindow.SelectPhotoPopupWindow;
import com.example.administrator.mychart.receiver.ConnectionChangeReceiver;
import com.example.administrator.mychart.service.UdpService;
import com.example.administrator.mychart.utils.PreferencesService;
import com.example.administrator.mychart.utils.ToastUtils;
import com.example.administrator.mychart.utils.Tools;
import com.example.administrator.mychart.utils.UIProvider;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明:BaseActy  基类
 * 编写日期:	2015-11-9
 * 作者: 魏巍
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class BaseActy extends Activity {

	/** 滑动返回是否打开 true--打开 false--关闭 */
	protected boolean M_SIDE_BACK_STATE = false;
	MyselfApplication app;
	ToastUtils toastUtils;
	/** 滑动手势 */
	private GestureDetector mDetector;
	/** 通过单例模式获取工具类的实例对象 */
	protected Tools tools = Tools.getInstance();
	/** 上下文环境 */
	protected static Context mContext;
	protected Activity mActivity;
	protected PreferencesService service;
	protected String TAG = "CGQ";
	protected ConnectionChangeReceiver myReceiver;

	protected static LayoutInflater inflater_toast;
	protected static View layout_toast;
	protected static LoginPopupWindow loginPopupWindow;
	protected static ConversationPopupWindow conversationPopupWindow;

	/** UI界面管理类 */
	protected UIProvider provider = UIProvider.getInstance();
	protected UdpService udpService;
	protected ServiceConnection mconn = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected");
		}
		public void onServiceConnected(ComponentName name, IBinder service) {
			//返回一个MsgService对象
			Log.v(TAG, "onServiceConnected");
			udpService = ((UdpService.MsgBinder)service).getService();
			MyselfApplication.udpService = udpService;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加该Activity到ExitApplication对象实例容器中
		//ExitApplication.getInstanse().addActivity(this);
		app = (MyselfApplication) getApplication();
		toastUtils = app.getToastUtils();
		inflater_toast = getLayoutInflater();
		layout_toast = inflater_toast.inflate(R.layout.custom_toast,
				(ViewGroup) findViewById(R.id.llToast));

		mContext = this;
		mActivity = this;

		service = new PreferencesService(mContext);
		/** 获取本地保存的语言类型 */
		// switchLanguage(service.getValue("language", "zh"));
		provider.switchLanguage(mActivity, service.getValue("language", "zh"));
		/** 保存设置语言的类型 */
		service.save("language", service.getValue("language", "zh"));

		/** 获取手势监听器 */
		mDetector = new GestureDetector(this, listener);

		//(new Intent(mContext, UdpService.class));
		//绑定Service
		Intent intent = new Intent(mContext, UdpService.class);
		bindService(intent, mconn, mContext.BIND_AUTO_CREATE);
        //注册广播
		registerReceiver();

		//manager.addDoorListener(MainActivity.getInstance());// 给门1增加监听器
		if (MainActivity.conversationFragment != null) {
			MyselfApplication.getManager().addDoorListener((ConversationFragment) MainActivity.conversationFragment);
		}
		if (mContext instanceof DoorListener) {
			MyselfApplication.getManager().addDoorListener((DoorListener) mContext);
		}
}
    //注册广播
	private void registerReceiver() {
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver = new ConnectionChangeReceiver();
		this.registerReceiver(myReceiver, filter);
	}
	//取消注册
	private  void unregisterReceiver(){
		this.unregisterReceiver(myReceiver);
	}
	@Override
	protected void onDestroy() {
		unbindService(mconn);
		unregisterReceiver();
		super.onDestroy();
	}
	/** 设置应用语言类型 */
	protected void switchLanguage(String language) {
		provider.switchLanguage(mActivity, language);
		/** 保存设置语言的类型 */
		service.save("language", language);

		Intent it = new Intent(this, MainActivity.class); // MainActivity是你想要重启的activity
		it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(it);
	}

	/** 滑动手势监听事件 */
	private SimpleOnGestureListener listener = new SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getRawX() - e1.getRawX() > 50 && M_SIDE_BACK_STATE) {
				// 右滑
				finish();// 结束当前页面
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	};

	/** ScrollView和GestureDetector触碰事件冲突的解决 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mDetector.onTouchEvent(ev); // 让GestureDetector响应触碰事件
		super.dispatchTouchEvent(ev); // 让Activity响应触碰事件
		return false;
	}

	/** 显示Toast短通知 */
	protected void toastShort(CharSequence msg) {
		showToast(msg.toString());
		// tools.toastShort(this, (String) msg);
	}

	/** 显示Toast长通知 */
	protected void toastLong(int resID) {
		showToast("" + resID);
		// tools.toastLong(mContext, resID);
	}

	/** 显示Toast短通知 */
	protected void toastShort(int resID) {
		showToast("" + resID);
		// tools.toastShort(mContext, resID);
	}

	public void showToast(String str) {
		toastUtils.showToast(str);
	}
	public static void TurnToShowToast(String content){
		MyHandler mhandler = new MyHandler();
		Message message = new Message();
		message.obj = content;
		mhandler.sendMessage(message);
	}
	/**
	 * 接受消息，处理消息 ，此Handler会与当前主线程一块运行
	 * */
	static class MyHandler extends Handler {
		public MyHandler() {
		}
		// 子类必须重写此方法，接受数据
		@Override
		public void handleMessage(Message msg) {
			ShowCustomToast((String) msg.obj);
		}
	}
	public static void ShowCustomToast(String content){
		Toast toast = null;
		LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layout_toast.setLayoutParams(layparams);
		Log.d("CGQ","layout_t"+layout_toast);
		ImageView image = (ImageView) layout_toast
				.findViewById(R.id.iv_toast);
		image.setImageResource(R.drawable.gqi);
		TextView text = (TextView) layout_toast.findViewById(R.id.tv_content);
		text.setText(content);
		toast = new Toast(mContext);
		toast.setGravity(Gravity.TOP /*| Gravity.TOP*/, LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout_toast);
		toast.show();
	}

	public Dialog loadingAlert;

	/** 调用加载Alert */
	protected void showLoadingAlert() {
		loadingAlert = provider.showLoadingAlert(mActivity);
		loadingAlert.setCanceledOnTouchOutside(true);
		loadingAlert.setCancelable(true);
		loadingAlert.show();
	}

	/** 隐藏Alert */
	protected void dismissAlert() {
		if (loadingAlert != null && loadingAlert.isShowing()) {
			loadingAlert.dismiss();
		}
	}

	private TextView unreadMessage;

	/** 弹出语言选择菜单 */
	private Dialog switchLanguageAlert;

	/** 弹出语言选择菜单 */
	protected void showSwitchLanguageAlert() {
		if (switchLanguageAlert == null) {
			switchLanguageAlert = provider.showLanguageSwitchAlert(mActivity, enListener, chListener);
		}
		switchLanguageAlert.show();
	}

	/** 切换英文操作 */
	private OnClickListener enListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switchLanguage("en");
			switchLanguageAlert.dismiss();
		}
	};

	/** 切换中文操作 */
	private OnClickListener chListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switchLanguage("zh");
			switchLanguageAlert.dismiss();
		}
	};

	/**
	 * 隐藏键盘
	 * 
	 * @param editText
	 */
	protected void hideInputMethod(EditText editText) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (null != imm) {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}
    /** 选择照片dialog */
	public void ShowDialog_SelectPhoto(View pview){
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,null);
		SelectPhotoPopupWindow slePhotoPopupWindow = new SelectPhotoPopupWindow();
		slePhotoPopupWindow.getView(this, view,pview);
	}
	/** 登陆时弹出的dialog */
	public static void ShowDialog_Login(View pview){
		View view = inflater_toast.inflate(R.layout.login_popupwindows,null);
		loginPopupWindow = new LoginPopupWindow();
		loginPopupWindow.getView(mContext, view,pview);
	}
	/** 登陆时弹出的dialog */
	public static void ShowDialog_ConversationMenu(View pview){
		View view = inflater_toast.inflate(R.layout.login_popupwindows,null);
		conversationPopupWindow = new ConversationPopupWindow();
		conversationPopupWindow.getView(mContext, view,pview);
	}
}
