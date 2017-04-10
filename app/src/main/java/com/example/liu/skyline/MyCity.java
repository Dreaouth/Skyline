package com.example.liu.skyline;

/**
 * Created by liu on 2017/4/10.
 */

public class MyCity {
    private String cityName;
    private int buttonId;
    public MyCity(String cityName,int buttonId){
        this.cityName=cityName;
        this.buttonId=buttonId;
    }

    public String getCityName() {
        return cityName;
    }

    public int getButtonId() {
        return buttonId;
    }

}
