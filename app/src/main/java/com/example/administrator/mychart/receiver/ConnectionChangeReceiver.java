package com.example.administrator.mychart.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.MyselfApplication;

/**
 * Created by Administrator on 2016/8/20.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            MyselfApplication.isNetworkConnected = false;
           // if(context instanceof BaseActy){
           //     ((BaseActy)context).TurnToShowToast("当前网络不可以用，请检查网络。");
           // }
            //改变背景或者 处理网络的全局变量
        }else {
            MyselfApplication.isNetworkConnected = true;
           // if(context instanceof BaseActy){
           // ((BaseActy)context).TurnToShowToast("网络已连接。");
           // }
            //改变背景或者 处理网络的全局变量
        }
    }

    class MyThread extends Thread{
        Context context;
        MyThread(Context context){
            this.context = context;
        }
        @Override
        public void run() {
            ((BaseActy)context).ShowCustomToast("网络不可以用");
        }
    }
}