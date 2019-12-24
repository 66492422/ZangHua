package com.smallcake.zanghua;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BGService extends Service {
    private final int NoticeID_Zanghua = 0x01;
    private Timer mTimerNotice = null;
    private NotificationManager mNotificationManager = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mNotificationManager == null)
        {
            mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        }

        if (mTimerNotice == null)
        {
            mTimerNotice = new Timer();
            mTimerNotice.schedule(new TimerTask() {
                @Override
                public void run() {


                    Notification.Builder builder = new Notification.Builder(BGService.this);
                    if (Build.VERSION.SDK_INT >= 26)
                    {
                        mNotificationManager.createNotificationChannel(new NotificationChannel("zanghua", getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT));
                        builder.setChannelId("zanghua");
                    }

                    ZanghuaDataBase zanghuaDataBase = new ZanghuaDataBase(BGService.this);
                    String msg = zanghuaDataBase.randomGet(true);
                    zanghuaDataBase.close();

                    Intent intent = new Intent(BGService.this, MainActivity.class);
                    intent.putExtra("msg", msg);


                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(BGService.this);
                    taskStackBuilder.addNextIntent(intent);

                    builder.setContentTitle("脏话");
                    builder.setContentText(msg);
                    builder.setSmallIcon(R.drawable.laba);
                    builder.setOngoing(true);

                    builder.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));
                    mNotificationManager.notify(NoticeID_Zanghua, builder.build());

                    Log.d("TimerNotice","脏话");

                }
            }, 1000, 600000);
        }

    }
}
