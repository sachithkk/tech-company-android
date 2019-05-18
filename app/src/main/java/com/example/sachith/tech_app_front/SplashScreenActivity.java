package com.example.sachith.tech_app_front;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen splashScreen = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(HomeCompany.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#95a5a6"))
                .withLogo(R.mipmap.logo)
                .withFooterText("TechCom 1.0");


        View splashView = splashScreen.create();
        setContentView(splashView);
    }
}
