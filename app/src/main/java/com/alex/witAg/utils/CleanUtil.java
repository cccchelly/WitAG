package com.alex.witAg.utils;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;

/**
 * Created by Administrator on 2018-04-11.
 */

public class CleanUtil {

    public static void resetSetting(){
        ShareUtil.saveTaskTime(AppContants.TASK_DEFAULT_TIME);
        ShareUtil.saveIp(AppContants.strIP);
        ShareUtil.savePort(AppContants.nPort);
        ShareUtil.saveUser(AppContants.strUser);
        ShareUtil.savePass(AppContants.strPsd);
        ShareUtil.cleanToken();
        ShareUtil.cleanAndroidPass();
        //ShareUtil.cleanBindComMsg();
        App.setIsNeedReLogin(true);
    }

}
