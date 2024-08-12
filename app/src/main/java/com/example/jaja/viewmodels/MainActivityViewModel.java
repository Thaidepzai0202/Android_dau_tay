package com.example.jaja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.jaja.model.Product;
import com.example.jaja.repositories.ListMockRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private ListMockRepository listMockRepository;
    private final MutableLiveData<Boolean> isStartToSortMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> priceToSortMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<List<Product>> resultOfSortingListMediatorLiveData = new MediatorLiveData<>();

    public MainActivityViewModel(@NonNull Application application, ListMockRepository listMockRepository) {
        super(application);
        this.listMockRepository = listMockRepository;

        LiveData<List<Product>> getListToMake = listMockRepository.getListProduct();

        resultOfSortingListMediatorLiveData.addSource(getListToMake, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                combine(products, isStartToSortMutableLiveData.getValue(), priceToSortMutableLiveData.getValue());
            }
        });

        resultOfSortingListMediatorLiveData.addSource(isStartToSortMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isStartToSort) {
                combine(getListToMake.getValue(), isStartToSort, priceToSortMutableLiveData.getValue());
            }
        });

        resultOfSortingListMediatorLiveData.addSource(priceToSortMutableLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer priceToSort) {
                combine(getListToMake.getValue(), isStartToSortMutableLiveData.getValue(), priceToSort);
            }
        });

    }

    public void setIsStartToSortMutableLiveData(boolean isStartToSort) {
        isStartToSortMutableLiveData.setValue(isStartToSort);
    }

    public void setPriceToSortMutableLiveData(Integer priceToSort) {
        priceToSortMutableLiveData.setValue(priceToSort);
    }

    public LiveData<List<Product>> getListSortOfProduct() {
        return resultOfSortingListMediatorLiveData;
    }

    private void combine(List<Product> products, Boolean isStartSort, Integer priceToSort) {
        if (products == null || isStartSort == null || priceToSort == null) {
            return;
        }

        if(isStartSort){
            List<Product> productListSort = new ArrayList<>();
            for(Product product : products){
                if(product.getPrice() >= priceToSort) {
                    productListSort.add(product);
                }
            }

            resultOfSortingListMediatorLiveData.setValue(productListSort);
        }
    }
}
