package com.example.finalproject.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class LocationUtil {


    private static HashMap<String, List<Double>> locationMap = new HashMap<String, List<Double>>() {{
        put("Duques-Bus", Arrays.asList(38.89885506942075, -77.04901317039368));
        put("Funger", Arrays.asList(38.898535541751244, -77.04947632035292));
    }};




    public static List<Double> getLangitudeAndLatitude(String location) {
        String[] arr = location.split(" ");

        if (locationMap.containsKey(arr[0])) {
            return locationMap.get(arr[0]);
        } else {
            return Arrays.asList(21.262130243719913, -157.80601553877577);
        }
    }
}
