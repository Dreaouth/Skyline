package com.example.liu.skyline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu.skyline.gson.Forecast;
import com.example.liu.skyline.gson.Weather;
import com.example.liu.skyline.util.HttpUtil;
import com.example.liu.skyline.util.Utility;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{
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
        now_information.setOnClickListener(this);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        qltyText=(TextView)findViewById(R.id.qlty_text);
        pm10Text=(TextView)findViewById(R.id.pm10_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        coText=(TextView)findViewById(R.id.co_text);
        so2Text=(TextView)findViewById(R.id.so2_text);
        noText=(TextView)findViewById(R.id.o3_text);
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
        airButton.setOnClickListener(this);
        comfButton.setOnClickListener(this);
        washButton.setOnClickListener(this);
        drsgButton.setOnClickListener(this);
        fluButton.setOnClickListener(this);
        sportButton.setOnClickListener(this);
        travelButton.setOnClickListener(this);
        uvButton.setOnClickListener(this);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);
        if (weatherString!=null){
            //有缓存时直接解析天气数据
            Weather weather= Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }
        else {
            //无缓存时去服务器查询天气数据
            String weatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
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
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final Weather weather=Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather!=null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }
                        else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 处理并显示Weather中的数据
     * @param weather
     */
    public void showWeatherInfo(Weather weather){
        String cityName=weather.basic.cityName;
        String UpdateTime="更新时间"+weather.basic.update.updateTime.split(" ")[1];
        String degree=weather.now.temperature+"℃";
        String weatherInfo=weather.now.more.info;
        titleCity.setText(cityName);
        updateTime.setText(UpdateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecastList){
            View view= LayoutInflater.from(this).inflate(R.layout.forcast_item,forecastLayout,false);
            TextView dateText=(TextView)view.findViewById(R.id.date_text);
            TextView infoText=(TextView)view.findViewById(R.id.info_text);
            TextView maxText=(TextView)view.findViewById(R.id.max_text);
            TextView minText=(TextView)view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            String max=forecast.temperature.max+"℃";
            String min=forecast.temperature.min+"℃";
            maxText.setText(max);
            minText.setText(min);
            forecastLayout.addView(view);
        }
        if (weather.aqi!=null){
            aqiText.setText(weather.aqi.city.aqi);
            qltyText.setText(weather.aqi.city.qlty);
            pm25Text.setText(weather.aqi.city.pm25);
            pm10Text.setText(weather.aqi.city.pm10);
            coText.setText(weather.aqi.city.co);
            so2Text.setText(weather.aqi.city.so2);
            noText.setText(weather.aqi.city.o3);
            no2Text.setText(weather.aqi.city.no2);
        }
        if (weather.suggestion!=null){
            airText.setText(weather.suggestion.air.status);
            comfText.setText(weather.suggestion.comf.status);
            washText.setText(weather.suggestion.cw.status);
            drsgText.setText(weather.suggestion.drsg.status);
            fluText.setText(weather.suggestion.flu.status);
            sportText.setText(weather.suggestion.sport.status);
            uvText.setText(weather.suggestion.uv.status);
            travelText.setText(weather.suggestion.trav.status);
        }
        weatherLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.now_information:
//                Intent now_information_intent=new Intent(this,nowActivity.class);
//        }
    }
}
