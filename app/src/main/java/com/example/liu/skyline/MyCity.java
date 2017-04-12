package com.example.liu.skyline;

import org.litepal.crud.DataSupport;

/**
 * Created by liu on 2017/4/10.
 */

public class MyCity extends DataSupport{
    private String cityName;
    private int buttonId;
    private String countyId;
    public MyCity(String cityName,int buttonId,String countyId){
        this.cityName=cityName;
        this.buttonId=buttonId;
        this.countyId=countyId;
    }

    public String getCityName() {
        return cityName;
    }

    public int getButtonId() {
        return buttonId;
    }

    public String getCountyId() {
        return countyId;
    }
}
