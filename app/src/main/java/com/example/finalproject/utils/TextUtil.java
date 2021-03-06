package com.example.finalproject.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtil {

    private static Integer USERNAME_LENGTH = 6;

    private static Integer MINIMUN_PASSWORD_LENGTH = 10;

    private static Integer MAXIMUM_PASSWORD_LENGTH = 10;


    // validate username length
    public static boolean validateUsernameLength(String username) {
        return username.length() == USERNAME_LENGTH;
    }

    // validate password length
    public static boolean validatePasswordLength(String password) {
        return password.length() >= MINIMUN_PASSWORD_LENGTH && password.length() <= MAXIMUM_PASSWORD_LENGTH;
    }

    // convert list string to string
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder converted = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                converted.append(list.get(i)).append(",");
            } else {
                converted.append(list.get(i));
            }
        }
        return converted.toString();
    }

    // split string to a list of string
    public static List<String> stringToList(String str) {
        String[] arr = str.split(",");
        return new ArrayList<>(Arrays.asList(arr));
    }

    // parse crn string
    public static String parseOnlineCrn(String str) {
        StringBuilder sb  = new StringBuilder();
        char[] cha = str.toCharArray();

        for (char c : cha) {
            if (sb.length() == 5) {
                sb.append("/");
            }

            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6'
                    || c == '7' || c == '8' || c == '9') {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}

