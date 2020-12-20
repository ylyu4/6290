package com.example.finalproject.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.utils.DButils;

public class InstructorRepository {


    private DButils dButils;

    public InstructorRepository(Context context) {
        dButils = new DButils(context);
    }


    // get instructor information by instructor name
    public Instructor getInstructorByName(String name) {
        SQLiteDatabase db = dButils.getReadableDatabase();
        String selectQuery = "SELECT *"+
                " FROM " + Instructor.TABLE
                + " WHERE " +
                Instructor.KEY_name + "=?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{name});
        if (cursor.moveToFirst()) {
            return new Instructor(cursor.getString(cursor.getColumnIndex(Instructor.KEY_name)), cursor.getString(cursor.getColumnIndex(Instructor.KEY_phoneNumber)),
                    cursor.getString(cursor.getColumnIndex(Instructor.KEY_email)), cursor.getString(cursor.getColumnIndex(Instructor.KEY_office)),
                    cursor.getString(cursor.getColumnIndex(Instructor.KEY_rateMyProfessorId)));
        }
        return null;
    }
}
