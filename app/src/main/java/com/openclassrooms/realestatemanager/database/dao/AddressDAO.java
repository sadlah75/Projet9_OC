package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.Address;

import java.util.List;

@Dao
public interface AddressDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createAddress(Address address);

    @Query("SELECT * FROM Address WHERE propertyId = :propertyId")
    LiveData<Address> getAddressByProperty(long propertyId);

    @Query("SELECT * FROM Address WHERE propertyId = :propertyId")
    Cursor getAddressWithCursor(long propertyId);

    @Update
    int updateAddress(Address address);

    @Query("DELETE FROM Address WHERE id = :addressId")
    int deleteAddress(long addressId);

}
