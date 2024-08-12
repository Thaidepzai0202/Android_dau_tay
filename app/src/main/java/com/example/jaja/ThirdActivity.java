package com.example.jaja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.example.jaja.databinding.ActivityMainBinding;
import com.example.jaja.databinding.ThirdActivityBinding;
import com.example.jaja.model.Product;

public class ThirdActivity extends AppCompatActivity {

    private ThirdActivityBinding binding;
    Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ThirdActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            product = bundle.getParcelable("product");
            if (product != null) {
                binding.titleNameProduct.nameProduct.setText(product.getName());
            }
        }

        Glide.with(this)
                .load(product.getImagePath())
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new CenterInside()
                        )
                )).into(binding.imageProduct);

        binding.titleNameProduct.backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );


    }
}
