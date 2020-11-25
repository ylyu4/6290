package com.example.finalproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.model.Schedule;
import com.example.finalproject.utils.DButils;
import com.example.finalproject.utils.TextUtil;

public class ScheduleRepository {

    private DButils dButils;

    public ScheduleRepository(Context context) {
        dButils = new DButils(context);
    }

    public int saveSchedule(Schedule schedule) {
        SQLiteDatabase db = dButils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schedule.KEY_username, schedule.getUsername());
        values.put(Schedule.KEY_selectedCourse, TextUtil.listToString(schedule.getSelectedCourse()));
        long res = db.insert(Schedule.TABLE,null,values);
        db.close();
        return (int)res;
    }

}
