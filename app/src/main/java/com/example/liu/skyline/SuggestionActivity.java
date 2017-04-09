package com.example.liu.skyline;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SuggestionActivity extends AppCompatActivity {
    private TextView suggestTitle;
    private Button suggestBackButton;
    private TextView statusText;
    private TextView informationText;
    private ImageView bingPic;


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
        setContentView(R.layout.activity_suggestion);
        //初始化各控件
        suggestTitle=(TextView)findViewById(R.id.suggest_title_text);
        suggestBackButton=(Button)findViewById(R.id.suggest_back_button);
        statusText=(TextView)findViewById(R.id.status_text);
        informationText=(TextView)findViewById(R.id.information_text);
        bingPic=(ImageView)findViewById(R.id.bing_pic_img_suggest);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String bingPicNow=prefs.getString("bing_pic",null);
        if (bingPicNow!=null){
            Glide.with(this).load(bingPicNow).into(bingPic);
        }
        else {
            Toast.makeText(this,"What the fuck???",Toast.LENGTH_SHORT).show();
        }
        String title=getIntent().getStringExtra("title");
        String status=getIntent().getStringExtra("status");
        String information="生活建议："+getIntent().getStringExtra("information");
        suggestTitle.setText(title);
        statusText.setText(status);
        informationText.setText(information);
        suggestBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
