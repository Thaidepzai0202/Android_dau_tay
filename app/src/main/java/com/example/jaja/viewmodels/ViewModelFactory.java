package com.example.jaja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.jaja.MainActivity;
import com.example.jaja.repositories.ListMockRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory instance;
    private final Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    public static ViewModelFactory getInstance(Application application){
        if (instance ==null){
            synchronized (ViewModelFactory.class){
                if(instance==null){
                    instance = new ViewModelFactory(application);
                }
            }
        }
        return instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)){
            return (T) new MainActivityViewModel(
                    application,
                    ListMockRepository.getInstance()
            );
        }
        throw new IllegalArgumentException("You forgot to place the new ViewModel Class insiade ViewModelFactory class");
    }
}
