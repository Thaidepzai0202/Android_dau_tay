package com.example.jaja.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jaja.ImageSliderAdapter;
import com.example.jaja.databinding.VipProductListFragmentBinding;
import com.example.jaja.model.Product;
import com.example.jaja.viewmodels.ListProductViewModel;

import java.util.List;

public class VipProductListFragment extends Fragment {
    private VipProductListFragmentBinding binding;
    private ListProductViewModel listProductViewModel;
    private ViewPager2 viewPager2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VipProductListFragmentBinding.inflate(inflater, container, false);
        Log.i("FragmentVip", "oncreateView");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listProductViewModel = new ViewModelProvider(this).get(ListProductViewModel.class);
        viewPager2 = binding.productVipPageView2;
        listProductViewModel.getListVipData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                ImageSliderAdapter adapter = new ImageSliderAdapter(products,viewPager2);
                viewPager2.setAdapter(adapter);
                setupNameProduct(products);
            }
        });
    }

    private void setupNameProduct(List<Product> products){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.productNameVip.setText(products.get(position).getName());
            }
        });
    }
}
