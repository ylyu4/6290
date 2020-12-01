package com.example.finalproject.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.finalproject.model.Course;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, formatter);
    }

    public static boolean validateTimeConflicts(List<Course> courseList) {
        HashMap<String, List<LocalTime>> map = new HashMap<>();

        for (Course course : courseList) {
            if (!map.containsKey(course.getDay())) {
                map.put(course.getDay(), new ArrayList<>(Arrays.asList(parseTime(course.getStartTime()), parseTime(course.getEndTime()))));

            } else {
                if (parseTime(course.getStartTime()).isAfter(Objects.requireNonNull(map.get(course.getDay())).get(0))
                        && parseTime(course.getStartTime()).isBefore(Objects.requireNonNull(map.get(course.getDay())).get(1))) {
                    return true;
                }

                if (parseTime(course.getEndTime()).isAfter(Objects.requireNonNull(map.get(course.getDay())).get(0))
                        && parseTime(course.getStartTime()).isBefore(Objects.requireNonNull(map.get(course.getDay())).get(1))) {
                    return true;
                }
            }
        }

        return false;
    }

}
