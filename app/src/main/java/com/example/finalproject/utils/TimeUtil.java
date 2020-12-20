package com.example.finalproject.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.finalproject.model.Course;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        HashMap<String, List<List<LocalTime>>> map = new HashMap<>();

        for (Course course : courseList) {
            String[] arr = course.getDay().split("/");
            for (String s : arr) {
                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<>(Collections.singletonList(Arrays.asList(parseTime(course.getStartTime()), parseTime(course.getEndTime())))));
                } else {
                    for (int i = 0; i < Objects.requireNonNull(map.get(s)).size(); i++) {
                        if (parseTime(course.getStartTime()).isAfter(Objects.requireNonNull(map.get(s)).get(i).get(0))
                                && parseTime(course.getStartTime()).isBefore(Objects.requireNonNull(map.get(s)).get(i).get(1))) {
                            return true;
                        }

                        if (parseTime(course.getEndTime()).isAfter(Objects.requireNonNull(map.get(s)).get(i).get(0))
                                && parseTime(course.getStartTime()).isBefore(Objects.requireNonNull(map.get(s)).get(i).get(1))) {
                            return true;
                        }
                    }
                    List<List<LocalTime>> values = map.get(s);
                    assert values != null;
                    values.add(Arrays.asList(parseTime(course.getStartTime()), parseTime(course.getEndTime())));
                    map.put(s, values);
                }
            }
        }
        return false;
    }

}

