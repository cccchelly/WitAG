package com.alex.witAg.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.UpdateMsgBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.receiver.UpdateReStartReceiver;
import com.alex.witAg.ui.activity.SplashActivity;
import com.google.gson.Gson;

import java.io.File;

import ezy.boost.update.ApkController;
import ezy.boost.update.ICheckAgent;
import ezy.boost.update.IInstall;
import ezy.boost.update.IUpdateChecker;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Administrator on 2018-04-16.
 */

public class AppUpdateUtil {
    private static String TAG = AppUpdateUtil.class.getName();

    static String mCheckUrl = "http://client.waimai.baidu.com/message/updatetag";

    static String mUpdateUrl = "http://p3o0oo73j.bkt.clouddn.com/witag.apk";

    public static void check(boolean isManual, final boolean hasUpdate, final boolean isForce, final boolean isSilent, final boolean isIgnorable, final int
            notifyId, Context context) {
        UpdateManager.create(context)
                .setWifiOnly(false).setChecker(new IUpdateChecker() {
            @Override
            public void check(ICheckAgent agent, String url) {
                Log.e("ezy.update", "checking");
                AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                        .getVersion(ShareUtil.getToken(),AppMsgUtil.getVersionCode(context)+"")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse<UpdateMsgBean>>() {
                            @Override
                            public void onSuccess(BaseResponse<UpdateMsgBean> response) {
                                Log.i("==version==",response.getData().toString());
                                agent.setInfo(response.getData().toString());
                            }
                        });
                //agent.setInfo("");
            }
        }).setUrl(mCheckUrl).setManual(isManual).setNotifyId(notifyId).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                Log.i(TAG,"parse");
                Log.i("==version2==",source);
                UpdateMsgBean msgBean = new Gson().fromJson(source,UpdateMsgBean.class);
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;

                /*info.updateContent = "更新内容测试";
                info.versionCode = 10001;
                info.versionName = "v1.0.1";
                info.url = mUpdateUrl;
                info.md5 = "3567c503cb4f3ba9be2916b2b0b2f233";
                info.size = 29348064;*/

                info.updateContent = msgBean.getContent();
                info.versionCode = msgBean.getCode();
                info.versionName = msgBean.getName();
                info.url = mUpdateUrl;
                info.md5 = msgBean.getMd5();
                info.size = msgBean.getSize();

                info.isForce = isForce;
                info.isIgnorable = isIgnorable;
                info.isSilent = isSilent;
                info.isAutoInstall =true;
                return info;
            }
        })
                .setInstall(new IInstall() {
                    @Override
                    public void install(Context context, File file, boolean force) throws Exception {
                        //主要就是通过,安装程序之前,启动一个定时任务,任务发送一个广播,广播收到之后,启动程序
                        Intent ite = new Intent();
                        ite.setAction("install_and_start");
                        context.sendBroadcast(ite);
                        AppUpdateUtil.install(context,file,force);
                        Log.i("====install2",file.getAbsolutePath());
                    }
                }).check();
    }

    public static void install(Context context, File file, boolean force) {
        ApkController.install(file.getPath(),context);
        //ApkController.startApp("com.alex.witAg","com.alex.witAg.ui.activity.SplashActivity");

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".updatefileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
        if (force) {
            System.exit(0);
        }
    }
}
