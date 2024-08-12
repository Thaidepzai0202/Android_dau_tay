package com.example.jaja.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jaja.api.ApiService;
import com.example.jaja.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallAPIViewModel extends ViewModel {
    private MutableLiveData<Product> product;

    public LiveData<Product> getData() {
        if (product == null) {
            product = new MutableLiveData<>();
            loadData("product0000001");
        }
        return product;
    }

    public void loadData(String productID) {
        ApiService.apiService.getProduct(productID)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            Product pro = response.body();
                            product.setValue(pro);
                        } else {
                            // Handle the error response
                            Log.e("LoadData", "Error response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        // Handle the failure
                        Log.e("LoadData", "Failed to load product", t);
                    }
                });
    }

}
