package com.example.finalproject.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    public static final String TABLE = "Course";

    public static final String KEY_courseNumber = "courseNumber";

    public static final String KEY_title = "title";

    public static final String KEY_creditHours = "creditHours";

    public static final String KEY_description = "description";

    public static final String KEY_crn = "crn";

    public static final String KEY_section = "section";

    public static final String KEY_day = "day";

    public static final String KEY_startTime = "startTime";

    public static final String KEY_endTime = "endTime";

    public static final String KEY_instructor = "instructor";

    public static final String KEY_location = "location";

    public static final String KEY_prerequisites = "prerequisites";


    private String courseNumber;

    private String title;

    private String creditHours;

    private String description;

    private String crn;

    private String section;

    private String day;

    private String startTime;

    private String endTime;

    private String instructor;

    private String location;

    private String prerequisites;


}

