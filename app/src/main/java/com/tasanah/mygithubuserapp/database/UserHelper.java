package com.tasanah.mygithubuserapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tasanah.mygithubuserapp.model.User;
import java.util.ArrayList;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.AVATAR;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.ID;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.TABLE_USER_NAME;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.URL;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.USERNAME;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class UserHelper {
    private static final String DATABASE_TABLE = TABLE_USER_NAME;
    private static DbHelper dbHelper;
    private static UserHelper INST;
    private static SQLiteDatabase database;

    public UserHelper(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INST == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INST == null) {
                    INST = new UserHelper(context);
                }
            }
        }
        return INST;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID+ " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public ArrayList<User> getDataUser(){
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,
                null,
                null,
                null,
                null,
                USERNAME + " ASC",
                null);
        cursor.moveToFirst();
        User user;
        if (cursor.getCount() > 0){
            do {
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                user.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME)));
                user.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR)));
                user.setHtmlUrl(cursor.getString(cursor.getColumnIndexOrThrow(URL)));
                userList.add(user);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }cursor.close();
        return userList;
    }

    public void userInsert(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,user.getId());
        contentValues.put(USERNAME,user.getLogin());
        contentValues.put(URL,user.getHtmlUrl());
        contentValues.put(AVATAR,user.getAvatarUrl());

        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public void userDelete(String id){
        database.delete(TABLE_USER_NAME, ID + " = '" + id + "'", null);
    }
    public int DeleteProvider(String id) {
        return database.delete(TABLE_USER_NAME, ID+ "=?",new String[]{id});
    }
    public int UpdateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " =?", new String[]{id});
    }
    public long InsertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
}