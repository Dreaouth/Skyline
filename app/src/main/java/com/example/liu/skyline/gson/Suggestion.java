package com.example.liu.skyline.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liu on 2017/4/5.
 */

public class Suggestion {
    @SerializedName("air")
    public Air air;             //空气状况
    @SerializedName("comf")
    public Comf comf;           //舒适状况
    @SerializedName("cw")
    public Cw cw;               //洗车指数
    @SerializedName("drsg")
    public Drsg drsg;           //穿衣指数
    @SerializedName("flu")
    public Flu flu;             //感冒指数
    @SerializedName("sport")
    public Sport sport;         //运动指数
    @SerializedName("trav")
    public Trav trav;           //旅行指数
    @SerializedName("uv")
    public Uv uv;               //紫外线指数
    public class Air{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;

    }
    public class Comf{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Cw{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Drsg{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Flu{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Trav{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
    public class Uv{
        @SerializedName("brf")
        public String status;
        @SerializedName("txt")
        public String info;
    }
}
