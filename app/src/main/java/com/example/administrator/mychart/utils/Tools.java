package com.example.administrator.mychart.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.R;


/**
 * 工具类
 * 
 * @author weiwei
 * 
 */
public class Tools {

	private static Tools tools = null;

	private Tools() {

	}

	/** 单例模式 */
	public static Tools getInstance() {
		if (tools == null) {
			tools = new Tools();
		}

		return tools;
	}

	/**
	 * 短暂显示Toast消息
	 * 
	 * @param context
	 * @param message
	 */
	public void toastShort(Context context, String message) {

		/*LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.custom_toast, null);
		TextView text = (TextView) view.findViewById(R.id.toast_message);
		text.setText(message);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setView(view);
		toast.show();*/
	}

	/**
	 * 显示Toast长通知
	 * 
	 * @param context
	 * @param resID
	 */
	public void toastLong(Context context, int resID) {

		/*LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.custom_toast_cgq, null);
		TextView text = (TextView) view.findViewById(R.id.toast_message);
		text.setText(resID);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setView(view);
		toast.show();*/
	}

	/**
	 * 显示Toast长通知
	 * 
	 * @param context
	 * @param resID
	 */
	public void toastShort(Context context, int resID) {

		/*LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.custom_toast_cgq, null);
		TextView text = (TextView) view.findViewById(R.id.toast_message);
		text.setText(resID);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setView(view);
		toast.show();*/
	}

	// 将图片保存到本地时进行压缩
	public void compressBmpToFile(String filepath, File file) {

		Bitmap bmp = BitmapFactory.decodeFile(filepath);// 此时返回bm为空

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// 个人喜欢从80开始,
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 800) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 文件复制
	public void doCopyFile(String filepath, String path) throws IOException {

		File srcFile = new File(filepath);
		File destFile = new File(path);
		FileInputStream input = new FileInputStream(srcFile);
		try {
			FileOutputStream output = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}
			} finally {
				try {
					if (output != null) {
						output.close();
					}
				} catch (IOException ioe) {
				}
			}
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	/* 获取随机的字符串 */
	public String getRandomString() {

		StringBuffer buffer = new StringBuffer();

		int n = -1;

		char chars[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z' };

		int len = chars.length;

		for (int i = 0; i < 10; i++) {
			n = (int) (Math.random() * len);
			buffer.append(chars[n]);
		}
		return buffer.toString();
	}

	/* 获取手机唯一的机器码(加入权限 android.permission.READ_PHONE_STATE)读取手机状态 */
	public String getAndroidUniqueId(Activity mActivity) {

		String tmDevice, tmSerial, androidId;

		TelephonyManager tm = (TelephonyManager) mActivity.getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

		tmDevice = "" + tm.getDeviceId();

		tmSerial = "" + tm.getSimSerialNumber();

		androidId = ""
				+ android.provider.Settings.Secure.getString(
						mActivity.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

		String uniqueId = deviceUuid.toString();

		return uniqueId;
	}

	/* 获取版本号 */
	public String getVersion(Context mContext) {
		try {
			PackageManager manager = mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					mContext.getPackageName(), 0);
			String appVersion = "V" + info.versionName;
			return appVersion;
		} catch (Exception e) {
			return "没有找到相关版本";
		}
	}

	/* 计算字符个数 @param c 字符 */
	public static long calculateLength(CharSequence c) {

		double len = 0;

		for (int i = 0; i < c.length(); i++) {
			int tmp = (int) c.charAt(i);
			if (tmp > 0 && tmp < 127) {
				len += 0.5;
			} else {
				len++;
			}
		}

		return Math.round(len);
	}
}
