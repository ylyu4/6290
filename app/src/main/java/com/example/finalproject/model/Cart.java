package com.example.finalproject.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    public static final String TABLE = "Cart";

    public static final String KEY_username = "username";

    public static final String KEY_courseInCart = "courseInCart";

    private String username;

    private List<String> courseInCart;
}
