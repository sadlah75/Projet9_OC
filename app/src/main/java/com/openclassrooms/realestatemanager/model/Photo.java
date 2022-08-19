package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "id",
        childColumns = "propertyId"),
        indices =  {@Index("propertyId")})

public class Photo {

    /**
     * The unique identifier of the user
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * The name of the photo
     */
    private String title;

    /**
     * The path of the photo
     */
    private String url;

    /**
     * The unique identifier of the property associated to the photo
     */
    @ColumnInfo
    private long propertyId;

    @Ignore
    public Photo() {}

    @Ignore
    public Photo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Photo(String title, String url, long propertyId) {
        this.title = title;
        this.url = url;
        this.propertyId = propertyId;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    // --- Utils ---
    public static Photo fromContentValues(ContentValues values) {
        Photo photo = new Photo();

        if (values.containsKey("propertyId")) photo.setPropertyId(values.getAsLong("propertyId"));
        if(values.containsKey("title")) photo.setTitle(values.getAsString("title"));
        if (values.containsKey("url")) photo.setUrl(values.getAsString("url"));

        return photo;
    }
}
