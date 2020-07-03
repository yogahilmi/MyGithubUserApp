package com.tasanah.mygithubuserapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.tasanah.mygithubuserapp.database.UserHelper;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import static com.tasanah.mygithubuserapp.database.DbContract.AUTHORITY;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.CONTENT_URI;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.TABLE_USER_NAME;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class UserContentProvider extends ContentProvider {
    private static final int USER = 0;
    private static final int USER_ID = 1;
    UserHelper userHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,TABLE_USER_NAME,USER);
        uriMatcher.addURI(AUTHORITY,TABLE_USER_NAME + "/#",USER_ID);
    }
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case USER:
                cursor = userHelper.queryAll();
                break;
            case USER_ID:
                cursor = userHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
        // TODO: Implement this to handle query requests from clients.
    }

    @Override
    public String getType(@NotNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues values) {
        long added;
        Uri contentUri = null;
        if (uriMatcher.match(uri) == USER) {
            added = userHelper.InsertProvider(values);
            if (added > 0) {
                contentUri = ContentUris.withAppendedId(CONTENT_URI, added);
            }
        } else {
            added = 0;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return contentUri;
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int update;
        if (uriMatcher.match(uri) == USER_ID) {
            update = userHelper.UpdateProvider(uri.getLastPathSegment(), values);
        } else {
            update = 0;
        }

        if (update > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return update;
    }

    @Override
    public int delete(@NotNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int delete;
        if (uriMatcher.match(uri) == USER_ID) {
            delete = userHelper.DeleteProvider(uri.getLastPathSegment());
        } else {
            delete = 0;
        }
        if (delete > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }
}