package com.tasanah.mygithubuserapp.view.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 Created by Yoga Hilmi Tasanah
 1 Juli 2020
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
