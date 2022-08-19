package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.UserDAO;
import com.openclassrooms.realestatemanager.model.User;

public class UserDataRepository {

    private final UserDAO userDAO;

    public UserDataRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public long createUser(User user) {
        return this.userDAO.createUser(user);
    }

    // get user
    public LiveData<User> getUser(long userId) {
        return this.userDAO.getUser(userId);
    }
}
