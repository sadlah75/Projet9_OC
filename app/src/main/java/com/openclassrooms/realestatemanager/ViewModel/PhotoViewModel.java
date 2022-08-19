package com.openclassrooms.realestatemanager.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PhotoViewModel extends ViewModel {

    private final PropertyDataRepository propertyDataSource;
    private final PhotoDataRepository photoDataSource;
    private final Executor executor;

    private LiveData<Property> currentProperty;


    public PhotoViewModel(PropertyDataRepository propertyDataSource,
                          PhotoDataRepository photoDataSource, Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.photoDataSource = photoDataSource;
        this.executor = executor;
    }

    public void init(long propertyId) {
        if(this.currentProperty != null) {
            return;
        }
        currentProperty = this.propertyDataSource.getPropertyById(propertyId);
    }

    // For property
    public LiveData<Property> getProperty() {
        return this.currentProperty;
    }

    // For photo
    public void createPhoto(Photo photo) {
        executor.execute(() -> photoDataSource.createPhoto(photo));
    }

    public LiveData<List<Photo>> getAllPhotosByProperty(long propertyId) {
        return this.photoDataSource.getAllPhotosByProperty(propertyId);
    }
}
