package com.example.administrator.mychart.event.myevent;

import com.example.administrator.mychart.model.Message;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class DoorManager {
	private Collection listeners;
	/**
     * 添加事件
     * 
     * @param listener
     *            DoorListener
     */
    public void addDoorListener(DoorListener listener) {
        if (listeners == null) {
            listeners = new HashSet();
        }
        listeners.add(listener);
    }

    /**
     * 移除事件
     * 
     * @param listener
     *            DoorListener
     */
    public void removeDoorListener(DoorListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }

    /**
     * 触发新消息事件
     */
    public void HaveNewMessage(Message message) {
        if (listeners == null)
            return;
        MessageEvent event = new MessageEvent(this, message);
        notifyListeners(event,EventType.NewMessage);
    }

    /**
     * 用户登陆事件
     */
    public void LoginMessage(Message message) {
        if (listeners == null)
            return;
        MessageEvent event = new MessageEvent(this, message);
        notifyListeners(event,EventType.Log);
    }

    /**
     * 触发关门事件
     */
   /* protected void fireWorkspaceClosed() {
        if (listeners == null)
            return;
        MessageEvent event = new MessageEvent(this, false);
        notifyListeners(event);
    }*/

    /**
     * 通知所有的DoorListener
     */
    private void notifyListeners(MessageEvent event ,EventType type) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            DoorListener listener = (DoorListener) iter.next();
            switch (type.toInt()){
                case 0:
                    listener.logEvent(event);
                    break;
                case 1:
                    listener.messageEvent(event);
                    break;
            }

        }
    }
    enum EventType{
        Log(0),NewMessage(1);
        private int value;
        EventType(int value){
            this.value = value;
        }
        public int toInt(){
            return value;
        }
    }
}
