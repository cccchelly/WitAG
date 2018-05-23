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
    private boolean flagStop = false;

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, ShareUtil.getTaskTime());
            Log.i("--post--","post_start");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CapturePostUtil.findLocalPic();
                }
            }).start();
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
        flagStop = false;  //服务启动

        new Thread(() -> {
            try {
                String taskTime = ShareUtil.getStartTaskTime();
                while (true) {
                    String nowTime = TimeUtils.millis2String(System.currentTimeMillis(), new SimpleDateFormat("HH:mm"));
                    Log.i(TAG,"post_time1="+taskTime+"time2="+nowTime);
                    if (flagStop) {    //检测到服务销毁，跳出循环
                        Log.i(TAG,"上传旧任务停止");
                        break;
                    }
                    if (TextUtils.equals(taskTime, nowTime)) {
                        mHandler.postDelayed(r, 5  *60* 1000);//延时执行上传服务
                        break;
                    } else {
                        Thread.sleep(1000);
                    }
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
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
        //服务销毁，取消handler循环，置反标志位，时间检测循环退出
        mHandler.removeCallbacks(r);
        flagStop = true;
        super.onDestroy();
    }
}
