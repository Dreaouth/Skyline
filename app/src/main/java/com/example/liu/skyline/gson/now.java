package com.example.liu.skyline.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liu on 2017/4/5.
 */

public class Now {
    @SerializedName("f1")
    public String f1;      //体感温度
    @SerializedName("hum")
    public String hum;     //湿度
    @SerializedName("pcpn")
    public String pcpn;    //降雨量
    @SerializedName("pres")
    public String pres;    //气压
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("vis")
    public String vis;     //能见度
    @SerializedName("wind")
    public Wind wind;
    public class Wind{
        @SerializedName("deg")
        public String deg; //风向(角度)
        @SerializedName("dir")
        public String dir; //风向(方向)
        @SerializedName("sc")
        public String sc;  //风力等级
        @SerializedName("spd")
        public String sqd; //风速(Kmph)
    }
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;//天气
    }

}
