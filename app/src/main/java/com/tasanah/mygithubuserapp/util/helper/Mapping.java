package com.tasanah.mygithubuserapp.util.helper;

import android.database.Cursor;
import com.tasanah.mygithubuserapp.database.DbContract;
import com.tasanah.mygithubuserapp.model.User;

import java.util.ArrayList;

public class Mapping {

    public static ArrayList<User> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<User> userList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DbContract.UserColumns._ID));
            String login = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DbContract.UserColumns.LOGIN));
            String name = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DbContract.UserColumns.NAME));
            String avatar_url = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DbContract.UserColumns.AVATAR_URL));

            userList.add(new User(id, login, name, avatar_url));
        }

        return userList;
    }
}

