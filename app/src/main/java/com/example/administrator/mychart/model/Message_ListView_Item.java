package com.example.administrator.mychart.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/6.
 */
public class Message_ListView_Item {
    private User sendUser;
    private String content;
    private int count;
    private Date time;

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
