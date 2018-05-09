package com.alex.witAg.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.taskqueue.SeralTask;
import com.alex.witAg.taskqueue.TaskQueue;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.utils.ToastUtils;

import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2018-03-28.
 */

public class CaptureService extends Service {
    public static String action = "capture_action";
    private static final String TAG = CaptureService.class.getName();
    Handler toastHandle =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ToastUtils.showToast(msg.obj.toString());
        }
    };

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    App.setIsTaskRun(true);
                    Log.i(TAG, App.getIsTaskRun() + " ----startCaptureTask");
                    toastOnMain("定时任务开始执行");
                    //每隔一段时间循环执行run方法
                    CaptureTaskUtil captureTaskUtil = CaptureTaskUtil.instance();
                    TaskQueue taskQueue = TaskQueue.getInstance();
                    Log.i(TAG,"==检查是否处于复位状态==");
                    String statue = ShareUtil.getDeviceStatue();
                    if (!TextUtils.equals(statue, SerialInforStrUtil.STA_CLOSE_RESET)) {  //设备不在复位状态则强制复位
                        Log.i(TAG,"==执行复位指令==");
                        toastOnMain("准备复位");
                        taskQueue.add(new SeralTask(SerialInforStrUtil.getForceRestartStr()));
                        isStatueChanged(SerialInforStrUtil.STA_CLOSE_RESET);    //持续检查是否到达复位状态，到达则继续执行
                    }
                    Log.i(TAG,"==打开相机并翻转到正面==");
                    toastOnMain("准备打开相机并翻转到正面");
                    captureTaskUtil.openCaptureTurnPositive();  //打开摄像头并翻转到正面
                    captureTaskUtil.isCaptureOpenLong();   //持续查询是否收到相机已打开命令
                    //sleepMills(60 * 1000);
                    Log.i(TAG,"==持续登录摄像头==");
                    toastOnMain("登录摄像头");
                    captureTaskUtil.loginCaptureLong();  //登录摄像头(若登录失败则重新继续登录,若账号密码错误则放弃登录)
                    if (isStatueChanged(SerialInforStrUtil.STA_OPEN_POSITIVE)) { //1
                        Log.i(TAG,"==拍摄正面==");
                        toastOnMain("拍摄正面照片");
                        captureTaskUtil.capture(CaptureTaskUtil.FROM_TASK);
                        sleepMills(5 * 1000);
                    }
                    Log.i(TAG,"==翻转到反面==");
                    toastOnMain("翻转到反面");
                    taskQueue.add(new SeralTask(SerialInforStrUtil.getDeclineStr())); //翻转到反面  2
                    if (isStatueChanged(SerialInforStrUtil.STA_OPEN_OPPOSITE)) {
                        Log.i(TAG,"==拍摄反面==");
                        toastOnMain("拍摄反面照片");
                        captureTaskUtil.capture(CaptureTaskUtil.FROM_TASK);
                        sleepMills(5 * 1000);
                    }
                    Log.i(TAG,"==复位关机==");
                    toastOnMain("复位并关机");
                    taskQueue.add(new SeralTask(SerialInforStrUtil.getForceRestartStr()));  //复位关机
                    sleepMills(2*60*1000);
                    App.setIsTaskRun(false);
                    Log.i(TAG, App.getIsTaskRun() + " ----completeCaptureTask");
                    mHandler.postDelayed(this, ShareUtil.getTaskTime());
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
        App.setIsTaskRun(false);
        Log.e(TAG, "--------->onCreate: ");
        new Thread(() -> {
            //mHandler.postDelayed(r, 0);//发送请求开始执行
            while (true) {
                String taskTime = ShareUtil.getStartTaskTime();
                String nowTime = TimeUtils.millis2String(System.currentTimeMillis(), new SimpleDateFormat("HH:mm"));
                //Log.i(TAG,"time1="+taskTime+"---time2="+nowTime);
                if (TextUtils.equals(taskTime, nowTime)) {
                    mHandler.postDelayed(r, 0);//发送请求开始执行
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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

    private boolean isStatueChanged(String nextStatus) {
        boolean isChange = false;
        String next = "";
        for (int i = 1; i < 5 * 60; i++) {    //查询状态是否改变  若状态未改变休眠一秒继续查询
            //Log.i(TAG, "sta=" + ShareUtil.getDeviceStatue() + ",s=" + nextStatus);
            if (!TextUtils.equals(nextStatus, ShareUtil.getDeviceStatue())) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                isChange = true;
            }
        }
        return isChange;
    }

    private void toastOnMain(String content) {
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = content;
        toastHandle.sendMessage(msg);
    }

    void sleepMills(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
