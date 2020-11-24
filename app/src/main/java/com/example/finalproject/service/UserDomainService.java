package com.example.finalproject.service;


import android.content.Context;

import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;

public class UserDomainService {

    private final UserRepository userRepository;

    public UserDomainService(Context context) {
        userRepository = new UserRepository(context);
    }

    public int register(User user) {
        return userRepository.saveAccount(user);
    }

    public boolean login(String username, String password) {
        return  userRepository.findAccount(username, password);
    }

    public boolean validateUsernameExist(String username) {
        return userRepository.findUsername(username);
    }

}
