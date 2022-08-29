package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.content.ContentValues;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId"),
        indices =  {@Index("userId")})
public class Property implements Serializable {

    /**
     * The unique identifier of the user
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String urlPicture;
    private String type;
    private String area;
    private String description;
    private long price;
    private int surface;
    private int numberOfRoom;
    private int numberOfBathroom;
    private int numberOfBedroom;
    private boolean isAvailable;
    private Date entryDate;
    private Date soldDate;
    private List<String> poi;
    private String urlVideo;


    @Ignore
    private List<Address> address;


    @Ignore
    private List<Photo> photos;

    /**
     * The unique identifier of the address associated to the property
     */
    @ColumnInfo
    private long userId;

    public Property(String urlPicture,String type, String area,String description, long price, int surface,
                    int numberOfRoom,int numberOfBathroom, int numberOfBedroom,
                    boolean isAvailable, Date entryDate, Date soldDate, List<String> poi, String urlVideo,List<Address> address,
                    List<Photo> photos, long userId) {
        this.urlPicture = urlPicture;
        this.type = type;
        this.area = area;
        this.description = description;
        this.price = price;
        this.surface = surface;
        this.numberOfRoom = numberOfRoom;
        this.numberOfBathroom = numberOfBathroom;
        this.numberOfBedroom = numberOfBedroom;
        this.isAvailable = isAvailable;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
        this.poi = poi;
        this.urlVideo = urlVideo;
        this.address = address;
        this.photos = photos;
        this.userId = userId;
    }

    public Property() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlPicture() {return urlPicture;}

    public void setUrlPicture(String urlPicture) {this.urlPicture = urlPicture; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public int getNumberOfBathroom() {
        return numberOfBathroom;
    }

    public void setNumberOfBathroom(int numberOfBathroom) {
        this.numberOfBathroom = numberOfBathroom;
    }

    public int getNumberOfBedroom() {
        return numberOfBedroom;
    }

    public void setNumberOfBedroom(int numberOfBedroom) {
        this.numberOfBedroom = numberOfBedroom;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public List<String> getPoi() {
        return poi;
    }

    public void setPoi(List<String> poi) {
        this.poi = poi;
    }

    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }

    public String getUrlVideo() { return urlVideo; }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    // --- Utils ---
    public static Property fromContentValues(ContentValues values) {
        Property property = new Property();

        if (values.containsKey("urlPicture")) property.setUrlPicture(values.getAsString("urlPicture"));
        if (values.containsKey("type")) property.setType(values.getAsString("type"));
        if (values.containsKey("price")) property.setPrice(values.getAsLong("price"));

        return property;
    }
}
