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

    @Query("SELECT * FROM Property WHERE userId = :userId AND type LIKE :type AND area LIKE :area AND surface BETWEEN :minSurface AND :maxSurface" +
            " AND price BETWEEN :minPrice AND :maxPrice AND numberOfRoom BETWEEN :minRoom AND :maxRoom ")
    LiveData<List<PropertyAndAddressAndPhotos>> searchProperty(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                                Integer minRoom, Integer maxRoom, long userId);

    @Transaction
    @Query("SELECT * FROM Property WHERE userId = :userId")
    LiveData<List<PropertyAndAddressAndPhotos>> getPropertiesByUser(long userId);

    @Transaction
    @Query("SELECT * FROM Property WHERE userId = :userId")
    List<PropertyAndAddressAndPhotos> getAllPropertiesByUser(long userId);



    @Query("SELECT * FROM Property WHERE userId = :userId")
    Cursor getPropertiesWithCursor(long userId);

    @Update
    int updateProperty(Property property);
}