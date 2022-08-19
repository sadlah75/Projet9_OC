package com.openclassrooms.realestatemanager.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.concurrent.Executor;

public class AddressViewModel extends ViewModel {

    private final PropertyDataRepository propertyDataSource;
    private final AddressDataRepository addressDataSource;
    private final Executor executor;

    private LiveData<Property> currentProperty;

    public AddressViewModel(PropertyDataRepository propertyDataSource,
                            AddressDataRepository addressDataSource, Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.addressDataSource = addressDataSource;
        this.executor = executor;
    }

    public void init(long propertyId) {
        if(this.currentProperty != null) {
            return;
        }
        currentProperty = this.propertyDataSource.getPropertyById(propertyId);
    }

    // For Property
    public LiveData<Property> getProperty() {
        return this.currentProperty;
    }

    // For Address
    public void createAddress(Address address) {
        executor.execute(() -> addressDataSource.createAddress(address));
    }

    public LiveData<Address> getAddressByProperty(long propertyId) {
        return this.addressDataSource.getAddressByProperty(propertyId);
    }
    public void updateAddress(Address address) {
        executor.execute(() -> addressDataSource.updateAddress(address));
    }
}
