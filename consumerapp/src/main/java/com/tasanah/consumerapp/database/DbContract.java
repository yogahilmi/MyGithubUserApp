package com.tasanah.consumerapp.database;

import android.net.Uri;
import android.provider.BaseColumns;
import android.database.Cursor;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class DbContract {

    public static final String AUTHORITY = "com.tasanah.mygithubuserapp";
    public static final String SCHEME = "content";

    public static final class UserColumns implements BaseColumns{
        public static final String TABLE_USER_NAME = "user";
        public static final String USERNAME = "username";
        public static final String URL = "url";
        public static final String AVATAR = "avatar";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_USER_NAME)
                .build();
    }

    public static String getFavoriteItem(Cursor cursor,String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }
}

