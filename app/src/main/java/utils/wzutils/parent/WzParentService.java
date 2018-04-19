package utils.wzutils.parent;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;

/**
 * abc on 2018/2/2.
 */

public class WzParentService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(123456,getNotification());
        LogTool.s("onStartCommand" +this);
        return START_STICKY;
    }
    public Notification getNotification(){
        Notification.Builder mBuilder = new Notification.Builder(AppTool.getApplication());
        mBuilder.setShowWhen(false);
        mBuilder.setAutoCancel(false);
        mBuilder.setSmallIcon(getApplication().getApplicationInfo().icon);
        mBuilder.setContentText("thisiscontent");
        mBuilder.setContentTitle("this is title");
        mBuilder.setContentIntent(PendingIntent.getActivity(this,0,new Intent(getPackageName()),0));
        return mBuilder.build();
    }

}
