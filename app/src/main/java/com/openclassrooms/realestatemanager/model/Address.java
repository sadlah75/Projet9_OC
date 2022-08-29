package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "id",
        childColumns = "propertyId"),
        indices =  {@Index("propertyId")})
public class Address implements Serializable {
    /**
     * The unique identifier of the user
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * The unique identifier of the property associated to the address
     */
    @ColumnInfo()
    private long propertyId;

    /**
     * The first address of the address
     */
    private String address1;

    /**
     * The second address of the address
     */
    private String address2;

    /**
     * The city's name of the address
     */
    private String city;

    /**
     * The zip's code of the address
     */
    private String zip;

    /**
     * The state's name of the address
     */
    private String state;

    @Ignore
    public Address() {}

    public Address(long propertyId, String address1, String address2, String city, String zip, String state) {
        this.propertyId = propertyId;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // --- Utils ---
    @NonNull
    public static Address fromContentValues(ContentValues values) {
        final Address address = new Address();
        if(values.containsKey("properId")) address.setPropertyId(values.getAsLong("propertyId"));
        if (values.containsKey("address1")) address.setAddress1(values.getAsString("address1"));
        if (values.containsKey("address2")) address.setAddress2(values.getAsString("address2"));
        if (values.containsKey("city")) address.setCity(values.getAsString("city"));
        if (values.containsKey("zip")) address.setZip(values.getAsString("zip"));
        if (values.containsKey("state")) address.setState(values.getAsString("state"));
        return address;
    }
}
