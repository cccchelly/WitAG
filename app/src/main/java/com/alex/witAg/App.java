package com.alex.witAg;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.multidex.MultiDexApplication;

import com.alex.witAg.ui.test.CrashUtil;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class App extends MultiDexApplication {

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

        initLogger();
        initARouter();
        initFresco();
        LitePal.initialize(mAppContext);
        CrashUtil.getInstance().init(mAppContext);
        initUmeng();

        refWatcher = LeakCanary.install(this);

    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initUmeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this,AppContants.UMENG_APP_KEY, "Umeng", UMConfigure.DEVICE_TYPE_BOX, AppContants.UMENG_SECERT);
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
                .newBuilder()
                .tag(AppContants.APP_TAG)
                .build()
        ) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private static  int iLogId = -1; // return by NET_DVR_Login_v30
    private static int iChanNum = 0; // channel number
    private static int iStartChan = 0;

    private static  boolean isTaskRun = false;
    private static boolean isOprate = false;
    private static boolean isNeedReLogin = false;  //是否需要重新登录（修改登录相机参数时需要）

    private static boolean isWaitTaskFinish = false; //等待任务队列中当前任务执行完

    public static boolean isIsWaitTaskFinish() {
        return isWaitTaskFinish;
    }

    public static void setIsWaitTaskFinish(boolean isWaitTaskFinish) {
        App.isWaitTaskFinish = isWaitTaskFinish;
    }

    public static boolean isIsNeedReLogin() {
        return isNeedReLogin;
    }

    public static void setIsNeedReLogin(boolean isNeedReLogin) {
        App.isNeedReLogin = isNeedReLogin;
    }

    public static Context getmAppContext() {
        return mAppContext;
    }

    public static void setmAppContext(Context mAppContext) {
        App.mAppContext = mAppContext;
    }

    public static boolean getIsTaskRun() {
        return isTaskRun;
    }
    public static boolean getIsOprate() {
        return isOprate;
    }

    public static void setIsTaskRun(boolean isTaskRun) {
        App.isTaskRun = isTaskRun;
    }
    public static void setIsOprate(boolean isOprate) {
        App.isOprate = isOprate;
    }


    public static int getiLogId() {
        return iLogId;
    }

    public static void setiLogId(int iLogId) {
        App.iLogId = iLogId;
    }

    public static int getiChanNum() {
        return iChanNum;
    }

    public static void setiChanNum(int iChanNum) {
        App.iChanNum = iChanNum;
    }

    public static int getiStartChan() {
        return iStartChan;
    }

    public static void setiStartChan(int iStartChan) {
        App.iStartChan = iStartChan;
    }

}
