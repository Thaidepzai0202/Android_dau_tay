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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jaja.ProductAdapter2;

import com.example.jaja.databinding.ListProductHorizontalFragmentBinding;
import com.example.jaja.model.Product;
import com.example.jaja.viewmodels.ListProductViewModel;

import java.util.List;

public class ListProductHorizontalFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListProductHorizontalFragmentBinding binding;
    private ListProductViewModel listProductViewModel;
    private ProductAdapter2 productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ListProductHorizontalFragmentBinding.inflate(inflater, container, false);
        Log.i("VIPVIP", "oncreateView");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("VIPVIP", "onviewcreate");


        listProductViewModel = new ViewModelProvider(this).get(ListProductViewModel.class);


        recyclerView = binding.listHorizontalProduct;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listProductViewModel.getListData().observe(getViewLifecycleOwner(),
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
//                        Log.i("VIPVIP", products.get(0).getName());
//                        productAdapter = new ProductAdapter2(requireContext(), products);
//                        recyclerView.setAdapter(productAdapter);
                        if (products != null && !products.isEmpty()) {
                            Log.i("VIPVIP", products.get(0).getName());
                            productAdapter = new ProductAdapter2(requireContext(), products);
                            recyclerView.setAdapter(productAdapter);
                        } else {
                            Log.i("VIPVIP", "Product list is empty");
                        }
                    }
                });
    }
}

