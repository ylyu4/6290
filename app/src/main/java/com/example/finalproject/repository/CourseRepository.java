package com.example.finalproject.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.finalproject.model.Course;
import com.example.finalproject.utils.DButils;



public class CourseRepository {

    private DButils dButils;

    public CourseRepository(Context context) {
        dButils = new DButils(context);
    }


    // get course data by a course number
    public Course getCourseByCourseNumber(String courseNumber) {
        SQLiteDatabase db = dButils.getReadableDatabase();
        String selectQuery = "SELECT *"+
                " FROM " + Course.TABLE
                + " WHERE " +
                Course.KEY_courseNumber + "=?";

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{courseNumber});
        if (cursor.moveToFirst()) {
            return Course.builder().courseNumber(cursor.getString(cursor.getColumnIndex(Course.KEY_courseNumber)))
                    .title(cursor.getString(cursor.getColumnIndex(Course.KEY_title)))
                    .creditHours(cursor.getString(cursor.getColumnIndex(Course.KEY_creditHours)))
                    .description(cursor.getString(cursor.getColumnIndex(Course.KEY_description)))
                    .crn(cursor.getString(cursor.getColumnIndex(Course.KEY_crn)))
                    .section(cursor.getString(cursor.getColumnIndex(Course.KEY_section)))
                    .day(cursor.getString(cursor.getColumnIndex(Course.KEY_day)))
                    .startTime(cursor.getString(cursor.getColumnIndex(Course.KEY_startTime)))
                    .endTime(cursor.getString(cursor.getColumnIndex(Course.KEY_endTime)))
                    .instructor(cursor.getString(cursor.getColumnIndex(Course.KEY_instructor)))
                    .location(cursor.getString(cursor.getColumnIndex(Course.KEY_location)))
                    .prerequisites(cursor.getString(cursor.getColumnIndex(Course.KEY_prerequisites)))
                    .build();
        } else {
            return null;
        }


    }


}
