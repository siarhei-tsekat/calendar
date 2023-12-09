package com.me.calendar.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.calendar.repository.dao.EventsDao;

public class Store extends SQLiteOpenHelper {

    private static final String DB_NAME = "calendar";
    private static final int DB_VERSION = 1;

    public Store(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = EventsDao.create_table_sql;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
}
