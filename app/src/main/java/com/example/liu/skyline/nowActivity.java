package com.example.liu.skyline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.liu.skyline.gson.Weather;
import com.example.liu.skyline.util.Utility;

public class nowActivity extends AppCompatActivity {
    private ImageView bingPic;
    private ScrollView weatherLayoutnow;
    private TextView nowTitle;
    private Button backToWeather;
    private TextView degree_now;
    private TextView weather_info_now;
    private TextView f1;
    private TextView hum;
    private TextView pcpn;
    private TextView pres;
    private TextView vis;
    private TextView deg;
    private TextView sc;
    private TextView spd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当SDK版本在21以上的具有全屏功能
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        setContentView(R.layout.activity_now);
        //初始化各控件
        bingPic=(ImageView)findViewById(R.id.bing_pic_img_now);
        weatherLayoutnow=(ScrollView)findViewById(R.id.weather_now_layout);
        nowTitle=(TextView)findViewById(R.id.suggest_title_text);
        backToWeather=(Button)findViewById(R.id.suggest_back_button);
        backToWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        degree_now=(TextView)findViewById(R.id.degree_text_now);
        weather_info_now=(TextView)findViewById(R.id.weather_info_text_now);
        f1=(TextView)findViewById(R.id.f1_text);
        hum=(TextView)findViewById(R.id.hum_text);
        pcpn=(TextView)findViewById(R.id.pcpn_text);
        pres=(TextView)findViewById(R.id.pres_text);
        vis=(TextView)findViewById(R.id.vis_text);
        deg=(TextView)findViewById(R.id.deg_text);
        sc=(TextView)findViewById(R.id.sc_text);
        spd=(TextView)findViewById(R.id.spd_text);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);
        String bingPicNow=prefs.getString("bing_pic",null);
        if (bingPicNow!=null){
            Glide.with(this).load(bingPicNow).into(bingPic);
        }
        else {
            Toast.makeText(this,"What the fuck???",Toast.LENGTH_SHORT).show();
        }
        if (weatherString!=null){
            //有缓存时直接解析天气数据
            Weather weather= Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }
        else {
            //无缓存时去服务器查询天气数据
            weatherLayoutnow.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"无缓存？？？",Toast.LENGTH_SHORT).show();
        }
    }
    private void showWeatherInfo(Weather weather){
        String title=weather.basic.cityName;
        nowTitle.setText(title);
        String degree=weather.now.temperature+"℃";
        String weatherInfo=weather.now.more.info;
        degree_now.setText(degree);
        weather_info_now.setText(weatherInfo);
        if (weather.now!=null){
            String f1Text=weather.now.fl+"℃";
            f1.setText(f1Text);
            String humText=weather.now.hum+"%";
            hum.setText(humText);
            String pcpnText=weather.now.pcpn;
            pcpn.setText(pcpnText);
            String presText=weather.now.pres;
            pres.setText(presText);
            String visText=weather.now.vis;
            vis.setText(visText);
            deg.setText(weather.now.wind.dir);
            String scString=weather.now.wind.sc+"级";
            sc.setText(scString);
            String speed=weather.now.wind.sqd+"km/h";
            spd.setText(speed);
        }

        weatherLayoutnow.setVisibility(View.VISIBLE);
    }
}
