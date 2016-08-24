package com.example.administrator.mychart.event.myevent;

import com.example.administrator.mychart.model.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventObject;


/*1.event object：事件状态对象，用于listener的相应的方法之中，作为参数，一般存在与listerner的方法之中

2.event source：具体的事件源，比如说，你点击一个button，那么button就是event source，要想使button对某些事件进行响应，你就需要注册特定的listener。

3.event listener：对每个明确的事件的发生，都相应地定义一个明确的Java方法。这些方法都集中定义在事件监听者（EventListener）接口中，这个接口要继承 java.util.EventListener。 实现了事件监听者接口中一些或全部方法的类就是事件监听者。
*/

/**
* 定义事件对象，必须继承EventObject
*/
public class MessageEvent extends EventObject {

	/*private String doorState = "";// 表示门的状态，有“开”和“关”两种*/
	private boolean haveNewMessage = false;
	private Message message;
	
	public MessageEvent(Object source, Message message) {
		super(source);
		this.message = message;
		this.haveNewMessage = true;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isHaveNewMessage() {
		return haveNewMessage;
	}

	public void setHaveNewMessage(boolean haveNewMessage) {
		this.haveNewMessage = haveNewMessage;
	}

	//触发事件方法
	public void doMethod(Class context, String methodname,Object[] args){

		// 调用getFullName()方法
		try {
			Method method = context.getMethod(methodname);
			method.invoke(context.getClass(),args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
