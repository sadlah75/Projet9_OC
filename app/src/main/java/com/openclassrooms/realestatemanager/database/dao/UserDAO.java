package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.model.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createUser(User user);

    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> getUser(long userId);
}