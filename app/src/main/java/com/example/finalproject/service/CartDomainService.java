package com.example.finalproject.service;

import android.content.Context;
import android.util.Log;


import com.example.finalproject.model.Cart;
import com.example.finalproject.repository.CartRepository;
import com.example.finalproject.utils.TextUtil;
import java.util.List;

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

    public void dropCourse(String username, String course) {
        Cart cart = cartRepository.getCourseInCart(username);
        cart.getCourseInCart().remove(course);
        Log.v("Course List", cart.getCourseInCart().toString());
        if (cart.getCourseInCart().size() == 0) {
            cartRepository.clearCart(username);
        } else {
            cartRepository.updateCourseInCart(cart.getUsername(), TextUtil.listToString(cart.getCourseInCart()));
        }

    }

    public List<String> getCouseList(String username) {
        Cart cart = getCart(username);
        if (cart != null) {
            return cartRepository.getCourseInCart(username).getCourseInCart();
        } else {
            return null;
        }

    }

    public Cart getCart(String username) {
        return cartRepository.getCourseInCart(username);
    }

    public boolean validateUserExistInCart(String username) {
        return cartRepository.getCourseInCart(username) != null;
    }

    public boolean validateCourseIsInCart(String username, String course) {
        List<String> courseList = getCouseList(username);
        if (courseList == null || courseList.size() == 0) {
            return false;
        }
        for (String element : courseList) {
            if (element.equals(course)) {
                return true;
            }
        }
        return false;
    }

    public void checkOutCourseInCart(String username) {
        cartRepository.clearCart(username);
    }

}
