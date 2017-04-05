package com.example.liu.skyline.db;

import org.litepal.crud.DataSupport;

/**
 * Created by liu on 2017/4/4.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int CityCode;
    private int provinceId;  //记录当前市所属省的Id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityCode() {
        return CityCode;
    }

    public void setCityCode(int cityCode) {
        CityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

}
