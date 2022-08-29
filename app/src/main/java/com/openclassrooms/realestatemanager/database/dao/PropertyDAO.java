package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;

import java.util.List;

@Dao
public interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createProperty(Property property);

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    LiveData<Property> getPropertyById(long propertyId);

    @Transaction
    @Query("SELECT * FROM Property WHERE userId = :userId")
    LiveData<List<PropertyAndAddressAndPhotos>> getPropertiesByUser(long userId);

    @Query("SELECT * FROM Property WHERE userId = :userId")
    Cursor getPropertiesWithCursor(long userId);

    @Update
    int updateProperty(Property property);

}