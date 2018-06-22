package com.alex.witAg;

import android.icu.util.TimeUnit;

import com.alex.witAg.utils.TimeUtils;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public interface AppContants {

    String API_BASE_URL     = "http://pk.meishifulu.cn";   //线上
    //String API_BASE_URL     = "http://172.168.21.33:9007";   //本地zy
    //String API_BASE_URL     = "http://172.16.23.179:9007";   //本地xxl
    String CHECK_VERSION_URL = API_BASE_URL+"update/index";
    int    CONNECT_TIME_OUT = 15;
    int    WRITE_TIME_OUT   = 15;
    int    READ_TIME_OUT    = 15;
    String APP_TAG          = "ParkYun";
    int LOGIN_INTERCEPTOR = 401;//通过ARouter拦截登录常量
    String HOME_CURRENT_TAB_POSITION = "home_current_tab_position";
    int TASK_DEFAULT_TIME = 24*60*60*1000; //定时任务默认时间ms
    String START_TASK_DEFAULT_TIME = "12:00"; //定时任务默认开始时间
    int PASS_CHECK_DEFAULT_TIME =  100000*60*1000; //默认密码验证超时时间
    String SHOW_PIC_URL_KEY = "SHOW_PIC_URL_KEY";

    String strIP = "192.168.0.64";//默认IP地址
    int nPort = 8000;//默认端口号
   /* String strUser = "admin";//默认用户名
    String strPsd = "1234qazz";//默认密码*/
    String strUser = "admin";//默认用户名
    String strPsd = "1234QAZZ";//默认密码

    String TOKEN_TRANS_KEY = "token_trans";

    String UMENG_APP_KEY = "5ad0192eb27b0a744a0000a3";  //umeng appkey
    String UMENG_SECERT = "1fe6a20054bcef865eeb0991ee84525b";  //Push推送业务的secret

    interface ARouterUrl{
        String SPLASH_ACTIVITY = "/foundation/splash";
        String MAIN_ACTIVITY = "/foundation/main";
        String LOGIN_ACTIVITY = "/foundation/login";
        String SHOW_PIC = "/foundation/showpic";
        String TASK_SETTING_ACTIVITY = "/foundation/tasksettting";
        String SET_ACCOUNT_ACTIVITY = "/foundation/setaccount";
        String BIND_PHONE_ACTIVITY = "/foundation/bindphone";
        String RESET_ACTIVITY = "/foundation/reset";
        String SETIP_ACTIVITY = "/foundation/setip";
        String SET_COMPANY_URL_ACTIVITY = "/foundation/set_company_url";
        String BIND_COMPANY_ACTIVITY = "/foundation/bind_company";
    }
    String KEY_PIC_BYTES_TAKE_PHOTO = "bytes_take_photo";
}
