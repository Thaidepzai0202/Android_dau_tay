package com.example.jaja;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jaja.api.ApiService;
import com.example.jaja.databinding.ActivityMainBinding;
import com.example.jaja.model.Product;
import com.example.jaja.viewmodels.CallAPIViewModel;
import com.example.jaja.viewmodels.MainActivityViewModel;
import com.example.jaja.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE = 1234; // Mã yêu cầu có thể tùy chỉnh
    private static final String TAG = "Test life circle";
    private int numSelect = 1;
    private MainActivityViewModel mainActivityViewModel;
    private CallAPIViewModel callAPIViewModel;

    //viewpage2
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;

    //button
    private GradientTextView discripionButton;
    private GradientTextView specificationButton;
    private TextView addToCartButton;

    //value content
    private TextView productContent;
    private TextView nameProductContent;
    private TextView nameProduct;
    private TextView priceProduct;


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

        // Mở rộng vào vùng cutout nếu mức API là 27 hoặc cao hơn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        //init viewmodel
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        mainActivityViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        callAPIViewModel = new ViewModelProvider(this).get(CallAPIViewModel.class);


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

        //set value content
        nameProduct = binding.includeLayout.nameProduct;
        nameProductContent = binding.titleContent.nameProductContent;
        productContent = binding.titleContent.productContent;
        priceProduct = binding.titleContent.priceProduct;

        //set click
        discripionButton = binding.titleContent.buttonDescription;
        specificationButton = binding.titleContent.buttonSpecification;
        addToCartButton = binding.titleContent.addToCartButton;


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
                        mainActivityViewModel.setIsStartToSortMutableLiveData(true);
                        mainActivityViewModel.setPriceToSortMutableLiveData(1000);
                        updateTitleSelect(2);
                    }
                }
        );
        updateTitleSelect(1);

        //callAPI obsever
        callAPIViewModel.getData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                nameProduct.setText(product.getName());
                nameProductContent.setText(product.getName());
                productContent.setText(product.getDescription());
                priceProduct.setText("$ " + product.getPrice());
            }
        });

        addToCartButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,FourthActivity.class);
                        startActivity(intent);
//                        clicktocallAPI();
                    }
                }
        );

        mainActivityViewModel.getListSortOfProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.i("MULTABLE", "onchange short list : " + products.toString());

                if (products.isEmpty()) {
                    binding.titleContent.frame.setText("Nothing your eyes");
                } else {
                    binding.titleContent.frame.setText(products.stream().map(Product::getName).collect(Collectors.toList()).toString());
                }
            }
        });

    }

    private void clicktocallAPI() {
        callAPIViewModel.loadData("product0000002");
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
