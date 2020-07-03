package com.tasanah.mygithubuserapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.TABLE_USER_NAME;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String USER_DB_NAME = "userdbname";
    private static final int USER_DB_VERSION = 2;

    private static final String SQL_CREATE_TABLE_USER = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TABLE_USER_NAME,
            DbContract.UserColumns.ID,
            DbContract.UserColumns.USERNAME,
            DbContract.UserColumns.URL,
            DbContract.UserColumns.AVATAR
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        onCreate(db);
    }

    public DbHelper(Context c){
        super(c,USER_DB_NAME,null,USER_DB_VERSION);
    }
}

