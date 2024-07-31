package com.example.jaja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jaja.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE = 1234; // Mã yêu cầu có thể tùy chỉnh
    private static final String TAG = "Test life circle";
    private int numSelect = 1;

    //viewpage2
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;

    //button
    GradientTextView discripionButton;
    GradientTextView specificationButton;

    List<SliderItem> sliderItems = new ArrayList<>();
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @SuppressLint("SuspiciousIndentation")
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == sliderItems.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());


        viewPager2 = findViewById(R.id.view_bike_page_2);
        circleIndicator3 = findViewById(R.id.circle_indicator_3);


        //image page
        sliderItems.add(new SliderItem(R.drawable.bike1));
        sliderItems.add(new SliderItem(R.drawable.bike2));
        sliderItems.add(new SliderItem(R.drawable.bike3));
        sliderItems.add(new SliderItem(R.drawable.bike4));
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        circleIndicator3.setViewPager(viewPager2);

        //auto run image
        handler.postDelayed(runnable, 5000);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });

        //next screen
        FrameLayout exampleButton = binding.includeLayout.backButton;
        exampleButton.setBackground(getDrawable(R.drawable.gradient_background));
        exampleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivities(new Intent[]{intent});
                    }
                }
        );

        //set click
        discripionButton = binding.titleContent.buttonDescription;
        specificationButton = binding.titleContent.buttonSpecification;

        discripionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        updateTitleSelect(1);
                    }
                }
        );
        specificationButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        updateTitleSelect(2);
                    }
                }
        );
        updateTitleSelect(1);

    }

    private void updateTitleSelect(int value) {
        numSelect = value;
        discripionButton.setHasSelect(numSelect == 1);
        specificationButton.setHasSelect(numSelect == 2);
        if (numSelect == 1) {
            binding.titleContent.contentScrollView.setVisibility(View.VISIBLE);
            binding.titleContent.specificationScroll.setVisibility(View.GONE);
        } else {
            binding.titleContent.contentScrollView.setVisibility(View.GONE);
            binding.titleContent.specificationScroll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }
}
