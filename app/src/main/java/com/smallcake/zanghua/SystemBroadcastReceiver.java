package com.smallcake.zanghua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class SystemBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26)
        {
            context.startForegroundService(new Intent(context, BGService.class));
        } else {
            context.startService(new Intent(context, BGService.class));
        }

        Log.d("SystemBroadcastReceiver","收到系统启动广播");
    }

}
