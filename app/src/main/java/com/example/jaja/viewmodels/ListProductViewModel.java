package com.example.jaja.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jaja.api.ApiService;
import com.example.jaja.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductViewModel extends ViewModel {

    private MutableLiveData<List<Product>> listProduct;

    public LiveData<List<Product>> getListData() {
        if (listProduct == null) {
            listProduct = new MutableLiveData<>();
            loadData();

        }
        return listProduct;
    }
    public LiveData<List<Product>> getListVipData() {
        if (listProduct == null) {
            listProduct = new MutableLiveData<>();
            loadVipData();

        }
        return listProduct;
    }

    public LiveData<List<Product>> getSearchData(String searchKey){
        MutableLiveData<List<Product>> searchResults = new MutableLiveData<>();

        if(listProduct!=null && listProduct.getValue()!=null){
            List<Product> filteredList = new ArrayList<>();
            for (Product product : listProduct.getValue()){
                if(product.getName().toLowerCase().contains(searchKey.toLowerCase())){
                    filteredList.add(product);
                }
            }
            searchResults.setValue(filteredList);
        }
        return searchResults;

    }

    private void loadData() {
        ApiService.apiService.getListProduct()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            List<Product> list = response.body();
                            listProduct.setValue(list);
                        } else {
                            Log.e("LoadData", "Error response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e("LoadData", "Failed to load list product", t);
                    }
                });
    }

    private void loadVipData() {
        ApiService.apiService.getListVIPProduct()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            List<Product> list = response.body();
                            Log.i("VIPVIP",response.body().toString());
                            listProduct.setValue(list);
                        } else {
                            Log.e("LoadData", "Error response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e("LoadData", "Failed to load list product", t);
                    }
                });
    }
}
