package com.example.finalproject.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.User;
import com.example.finalproject.utils.DButils;
import com.example.finalproject.utils.TextUtil;

import java.util.List;

public class UserRepository {

    private DButils dButils;

    public UserRepository(Context context) {
        dButils = new DButils(context);
    }

    public int saveAccount(User user) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_username, user.getUsername());
        values.put(User.KEY_password, user.getPassword());
        values.put(User.KEY_registeredCourses, TextUtil.listToString(user.getRegisteredCourses()));
        long res = db.insert(User.TABLE,null,values);
        db.close();
        return (int)res;
    }

    public boolean findAccount(String username, String password) {
        SQLiteDatabase db = dButils.getReadableDatabase();
        String selectQuery = "SELECT *"+
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_username + "=?"
                +" and " + User.KEY_password + " =?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{username, password});
        return cursor.moveToFirst();
    }

    public boolean findUsername(String username) {
        SQLiteDatabase db = dButils.getReadableDatabase();
        String selectQuery = "SELECT *"+
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_username + "=?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        return cursor.moveToFirst();
    }

    public void updateUserRegisteredCourse(String username, String courses) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_registeredCourses, courses);
        long res = db.update(User.TABLE, values, Cart.KEY_username + "=?", new String[]{username});
        db.close();
    }

    public void cleanData() {
        SQLiteDatabase db = dButils.getWritableDatabase();
        String deleteQuery = "Delete from " + User.TABLE;
        db.execSQL(deleteQuery);
    }

    public List<String> getRegisteredCourseByUsername(String username) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        String selectQuery = "SELECT *"+
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_username + "=?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        if (cursor.moveToFirst()) {
            return TextUtil.stringToList(cursor.getString(cursor.getColumnIndex(User.KEY_registeredCourses)));
        } else {
            return null;
        }
    }

}
