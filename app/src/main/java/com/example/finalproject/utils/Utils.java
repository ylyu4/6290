package com.example.finalproject.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Integer USERNAME_LENGTH = 6;

    private static Integer MINIMUN_PASSWORD_LENGTH = 10;

    private static Integer MAXIMUM_PASSWORD_LENGTH = 10;

    public static boolean validateUsernameLength(String username) {
        return username.length() == USERNAME_LENGTH;
    }

    public static boolean isAlphaNumeric(String username){
        Pattern p = Pattern.compile("[0-9a-zA-Z]{1,}");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean validatePasswordLength(String password) {
        return password.length() >= MINIMUN_PASSWORD_LENGTH && password.length() <= MAXIMUM_PASSWORD_LENGTH;
    }
}

