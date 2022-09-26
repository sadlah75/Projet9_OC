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

    public List<PropertyAndAddressAndPhotos> getPropertiesByUser(long userId) {
        return this.propertyDAO.getAllPropertiesByUser(userId);
    }

    // Update property
    public int updateProperty(Property property) {
        return this.propertyDAO.updateProperty(property);
    }

    // Search Property by criteria
    public LiveData<List<PropertyAndAddressAndPhotos>> searchProperty(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                                       Integer minRoom, Integer maxRoom, long userId) {
        return this.propertyDAO.searchProperty(type, area, minSurface, maxSurface, minPrice, maxPrice,
                minRoom, maxRoom, userId);
    }
}
