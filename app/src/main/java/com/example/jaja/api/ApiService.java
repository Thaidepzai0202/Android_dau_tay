package com.example.jaja.api;

import com.example.jaja.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    // link : https://6a97-222-252-12-118.ngrok-free.app/api/products
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://8448-222-252-12-118.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/products/{productId}")
    Call<Product> getProduct(@Path("productId") String productId);
    @GET("api/products")
    Call<List<Product>> getListProduct();
    @GET("api/products/vip")
    Call<List<Product>> getListVIPProduct();
}
