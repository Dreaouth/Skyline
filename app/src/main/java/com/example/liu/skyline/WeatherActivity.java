package com.example.liu.skyline;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu.skyline.gson.Weather;
import com.example.liu.skyline.util.HttpUtil;
import com.example.liu.skyline.util.Utility;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;

    private TextView titleCity;
    private TextView updateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private Button now_information;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView qltyText;
    private TextView pm10Text;
    private TextView pm25Text;
    private TextView coText;
    private TextView so2Text;
    private TextView noText;
    private TextView no2Text;
    private LinearLayout airButton;
    private TextView airText;
    private LinearLayout comfButton;
    private TextView comfText;
    private LinearLayout washButton;
    private TextView washText;
    private LinearLayout drsgButton;
    private TextView drsgText;
    private LinearLayout fluButton;
    private TextView fluText;
    private LinearLayout sportButton;
    private TextView sportText;
    private LinearLayout travelButton;
    private TextView travelText;
    private LinearLayout uvButton;
    private TextView uvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //初始化各控件
        weatherLayout=(ScrollView)findViewById(R.id.weather_layout);
        titleCity=(TextView)findViewById(R.id.title_city);
        updateTime=(TextView)findViewById(R.id.update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        now_information=(Button)findViewById(R.id.now_information);
        forecastLayout=(LinearLayout)findViewById(R.id.forcast_layout);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        qltyText=(TextView)findViewById(R.id.qlty_text);
        pm10Text=(TextView)findViewById(R.id.pm10_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        coText=(TextView)findViewById(R.id.co_text);
        so2Text=(TextView)findViewById(R.id.so2_text);
        noText=(TextView)findViewById(R.id.no_text);
        no2Text=(TextView)findViewById(R.id.no2_text);
        airButton=(LinearLayout)findViewById(R.id.air_button);
        airText=(TextView)findViewById(R.id.air_text);
        comfButton=(LinearLayout)findViewById(R.id.comf_button);
        comfText=(TextView)findViewById(R.id.comf_text);
        washButton=(LinearLayout)findViewById(R.id.wash_button);
        washText=(TextView)findViewById(R.id.wash_text);
        drsgButton=(LinearLayout)findViewById(R.id.drsg_button);
        drsgText=(TextView)findViewById(R.id.drsg_text);
        fluButton=(LinearLayout)findViewById(R.id.flu_button);
        fluText=(TextView)findViewById(R.id.flu_text);
        sportButton=(LinearLayout)findViewById(R.id.sport_button);
        sportText=(TextView)findViewById(R.id.sport_text);
        travelButton=(LinearLayout)findViewById(R.id.travel_button);
        travelText=(TextView)findViewById(R.id.travel_text);
        uvButton=(LinearLayout)findViewById(R.id.uv_button);
        uvText=(TextView)findViewById(R.id.uv_text);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);
        if (weatherString!=null){
            //有缓存时直接解析天气数据
            Weather weather= Utility.handleWeatherResponse(weatherString);

        }

    }
    /**
     * 根据天气1d请求城市天气信息
     */
    public void requestWeather(final String weatherId){
        String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherId+"&key=cfe268e31b524d0bbe763e88295d38da";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

            }
        });
    }

}
