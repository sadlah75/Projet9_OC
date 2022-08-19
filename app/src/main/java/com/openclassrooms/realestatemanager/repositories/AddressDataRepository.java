package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;
import com.openclassrooms.realestatemanager.database.dao.AddressDAO;
import com.openclassrooms.realestatemanager.model.Address;

public class AddressDataRepository {

    private final AddressDAO addressDAO;

    public AddressDataRepository(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    // Create address
    public long createAddress(Address address) {
        return this.addressDAO.createAddress(address);
    }

    // Get address
    public LiveData<Address> getAddressByProperty(long propertyId) {
        return this.addressDAO.getAddressByProperty(propertyId);
    }

    // update address
    public int updateAddress(Address address) {
        return this.addressDAO.updateAddress(address);
    }
}
