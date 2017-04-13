package com.example.liu.skyline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<MyCity> myCities= DataSupport.findAll(MyCity.class);
        MyCityFragment.cityList.addAll(myCities);
        if (myCities.size()>0){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }
//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
//        if (prefs.getString("weather",null)!=null){
//            Intent intent=new Intent(this,WeatherActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
}
