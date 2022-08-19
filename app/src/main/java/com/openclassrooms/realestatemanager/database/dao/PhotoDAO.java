package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

@Dao
public interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createPhoto(Photo photo);

    @Query("SELECT * FROM Photo WHERE propertyId = :propertyId")
    LiveData<List<Photo>> getPhotosByProperty(long propertyId);

    @Query("SELECT * FROM Photo WHERE propertyId = :propertyId")
    Cursor getPhotosWithCursor(long propertyId);

    @Update
    int updatePhoto(Photo photo);

    @Query("DELETE FROM Photo WHERE id = :photoId")
    int deletePhoto(long photoId);
}
