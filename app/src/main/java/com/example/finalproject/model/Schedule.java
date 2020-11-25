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
public class Schedule {

    public static final String TABLE = "Schedule";

    public static final String KEY_username = "username";

    public static final String KEY_selectedCourse = "selectedCourse";

    private String username;

    private List<String> selectedCourse;
}
