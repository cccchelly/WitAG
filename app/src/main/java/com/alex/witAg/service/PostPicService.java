package com.alex.witAg.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.utils.CapturePostUtil;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TimeUtils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/3/31.
 */
public class PostPicService extends Service {
    public static String actionPost = "action_post_pic";
    private String TAG = PostPicService.class.getName();

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            CapturePostUtil.findLocalPic();
            mHandler.postDelayed(this, ShareUtil.getTaskTime());}
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            while (true) {
                String taskTime =  ShareUtil.getStartTaskTime();
                String nowTime =  TimeUtils.millis2String(System.currentTimeMillis(),new SimpleDateFormat("HH:mm"));
                if (TextUtils.equals(taskTime,nowTime)) {
                    mHandler.postDelayed(r, 5*60*1000);//延时执行上传服务
                    break;
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Log.e(TAG, "--------->onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "--------->onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "--------->onDestroy: ");
        super.onDestroy();
    }
}
