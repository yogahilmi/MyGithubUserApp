package com.tasanah.mygithubuserapp.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tasanah.mygithubuserapp.R;

import java.util.Objects;

/**
 Created by Yoga Hilmi Tasanah
 1 Juli 2020
 */

public class ImageBannerWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.tasanah.mygithubuserapp.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.tasanah.mygithubuserapp.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "com.tasanah.mygithubuserapp.UPDATE_WIDGET";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);
        Intent toastIntent = new Intent(context, ImageBannerWidget.class);
        toastIntent.setAction(ImageBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                String name = intent.getStringExtra(EXTRA_ITEM);
                Toast.makeText(context, "Touched view " + name, Toast.LENGTH_SHORT).show();
            }
        }

        if (Objects.equals(intent.getAction(), UPDATE_WIDGET)){
            AppWidgetManager update = AppWidgetManager.getInstance(context);
            int[] ids = update.getAppWidgetIds(new ComponentName(context, ImageBannerWidget.class));
            update.notifyAppWidgetViewDataChanged(ids ,R.id.stack_view);
        }
        super.onReceive(context, intent);
    }
}

