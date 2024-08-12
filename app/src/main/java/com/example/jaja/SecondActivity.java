package com.example.jaja;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jaja.api.ApiService;
import com.example.jaja.databinding.ActivityMainBinding;
import com.example.jaja.databinding.ActivitySecondBinding;
import com.example.jaja.model.Product;
import com.example.jaja.viewmodels.ListProductViewModel;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private ActivitySecondBinding binding;
    private ListProductViewModel listProductViewModel;
    private ProductAdapter productAdapter;
    private List<Product> listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listProductViewModel = new ViewModelProvider(this).get(ListProductViewModel.class);

        //next screen
        FrameLayout exampleButton = findViewById(R.id.back_button);
        exampleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        //rename

        binding.titleList.nameProduct.setText("List Product");
        listView = binding.listProduct;


        //call API
        listProductViewModel.getListData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                binding.loading.setVisibility(View.GONE);

                productAdapter = new ProductAdapter(SecondActivity.this, products);
                listView.setAdapter(productAdapter);
            }
        });

        //search
        binding.searchEditText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        listProductViewModel.getSearchData(s.toString()).observe(
                                SecondActivity.this,
                                new Observer<List<Product>>() {

                                    @Override
                                    public void onChanged(List<Product> products) {
                                        productAdapter = new ProductAdapter(SecondActivity.this, products);
                                        listView.setAdapter(productAdapter);
                                    }
                                }
                        );
                    }
                }
        );

        //hide keyboard
        binding.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

    }


}