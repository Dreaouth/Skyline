---

title: Android天气软件'Skyline'设计说明
date: 2017-05-17 21:04:06
tags:

---
从开始学习Android到现在，差不多也有两个多月了，由于平时学校课比较多，所以也只能在课余时间和周末学习些自己的东西。虽然以后不打算从事安卓开发，也算是一时兴起，加上今年要做一个关于安卓的项目，于是就开始了Android的学习。  

我学Android用的书是郭霖的《第一行代码 第二版》，书里讲的很细致，而且把每个操作都说明的很详细，基本不会出现按照指示做然后出错的情况，忙了半天却依然出错这钟痛苦相信很多开始接触安卓的人都深有体会。我在学习这本书的过程中，可以自豪的说，我把这本书的所有讲解看了不止一遍，所有书上出现的代码都至少敲过一遍，比较喜欢Material design部分。  

该App是基于书上的应用程序CoolWeather的修改版，说是修改，其实相当于重写了整个App，只是界面布局仿照了以往的格式。  


---
## 整体说明
这是一个可以查询天气的App，用户可以根据自己的喜好添加自己的城市到城市列表，然后查看自己城市的天气情况。  

打开软件的首页面如下  

![](http://i.imgur.com/KZBXLV5.png) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![](http://i.imgur.com/UQIjk1v.png)  

点击天气详情按钮，会跳到一个显示详细内容的页面  

![](http://i.imgur.com/44AaCer.png)  

点击加号按钮，会进行到添加城市的页面  

![](http://i.imgur.com/S9bkiPo.png)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![](http://i.imgur.com/9IurY9v.png)  

在首页左划0或者点击向左的按钮，会弹出选择城市的菜单栏  

![](http://i.imgur.com/YJeQC0E.png)  

点击相应城市，就会切换到该城市，长按城市按钮，会弹出删除城市的选项，点击删除，该城市就从列表中删除  

![](http://i.imgur.com/ufKbtW7.png)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](http://i.imgur.com/DoHVcgE.png)  

## 一、数据获取
本天气应用有关的所有天气信息都是通过[和风天气](https://www.heweather.com/ "和风天气官网")的天气预报接口实现，查询和风天气官网的API了解，首先，注册一个开发者账号，然后通过自己的密钥访问天气预报接口，服务器会返回指定的JSON数据。然后对返回的JSON数据进行解析，再显示屏幕上。  
例如，查询现在的天气(now)，系统返回的数据是这样的格式。
```
{
    "HeWeather5": [
        {
            "now": {  //实况天气
                "cond": {  //天气状况
                    "code": "104",  //天气状况代码
                    "txt": "阴"  //天气状况描述
                },
                "fl": "11",  //体感温度
                "hum": "31",  //相对湿度（%）
                "pcpn": "0",  //降水量（mm）
                "pres": "1025",  //气压
                "tmp": "13",  //温度
                "vis": "10",  //能见度（km）
                "wind": {  //风力风向
                    "deg": "40",  //风向（360度）
                    "dir": "东北风",  //风向
                    "sc": "4-5",  //风力
                    "spd": "24"  //风速（kmph）
                }
            },
            "status": "ok"  //接口状态
        }
    ]
}
```  
然后，要将天气接口返回的JSON数据解析成实体类。这里，我使用了一个第三方库叫[gson](http://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.0)，通过gson库可以很简便地将json数据转化为java实体类，具体实现方式如下：

```
    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }
        catch (Exception e){
            return null;
        }
    }
```

所以，针对要查询的每一类信息，都需要建立不同的类来存放他们。例如，在这里，我建立了now类
```
public class Now {
    @SerializedName("fl")
    public String fl;      //体感温度
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
```
这样，就把网络上返回地jSON数据转化成实体类存放了。注意要用@SerializedName("")对它们的信息进行标识，否则无法正确地识别，导致无法获取到天气。  

## 二、前台界面显示  

### 未完待续。。。。