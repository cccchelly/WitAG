package com.alex.witAg.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alex.witAg.App;
import com.alex.witAg.utils.AppUpdateUtil;
import com.alex.witAg.utils.ShareUtil;

/**
 * Created by Administrator on 2018-04-16.
 */

public class UpdateService extends Service {
    Handler mHandler =  new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            AppUpdateUtil.check(true, true, false, true, true, 998, App.getAppContext());    //检查新版本
            mHandler.postDelayed(this, ShareUtil.getTaskTime()+60*60*1000);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
