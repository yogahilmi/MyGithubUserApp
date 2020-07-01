package com.tasanah.mygithubuserapp.util.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.tasanah.mygithubuserapp.database.FavouriteHelper;
import java.util.Objects;

import static com.tasanah.mygithubuserapp.database.DbContract.AUTHORITY;
import static com.tasanah.mygithubuserapp.database.DbContract.TABLE_FAVORITE_NAME;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.CONTENT_URI;

public class FavouriteProvider extends ContentProvider {

    //untuk mengecek URI yang masuk, bersifat ALL atau ada tambahan ID nya
    private static final int FAV = 1;
    private static final int FAV_ID = 2;
    private FavouriteHelper favouriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //content://com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_NAME, FAV);

        //content://com.dicoding.androidfundamental.submission3aplikasigithubuserfinal
        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE_NAME + "/#",
                FAV_ID);
    }

    @Override
    public boolean onCreate() {
        favouriteHelper = FavouriteHelper.getInstance(getContext());
        favouriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                cursor = favouriteHelper.queryAll();
                break;

            case FAV_ID:
                cursor = favouriteHelper.queryByLogin(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        if (sUriMatcher.match(uri) == FAV_ID) {
            deleted = favouriteHelper.deleteById(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;
        if (sUriMatcher.match(uri) == FAV) {
            added = favouriteHelper.insert(values);
        } else {
            added = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        if (sUriMatcher.match(uri) == FAV_ID) {
            updated = favouriteHelper.update(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }
}
