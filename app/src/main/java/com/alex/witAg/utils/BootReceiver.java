package com.alex.witAg.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alex.witAg.ui.activity.MainActivity;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){

            Intent mBootIntent = new Intent(context, MainActivity.class);
            mBootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mBootIntent);
        }

    }
}
