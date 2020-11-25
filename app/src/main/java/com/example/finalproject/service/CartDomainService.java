package com.example.finalproject.service;

import android.content.Context;

import com.example.finalproject.model.Cart;
import com.example.finalproject.repository.CartRepository;
import com.example.finalproject.utils.TextUtil;

public class CartDomainService {

    private final CartRepository cartRepository;

    public CartDomainService(Context context) {
        cartRepository = new CartRepository(context);
    }

    public void addCourseInCart(String username, String course) {
        if (cartRepository.checkUserHasCartRecord(username)) {
            Cart cart = cartRepository.getCourseInCart(username);
            cart.getCourseInCart().add(course);
            cartRepository.updateCourseInCart(cart.getUsername(), TextUtil.listToString(cart.getCourseInCart()));
        } else {
            cartRepository.addCourseToCartFistTime(username, course);
        }
    }

    public void clearCart(String username) {
        cartRepository.deleteAllCourseInCart(username);
    }

}