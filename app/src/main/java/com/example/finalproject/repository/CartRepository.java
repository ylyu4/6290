package com.example.finalproject.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.finalproject.model.Cart;
import com.example.finalproject.utils.DButils;
import com.example.finalproject.utils.TextUtil;

public class CartRepository {

    private DButils dButils;

    public CartRepository(Context context) {
        dButils = new DButils(context);
    }

    public int addCourseToCartFistTime(String username, String course) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cart.KEY_username, username);
        values.put(Cart.KEY_courseInCart, course);
        long res = db.insert(Cart.TABLE,null,values);
        db.close();
        return (int)res;
    }

    public boolean checkUserHasCartRecord(String username) {
        SQLiteDatabase db = dButils.getReadableDatabase();
        String selectQuery = "select * from " + Cart.TABLE +
                " where " + Cart.KEY_username + "=?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        return cursor.moveToFirst();
    }

    public void updateCourseInCart(String username, String course) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cart.KEY_courseInCart, course);
        long res = db.update(Cart.TABLE, values, Cart.KEY_username + "=?", new String[]{username});
        db.close();
    }

    public Cart getCourseInCart(String username) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        String selectQuery = "select * from " + Cart.TABLE +
                " where " + Cart.KEY_username + "=?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        if (cursor.moveToFirst()) {
            return new Cart(cursor.getString(cursor.getColumnIndex(Cart.KEY_username)), TextUtil.stringToList(cursor.getString(cursor.getColumnIndex(Cart.KEY_courseInCart))));
        }
        return null;
    }

    public void clearCart(String username) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        db.execSQL("delete from " + Cart.TABLE + " where " + Cart.KEY_username + " =" + username);
    }
}
