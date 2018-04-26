package com.alex.witAg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alex.witAg.ui.activity.SplashActivity;
import com.alex.witAg.utils.ToastUtils;

/**
 * Created by Administrator on 2018-04-13.
 */

public class UpdateReStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

     /*   if (intent.getAction().endsWith("install_and_start")){
            ToastUtils.showToast("升级安装包，重启程序");
            Intent intent2 = new Intent(context, SplashActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }*/
     /*if (intent.getAction().contains("PACKAGE")) {
         Log.i("==receiver==", intent.getAction());
     }
        // TODO Auto-generated method stub
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
            ToastUtils.showToast("升级了一个安装包，重新启动此程序");
            Intent intent2 = new Intent(context, SplashActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            System.out.println("安装了:" +packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("卸载了:"  + packageName + "包名的程序");

        }*/

    }
}
