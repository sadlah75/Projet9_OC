package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
public class User {
    /**
     * The unique identifier of the user
     */
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

    /**
     * The username of the user
     */
    @NonNull
    private String username;

    /**
     * the urlPhoto of the user
     */
    @Nullable
    private String urlPhoto;

    /**
     * Instantiates a new user
     * @param username  the username of the user to set
     * @param urlPhoto  the path of the picture associated to the user to set
     */
    /*
    public User(@NonNull String username, @NonNull String urlPhoto) {
        this.username = username;
        this.urlPhoto = urlPhoto;
    }*/

    public User(long id, @NonNull String username, @Nullable String urlPhoto) {
        this.id = id;
        this.username = username;
        this.urlPhoto = urlPhoto;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(@NonNull String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
