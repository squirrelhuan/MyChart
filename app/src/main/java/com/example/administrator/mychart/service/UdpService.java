package com.example.administrator.mychart.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.MyselfApplication;
import com.example.administrator.mychart.model.UdpData;
import com.example.administrator.mychart.model.Message;
import com.example.administrator.mychart.model.User;
import com.example.administrator.mychart.model.enums;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class UdpService extends BaseService {
	private static final String TAG = "CGQ";
	private static DatagramSocket client;
	private static Thread receive_Thread;
	private static Thread heartbeat_thread;
	private static Thread sendmessage_thread;
	private String IP_Server_Str = "192.168.1.114", Port_Server_Str = "5050";
	private boolean isRunning = true;
	private String recvStr, send_Str;
	public static List<Message> result = new ArrayList<Message>();
	private MsgBinder mbinder = new MsgBinder();

	/**
	 * onBind 是 Service 的虚方法，因此我们不得不实现它。 返回 null，表示客服端不能建立到此服务的连接。
	 */
	@Override
	public IBinder onBind(Intent intent) {
		super.onBind(intent);
		Log.v(TAG, "ServiceDemo onBind");
		return mbinder;
	}

	public class MsgBinder extends Binder {
		/**
		 * 获取当前Service的实例
		 * @return
		 */
		public UdpService getService(){
			return UdpService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		ReceiveMessage();
		Log.v(TAG, "ServiceDemo onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i(TAG, "ServiceDemo onStart");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "ServiceDemo onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	public void Login(String username,String password){
		UdpData udpData = new UdpData();
		udpData.setId(001);
		udpData.setRequestType(enums.requestType.login);
		Message message = new Message();
		message.setContent("ni hao a");
		User user1 = new User();
		user1.setUsername(username);
		user1.setPassword(password);
		user1.setId(2);
		User user2 = new User();
		user2.setId(3);
		message.setReciveUser(user2);
		message.setSendUser(user1);
		udpData.setMessage(message);
		sendMessage(udpData);
	}
	public void Logoff(String username,String password){

	}

	public void ReceiveMessage(){
		try {
			if(client==null) {
				client = new DatagramSocket();
			}
			if(receive_Thread==null){
				receive_Thread = new Thread(new ReceiveMessage_Thread());
				receive_Thread.start();
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}

	public static void SendHeartMessage(){
		if (heartbeat_thread==null) {
			heartbeat_thread = new Thread(new Heartbeat_Thread());
			heartbeat_thread.start();
		}
	}
	public static void sendMessage(final UdpData udata){
			sendmessage_thread = new Thread(new SendMessage_Thread(udata));
			sendmessage_thread.start();
	}

	// 心跳线程类
	static class Heartbeat_Thread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(6000);
					// 开门
					Log.i("CGQ", "service:send message...");

					UdpData udpData = new UdpData();
					udpData.setId(001);
					udpData.setRequestType(enums.requestType.sendData);
					Message message = new Message();
					message.setContent("ni hao a");
					User user1 = new User();
					user1.setUsername("978252909");
					user1.setPassword("112233");
					user1.setId(1);
					User user2 = new User();
					user2.setId(2);
					message.setReciveUser(user2);
					message.setSendUser(user1);
					udpData.setMessage(message);

					String sendStr = JSON.toJSONString(udpData);
					Log.d(TAG, "sendStr=" + sendStr);
					byte[] sendBuf;
					sendBuf = sendStr.getBytes();
					InetAddress addr = InetAddress
							.getByName("192.168.1.114");
					int port = 5050;
					DatagramPacket sendPacket = new DatagramPacket(sendBuf,
							sendBuf.length, addr, port);
					client.send(sendPacket);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.i(TAG, "ServiceDemo is running...");
			}
		}
	}

	// 发送数据线程类
	static class SendMessage_Thread implements Runnable {
		UdpData udata;
		SendMessage_Thread(UdpData udata){
			this.udata = udata;
		}
		@Override
		public void run() {
			try{
				UdpData udpData = udata;
				String sendStr = JSON.toJSONString(udpData);
				Log.d(TAG, "sendStr="+sendStr);
				byte[] sendBuf;
				sendBuf = sendStr.getBytes();
				InetAddress addr = InetAddress.getByName("192.168.1.114");
				int port = 5050;
				DatagramPacket sendPacket = new DatagramPacket(sendBuf,
						sendBuf.length, addr, port);
				client.send(sendPacket);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 接收数据线程类
	static class ReceiveMessage_Thread implements Runnable {
		@Override
		public void run() {
			try {
				byte[] recvBuf = new byte[1000];
				DatagramPacket recvPacket = new DatagramPacket(recvBuf,
						recvBuf.length);
				while(true){
					client.receive(recvPacket);
					String recvStr = new String(recvPacket.getData(), 0,
							recvPacket.getLength());
					Log.d(TAG,"收到:" + recvStr);
					UdpData udpData = JSONObject.parseObject(recvStr,
							UdpData.class);
					analysisData(udpData);

				}
			} catch (SocketException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static void analysisData(UdpData udpData){
		Message message = udpData.getMessage();
		result.add(message);
		//manager.HaveNewMessage(message);
		if(udpData.getMessage().getContent().equals("login success")){
			//登陆成功
			message.setContent("login success");
			MyselfApplication.manager.LoginMessage(message);
			Log.d(TAG,"登陆成功！！！");
		}else {
			MyselfApplication.manager.HaveNewMessage(message);
		}
	}
}
