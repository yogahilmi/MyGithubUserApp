package com.tasanah.mygithubuserapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String AUTHORITY = "com.tasanah.mygithubuserapp";
    private static final String SCHEME = "content";

    public static String TABLE_FAVORITE_NAME = "favorite";

    public DbContract() {
    }

    public static final class UserColumns implements BaseColumns {

        public static String LOGIN = "login";
        public static String NAME = "name";
        public static String AVATAR_URL = "avatar_url";


        //Untuk membuat URI content://com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_NAME)
                .build();

    }
}
