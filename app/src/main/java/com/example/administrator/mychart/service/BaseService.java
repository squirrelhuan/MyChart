package com.example.administrator.mychart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.ConversationFragment;
import com.example.administrator.mychart.MyselfApplication;
import com.example.administrator.mychart.activity.LoginActivity;
import com.example.administrator.mychart.event.myevent.DoorManager;

import java.util.Collection;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract class BaseService extends Service{

    //static DoorManager manager = new DoorManager();
   // private Collection listeners;

    public IBinder onBind(Intent intent) {
        Log.i("CGQ","service:new message...");
       // manager.addDoorListener(MainActivity.getInstance());// 给门1增加监听器
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("CGQ","service:onCreate...");
        //manager.addDoorListener(MainActivity.getInstance());// 给门1增加监听器
        if(MainActivity.conversationFragment!=null) {
           // manager.addDoorListener((ConversationFragment) MainActivity.conversationFragment);
        }
      //  manager.addDoorListener(LoginActivity.getInstance());
    }
}
