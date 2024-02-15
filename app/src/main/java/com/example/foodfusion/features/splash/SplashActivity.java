package com.example.foodfusion.features.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.foodfusion.MainActivity;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.features.Authentication.AuthenticationActivity;
import com.example.foodfusion.features.OnBoarding.OnBoardingActivity;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 5000;
    private static final String TAG = "SplashActivity";
    private static final String firstTimeOnBoarding = "firstTime";
    private static final String SharePrefName = "mySharedPref";
    SharedPreferences preferences;
    AuthenticationFireBaseRepo authenticationFireBaseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        authenticationFireBaseRepo = AuthenticationFireBaseRepo.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences(SharePrefName, MODE_PRIVATE);
                boolean isOnBoarding = preferences.getBoolean(firstTimeOnBoarding, true);
                if (isOnBoarding) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(firstTimeOnBoarding, false);
                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (authenticationFireBaseRepo.isAuthenticated()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Log.i(TAG, "run: This user is logged in ");
                } else {
                    Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}