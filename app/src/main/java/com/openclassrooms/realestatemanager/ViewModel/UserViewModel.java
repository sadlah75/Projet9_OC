package com.openclassrooms.realestatemanager.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;

public class UserViewModel extends ViewModel {
    private final UserDataRepository userDataSource;
    private final Executor executor;

    public UserViewModel(UserDataRepository userDataSource,Executor executor) {
        this.userDataSource = userDataSource;
        this.executor = executor;
    }

    public LiveData<User> getUserById(long userId) {
        return this.userDataSource.getUser(userId);
    }

    public void createUser(User user) {
        executor.execute(() -> this.userDataSource.createUser(user));
    }

}
