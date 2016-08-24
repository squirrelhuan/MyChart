package com.example.administrator.mychart.event.myevent;

public class DoorListener1 implements DoorListener {

	public void messageEvent(MessageEvent event) {
		if (event.isHaveNewMessage()) {
            System.out.println("门1打开");
        } else {
            System.out.println("门1关闭");
        }
	}

    @Override
    public void logEvent(MessageEvent event) {

    }
}
