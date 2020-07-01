package com.tasanah.mygithubuserapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.tasanah.mygithubuserapp.database.DbContract.TABLE_FAVORITE_NAME;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.LOGIN;

public class FavouriteHelper {
    private final String DATABASE_TABLE = TABLE_FAVORITE_NAME;
    private static DbHelper dbHelper;
    private static FavouriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavouriteHelper(Context context) {
        dbHelper = new DbHelper(context);
    }

    //Untuk inisisasi database
    public static FavouriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavouriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    //membuka dan menutup koneksi ke database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    //CRUD
    //Ambil Data
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    //Ambil Data dengan ID
    public Cursor queryByLogin(String login) {
        return database.query(
                DATABASE_TABLE,
                null,
                LOGIN + " = ?",
                new String[]{login},
                null,
                null,
                null,
                null);
    }

    //insert data
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    //update data
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[] {id});
    }

    //delete data
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
