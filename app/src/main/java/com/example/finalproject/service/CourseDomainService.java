package com.example.finalproject.service;

import android.content.Context;
import com.example.finalproject.model.Course;
import com.example.finalproject.repository.CourseRepository;


public class CourseDomainService {

    private final CourseRepository courseRepository;

    public CourseDomainService(Context context) {
        courseRepository = new CourseRepository(context);
    }

    public Course getCourse(String courseNumber) {
        return courseRepository.getCourseByCourseNumber(courseNumber);
    }
}
