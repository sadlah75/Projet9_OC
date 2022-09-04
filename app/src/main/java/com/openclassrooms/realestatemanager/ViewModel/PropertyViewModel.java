package com.openclassrooms.realestatemanager.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    private final PropertyDataRepository propertyDataSource;
    private final UserDataRepository userDataSource;
    private final AddressDataRepository addressDataSource;
    private final PhotoDataRepository photoDataSource;
    private final Executor executor;

    private LiveData<User> currentUser;


    public PropertyViewModel(PropertyDataRepository propertyDataSource,
                             UserDataRepository userDataSource, AddressDataRepository addressDataSource,
                             PhotoDataRepository photoDataSource, Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
        this.photoDataSource = photoDataSource;
        this.executor = executor;
    }

    public void init(long userId) {
        if(this.currentUser == null) {
            currentUser = userDataSource.getUser(userId);
        }
    }

    // For user
    public LiveData<User> getUser(long userId) {
        return this.currentUser;
    }

    // For property
    public void createProperty(Property property) {
        executor.execute(() -> {
            long propertyId = propertyDataSource.createProperty(property);
            // --- Insertion address ---
            List<Address> address = property.getAddress();
            address.get(0).setPropertyId(propertyId);
            addressDataSource.createAddress(address.get(0));

            // --- Insertion List of photos ---
            List<Photo> photos = property.getPhotos();
            for (Photo photo : photos) {
                photo.setPropertyId(propertyId);
                photoDataSource.createPhoto(photo);
            }
        });
    }

    public LiveData<List<PropertyAndAddressAndPhotos>> getPropertiesByUser(long userId) {
        LiveData<List<PropertyAndAddressAndPhotos>> properties = propertyDataSource.getAllPropertiesByUser(userId);
        return properties;
    }

    public void updateProperty(Property property) {
        executor.execute(() -> {
            // Update property
            propertyDataSource.updateProperty(property);

            // Update photos
            //photoDataSource.updatePhotos(property.getPhotos());

            // Update address
           // addressDataSource.updateAddress(property.getAddress().get(0));
        });
    }
}
