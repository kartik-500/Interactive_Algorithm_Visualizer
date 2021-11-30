package com.kd.myapplication.Activities.Starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kd.myapplication.R;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(
                () -> {
                    startActivity(new Intent(SplashScreenActivity.this, OnBoardingScreenActivity.class));
                    finish();
                },6000 );

    }
}