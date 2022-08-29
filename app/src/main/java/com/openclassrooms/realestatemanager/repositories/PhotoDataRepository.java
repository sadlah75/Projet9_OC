package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PhotoDAO;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class PhotoDataRepository {

    private final PhotoDAO photoDAO;

    public PhotoDataRepository(PhotoDAO photoDAO) {
        this.photoDAO = photoDAO;
    }

    // Create photo
    public long createPhoto(Photo photo) {
        return this.photoDAO.createPhoto(photo);
    }

    // get all photos by property
    public LiveData<List<Photo>> getAllPhotosByProperty(long propertyId) {
        return this.photoDAO.getPhotosByProperty(propertyId);
    }

    // Update photo
    public void updatePhotos(List<Photo> photos) {
        for (Photo p : photos) {
            photoDAO.updatePhoto(p);
        }
    }

}
