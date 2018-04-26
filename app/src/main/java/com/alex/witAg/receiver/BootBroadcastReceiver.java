package com.alex.witAg.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alex.witAg.ui.activity.SplashActivity;
import com.alex.witAg.utils.BootReceiver;

/**
 * Created by Administrator on 2018-04-02.
 */

public class BootBroadcastReceiver extends BootReceiver {
    private String TAG = BootBroadcastReceiver.class.getName();
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
/*
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            // 开机即启动此应用
            Intent mIntent = new Intent(context, SplashActivity.class);
            Log.i(TAG,"开机启动");
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }*/
}
