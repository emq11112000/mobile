package com.example.palestinehotel;


import android.os.Handler;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;
import java.util.Timer;
import java.util.TimerTask;

public class ImagesSetting extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private final String[] urls = new String[] {
            "http://10.0.2.2:80/project2/ten.jpg",
            "http://10.0.2.2:80/project2/images/image1.jpg",
            "http://10.0.2.2:80/project2/images/image2.jpg",
            "http://10.0.2.2:80/project2/images/image3.jpg",
            "http://10.0.2.2:80/project2/images/second.jpg",
            "http://10.0.2.2:80/project2/images/third.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_setting);
        init();
    }

    private void init() {

        mPager =findViewById(R.id.pager);
        CirclePageIndicator indicator = findViewById(R.id.indicator);

        mPager.setAdapter(new SlidingImage_Adapter(ImagesSetting.this,urls));
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator "on swipe by click"
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
}