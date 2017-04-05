package com.example.liu.skyline.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liu on 2017/4/5.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
