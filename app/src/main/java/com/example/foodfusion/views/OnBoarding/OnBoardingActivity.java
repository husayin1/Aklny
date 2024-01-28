package com.example.foodfusion.views.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodfusion.views.Authentication.AuthenticationActivity;
import com.example.foodfusion.R;

public class    OnBoardingActivity extends AppCompatActivity {
    private ViewPager slideViewPager;
    private LinearLayout dotIndicator;
    private ViewPagerAdapter viewPagerAdapter;
    private Button nextButton, skipButton,backButton;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initUI();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0)>0){

                    slideViewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0)<2){
                    slideViewPager.setCurrentItem(getItem(1),true);
                }else{
                    Intent intent = new Intent(OnBoardingActivity.this, AuthenticationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);

        setUptIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);


    }

    private void initUI(){

        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        skipButton = (Button) findViewById(R.id.skipButton);

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator =(LinearLayout) findViewById(R.id.dotIndicator);

    }
    public void setUptIndicator(int position){
        dots = new TextView[3];
        dotIndicator.removeAllViews();

        for( int i=0 ; i<dots.length ; i++ ){
            dots[i] = new TextView(OnBoardingActivity.this);
            dots[i].setText(Html.fromHtml("&#8226",Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.green,getApplicationContext().getTheme()));
    }


    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            setUptIndicator(position);
            if(position>0){
                backButton.setVisibility(View.VISIBLE);
            }else{
                backButton.setVisibility(View.INVISIBLE);
            }
            if (position == 2) {
                backButton.setVisibility(View.INVISIBLE);
                skipButton.setVisibility(View.INVISIBLE);
                nextButton.setText(R.string.finish);

            } else {
                nextButton.setText(R.string.next);
                backButton.setVisibility(View.VISIBLE);
                skipButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getItem(int i){
        return slideViewPager.getCurrentItem()+i;
    }

}