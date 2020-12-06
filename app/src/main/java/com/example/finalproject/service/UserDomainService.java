package com.example.finalproject.service;


import android.content.Context;

import com.example.finalproject.model.Cart;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.utils.TextUtil;

import java.util.List;

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

    public void updateUserRegisteredCourse(Cart cart) {
        userRepository.updateUserRegisteredCourse(cart.getUsername(), TextUtil.listToString(cart.getCourseInCart()));
    }

    public List<String> getCourseListByUsername(String username) {
        return userRepository.getRegisteredCourseByUsername(username);
    }
}
