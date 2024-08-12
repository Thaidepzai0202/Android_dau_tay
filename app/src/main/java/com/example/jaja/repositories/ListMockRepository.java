package com.example.jaja.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jaja.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListMockRepository {

    private static ListMockRepository instance;

    public static ListMockRepository getInstance() {
        if (instance == null) {
            instance = new ListMockRepository();
        }
        return instance;
    }

    public LiveData<List<Product>> getListProduct() {
        MutableLiveData<List<Product>> result = new MutableLiveData<>();
        List<Product> products = new ArrayList<>();


        products.add(new Product("PEUGEOT - LR01", 1, 351, "https://motofomo.com/wp-content/uploads/2021/12/2007-EICMA-Most-Beautiful-Bike-winner-Ducati-Monster-696-red-1536x1152.jpg", "The LR01 uses the same design as the most iconic bikes from PEUGEOT Cycles' 130-year history and combines it with agile, dynamic performance that's perfectly suited to navigating today's cities.", "today", "product0000001"));
        products.add(new Product("PEUGEOT - LR02", 1, 123456, "https://motofomo.com/wp-content/uploads/2021/12/2007-EICMA-Most-Beautiful-Bike-winner-Ducati-Monster-696-red-1536x1152.jpg", "The LR01 uses the same design as the most iconic bikes from PEUGEOT Cycles' 130-year history and combines it with agile, dynamic performance that's perfectly suited to navigating today's cities.", "today", "product0000001"));
        products.add(new Product("PEUGEOT - LR03", 1, 35135, "https://motofomo.com/wp-content/uploads/2021/12/2007-EICMA-Most-Beautiful-Bike-winner-Ducati-Monster-696-red-1536x1152.jpg", "The LR01 uses the same design as the most iconic bikes from PEUGEOT Cycles' 130-year history and combines it with agile, dynamic performance that's perfectly suited to navigating today's cities.", "today", "product0000001"));

        result.postValue(products);

        return result;
    }
}
