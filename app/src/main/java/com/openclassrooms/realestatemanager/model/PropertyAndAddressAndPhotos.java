package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.io.Serializable;
import java.util.List;

public class PropertyAndAddressAndPhotos implements Serializable {

    @Embedded
    public Property property;

    @Relation(
            parentColumn = "id",
            entityColumn = "propertyId"
    )
    public List<Address> address;

    @Relation(
            parentColumn = "id",
            entityColumn = "propertyId"
    )
    public List<Photo> photos;
}