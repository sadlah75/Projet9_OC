package com.openclassrooms.realestatemanager.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.ViewModel.AddressViewModel;
import com.openclassrooms.realestatemanager.ViewModel.PhotoViewModel;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.ViewModel.UserViewModel;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PhotoDataRepository photoDataSource;
    private final AddressDataRepository addressDataSource;
    private final PropertyDataRepository propertyDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context) {
        if(factory == null) {
            synchronized (ViewModelFactory.class) {
                if(factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    private ViewModelFactory(Context context) {
        RealEstateManagerDatabase db = RealEstateManagerDatabase.getInstance(context);
        this.photoDataSource = new PhotoDataRepository(db.photoDAO());
        this.addressDataSource = new AddressDataRepository(db.addressDAO());
        this.propertyDataSource = new PropertyDataRepository(db.propertyDAO());
        this.userDataSource = new UserDataRepository(db.userDAO());
        this.executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddressViewModel.class)) {
            return (T) new AddressViewModel(propertyDataSource,addressDataSource,executor);
        }
        if (modelClass.isAssignableFrom(PhotoViewModel.class)) {
            return (T) new PhotoViewModel(propertyDataSource,photoDataSource,executor);
        }
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(propertyDataSource,userDataSource, addressDataSource, photoDataSource, executor);
        }
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}