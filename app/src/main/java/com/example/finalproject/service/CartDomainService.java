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


    // add a course to the cart
    public void addCourseInCart(String username, String course) {
        if (cartRepository.checkUserHasCartRecord(username)) {
            Cart cart = cartRepository.getCourseInCart(username);
            cart.getCourseInCart().add(course);
            cartRepository.updateCourseInCart(cart.getUsername(), TextUtil.listToString(cart.getCourseInCart()));
        } else {
            cartRepository.addCourseToCartFirstTime(username, course);
        }
    }

    // drop a course from the cart
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

    // get how many courses in cart
    public List<String> getCouseList(String username) {
        Cart cart = getCart(username);
        if (cart != null) {
            return cartRepository.getCourseInCart(username).getCourseInCart();
        } else {
            return null;
        }

    }

    // get cart by username
    public Cart getCart(String username) {
        return cartRepository.getCourseInCart(username);
    }


    // check whether the user exists in the cart table or not
    public boolean validateUserExistInCart(String username) {
        return cartRepository.getCourseInCart(username) != null;
    }


    // validate a course exist in cart or not by the username
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


    // validate the number of courses in the cart
    public boolean validateMaxNumOfAllCoursesInCart(String username) {
        List<String> courseList = getCouseList(username);
        if (courseList == null) {
            return false;
        }
        if (courseList.size() == 6) {
            return true;
        }
        return false;
    }

    // register all courses and clear this data in the cart table
    public void checkOutCourseInCart(String username) {
        cartRepository.clearCart(username);
    }

}
