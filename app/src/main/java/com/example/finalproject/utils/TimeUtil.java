package com.example.finalproject.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, formatter);
    }

}
