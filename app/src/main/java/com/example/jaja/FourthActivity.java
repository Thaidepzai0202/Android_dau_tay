package com.example.jaja;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.jaja.databinding.FourthActivityBinding;
import com.example.jaja.fragment.ListProductHorizontalFragment;
import com.example.jaja.fragment.VipProductListFragment;
import com.example.jaja.viewmodels.ListProductViewModel;


public class FourthActivity extends AppCompatActivity {


    private FourthActivityBinding binding;
    private ListProductViewModel listProductViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FourthActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listProductViewModel = new ViewModelProvider(this).get(ListProductViewModel.class);

        //back screen
        FrameLayout backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        //        rename
        binding.titleList.nameProduct.setText("List VIP Product");




        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_list_product_horizontal_fragment, new ListProductHorizontalFragment());
//        fragmentTransaction.commit();


        fragmentTransaction.add(R.id.fragment_list_vip_product, new VipProductListFragment());
        fragmentTransaction.commit();







    }
}
