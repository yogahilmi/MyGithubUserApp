package com.tasanah.mygithubuserapp.view.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.bumptech.glide.Glide;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.model.UserDetail;
import java.util.concurrent.ExecutionException;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.CONTENT_URI;

/**
 Created by Yoga Hilmi Tasanah
 1 Juli 2020
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        if (cursor != null) {
            cursor.close();
        }

        final long identitiyToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identitiyToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position))
        {
            return null;
        }

        UserDetail detail = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        String avatar = detail.getAvatarUrl();
        String username = detail.getLogin();
        try {

            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(avatar)
                    .submit()
                    .get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putString(ImageBannerWidget.EXTRA_ITEM, username);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(0);
        } else return position;
    }

    private UserDetail getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new UserDetail(cursor);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
