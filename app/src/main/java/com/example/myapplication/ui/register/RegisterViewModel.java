package com.example.myapplication.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.db.user.User;
import com.example.myapplication.db.user.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository repository;
    private User user;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
//        repository = new UserRepository(application);
    }

    public void insert(User user) {
        repository.insert(user);
    }
}