package com.example.administrator.mychart.event.myevent;

import com.example.administrator.mychart.model.Message;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface DoorListener extends EventListener {
    public void messageEvent(MessageEvent event);
    public void logEvent(MessageEvent event);
}
