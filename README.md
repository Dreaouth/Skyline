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
#### **注意：由于官方返回的数据会有不定时的改变，所以程序有时会因无法获取数据导致崩溃！！**

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

主页面我采用了FrameLayout呈现，FrameLayout是一个比较简单的布局方式所有放在布局中的控件，都按照层次堆积在屏幕的左上角，后加进来的控件覆盖前面的控件。这样我在FrameLayout中添加了一个ImageView，并且将它的宽和高都设置成为match_parent,然后将FrameLayout的背景色设置成colorPrimary,这样IamgeView就成为背景图片了。
由于页面显示的内容比较多，这里我将显示的所有内容使用ScrollView放在一起，可以实现向下滚动查看屏幕外的功能。另外，基于当今比较流行的MaterialDesign，我又添加了一个左划显示菜单栏的功能，只需在ScrollView外部添加DrawerLayout，然后在后面添加一个fragment，在其layout_gravity属性中设置为start，就能实现左划弹出菜单栏的功能。
由于主页面显示的内容过多，这里我并没有将它们放在一个xml文件中，而是在一个LinearLayout中将它们分成几个部分include进来。具体实现方法如下：

```
    <ScrollView
        android:id="@+id/weather_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="none"
        android:overScrollMode="never">
        <LinearLayout
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fitsSystemWindows="true">
            <include layout="@layout/title"/>
            <include layout="@layout/now"/>
            <include layout="@layout/forecast"/>
            <include layout="@layout/aqi"/>
            <include layout="@layout/suggestion"/>
        </LinearLayout>
    </ScrollView>

```  

关于界面布局方面其实并没有太多要说的，主要都是个人的想象。本人比较喜欢将显示的内容设置成半透明，然后在后面加上一张背景图片的样式。其实对于安卓来说，界面应该是比较简单的一部分，最显著的一点就是你在代码中更改后，可以直接测试运行，如果不符合自己的想法可以接着改，知道让自己满意。而对于内部逻辑处理来说，一个小小的更改，可能程序就会发生异常，甚至根本跑不起来，在这一点上自己也踩过很多坑。

## 三、后台逻辑处理

### 主页面跳转
首先，当程序运行时，我在oncreate方法中读取数据库，这里的Mycity类和数据库中的表是相对应的，当查询到MyCity.size()大小为0（即数据为空）时，进入到ChooseAreaFragment，在这里可以选择自己的城市信息，然后系统会将该城市添加到城市列表中并存储到数据库中，然后会自动跳转到主页面。如果MyCity.size()>0时，系统会自动跳转到主页面并显示列表中第一个城市的信息。  

### 添加城市到左划菜单栏中
由于自己之前并没有做过什么比较大型的应用，只是局限于一些算法的实现，所以在进行编写该App时并没有考虑周全，导致走了很多的坑。由于逻辑处理不清晰，所以这也是我在制作本App时遇到的最大的阻碍，所以以后在写程序前，一定要先理清程序的脉络，知道每个步骤都要干什么，然后再开始实行。
在点击加号按钮添加城市时，要在一个Fragment（主页面）上覆盖另一个Fragment（添加城市的列表），并支持点击返回键后返回上一个Fragment（主页面）的功能，这里我使用FragmentManger，从FragmentManager获得一个FragmentTransaction的实例，FragmentTransaction可以对fragment进行添加,移除,替换,以及执行其他动作，然后，要给activity应用事务,，必须调用commit()。在调用commit()之前,，你可能想调用 addToBackStack()，将事务添加到一个fragment事务的back stack，这个back stack由activity管理,，并允许用户通过按下BACK按键返回到前一个fragment状态。具体实现方法如下：

```
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.add_layout,new ChooseAreaFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
  
```  

然后，最关键的部分来了，当点击了想要添加的城市之后，如何将它们显示到主页面中并在左划列表中添加，这里我的逻辑是这样的:
- 判断目前的Fragment属于哪个Activity，如果属于MainActivity则表示第一次运行该App,执行上述主页面跳转逻辑，否则执行添加列表的功能。
- 首先，通过该城市的ID获取到该城市的实例，将其存到数据库中。
- 将该城市信息添加到左划列表中，并弹出一个Toast显示“添加成功”。
- 执行FragmentManger的出栈方法（返回主页面）。
- 自动执行上拉功能，并使用该城市ID重新向服务器接口请求并重新加载页面。

虽然在理清了脉络后感觉也并不是很繁琐，但如果实现没有想明白直接去做的话也并不是那么容易。比如对于存储数据库和添加列表来说，如果它们的顺序对调的话，就会产生存储索引无法对应的问题，这样在查看时会显示错误，删除城市时程序也会因为无法对应而闪退。
具体实现方法如下：

```
      String WeatherId=countyList.get(position).getWeatherId();
      if (getActivity() instanceof MainActivity){
         ......
      }
      else {
          MyCity myCity=new MyCity(countyList.get(position).getCountyName(),R.id.city_delete,WeatherId);
          myCity.save();
          MyCityFragment.cityList.add(myCity);
          Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
          getFragmentManager().popBackStack();
          WeatherActivity activity=(WeatherActivity)getActivity();
          activity.swipeRefresh.setRefreshing(true);
          activity.requestWeather(countyList.get(position).getWeatherId());
      }
  }
  
```  

### 从左划菜单栏中删除城市
左划菜单栏也是一个Fragment，在这里我用listview将每个城市加载到一个列表中，然后我又设置了一个长按删除功能，这里的逻辑和添加城市大体相同：
- 当长按listview中的一个城市项时，将删除图标设置为可见。
- 点击删除图标时，首先根据该城市的ID删除数据库中该城市的信息。
- 然后在citylist列表中删除该城市，并弹出Toast显示“删除成功”。
- 使用notifyDataSetChanged()方法刷新列表

具体实现代码如下：

```
    holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DataSupport.deleteAll(MyCity.class,"countyId = ?",myCities.get(position).getCountyId());
            myCities.remove(position);
            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }
    });
  
```  

有一点需要特别注意，删除数据库和在myCities列表的删除顺序一定不能对调，因为如果先从列表中删除该城市，id就会自动减一，所以在数据库删除中就查询不到该城市，会造成删除错误，有时还会因为无法删除城市而直接造成程序崩溃，这与数据库中的信息有关。所以，程序执行的逻辑很重要，当初就是因为这个bug卡了好长时间，最后才发现是如此愚蠢的问题2333...

## 四、后台自动更新数据服务

为了让自己的天气App更加只能化，我又新加了后台自动更新天气的功能。我创建了一个在后台自动运行的服务，在服务中，我设置每隔8小时自动更新天气信息并重新获取必应图片，然后将它们存储在SharedPreferences，当再次打开App时，会优先读取SharedPreferences中的信息。具体实现代码如下：
```
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int aHour=8*60*60*1000;    //8小时的毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+aHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

```  

最后，不要忘了在WeatherActicity中的showWeather方法中加入启动服务的代码，这样一旦选中城市后，更新服务会在后台运行，并每隔8小时刷新一下天气。

