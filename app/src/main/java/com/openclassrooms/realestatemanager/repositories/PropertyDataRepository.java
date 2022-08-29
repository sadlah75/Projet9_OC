package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDAO;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;

import java.util.List;

public class PropertyDataRepository {

    private final PropertyDAO propertyDAO;

    public PropertyDataRepository(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    // create property
    public long createProperty(Property property) {
        return this.propertyDAO.createProperty(property);
    }

    public LiveData<Property> getPropertyById(long propertyId) {
        return this.propertyDAO.getPropertyById(propertyId);
    }

    // get All properties by user
    public LiveData<List<PropertyAndAddressAndPhotos>> getAllPropertiesByUser(long userId) {
        return this.propertyDAO.getPropertiesByUser(userId);
    }

    // Update property
    public int updateProperty(Property property) {
        return this.propertyDAO.updateProperty(property);
    }
}
