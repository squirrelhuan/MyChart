package com.example.administrator.mychart.model;

public class enums{
public static enum requestType {
login(1),logoff(2),getData(3),sendData(4);
    private int value;
    requestType(int value){
       this.value = value;
    }
}
}