package com.alex.witAg.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.text.format.Time;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;

import java.text.SimpleDateFormat;
import java.util.TreeMap;

import bolts.Task;

/**
 * Created by Administrator on 2018-03-28.
 */

public class ShareUtil {
    private static final String SHARE_NAME = "app_share_name";
    private static final String COMPANY_BASE_URL = "company_base_url";
    private static final String TOKEN = "user_token";
    private static final String BindCompany = "bind_company";

    private static final String LOGIN_ID ="login_id";
    private static final String CHANNEL ="channel";
    private static  final String TIME_TASK = "time_task";  //定时任务执行时间
    private static  final String START_TIME_TASK = "start_time_task";  //定时任务开始时间
    private static  final String CAPTURE_QUALITY = "capture_quality";  //拍照质量

    private static  final String STR_IP = "device_ip";//IP地址
    private static  final String PORT = "device_port";//端口号
    private static  final String USER = "device_username";//用户名
    private static  final String PASSWORD = "device_password";//密码

    private static final String ANDROID_PASS = "android_password"; //设备密码

    private static final String ANDROID_PASS_CHECK_TIME = "android_password_check_time"; //平板密码校验时间
    private static final String CaptureCamSta = "CaptureCamSta"; //相机状态
    private static final String CaptureHignSta = "CaptureHignSta"; //板子高度
    private static final String CaptureErrorSta = "CaptureErrorSta"; //相机错误码
    private static final String DeviceStatue = "DeviceStatue"; //设备状态
    private static final String DeviceError = "DeviceError"; //设备错误码
    private static final String DeviceBatvol = "DeviceBatvol"; //电池电压
    private static final String DeviceSunvol = "DeviceSunvol"; //太阳能电压

    public static SharedPreferences getShare(){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    // 相机状态
    public static void saveCaptureCamSta(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(CaptureCamSta,sta);
        editor.commit();
    }
    public static String getCaptureCamSta(){
        String sta = getShare().getString(CaptureCamSta,"0");
        return sta;
    }

    // 板子高度
    public static void saveCaptureHignSta(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(CaptureHignSta,sta);
        editor.commit();
    }
    public static String getCaptureHignSta(){
        String sta = getShare().getString(CaptureHignSta,"1");
        return sta;
    }

    // 相机错误码
    public static void saveCaptureErrorSta(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(CaptureErrorSta,sta);
        editor.commit();
    }
    public static String getCaptureErrorSta(){
        String sta = getShare().getString(CaptureErrorSta,"0");
        return sta;
    }

    // 设备状态
    public static void saveDeviceStatue(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(DeviceStatue,sta);
        editor.commit();
    }
    public static String getDeviceStatue(){
        String sta = getShare().getString(DeviceStatue,"0");
        return sta;
    }

    // 设备错误码
    public static void saveDeviceError(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(DeviceError,sta);
        editor.commit();
    }
    public static String getDeviceError(){
        String sta = getShare().getString(DeviceError,"0");
        return sta;
    }

    // 电池电压
    public static void saveDeviceBatvol(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(DeviceBatvol,sta);
        editor.commit();
    }
    public static String getDeviceBatvol(){
        String sta = getShare().getString(DeviceBatvol,"0");
        return sta;
    }

    // 太阳能电压
    public static void saveDeviceSunvol(String sta){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(DeviceSunvol,sta);
        editor.commit();
    }
    public static String getDeviceSunvol(){
        String sta = getShare().getString(DeviceSunvol,"0");
        return sta;
    }


    // 企业baseurl
    public static void saveCompanyBaseUrl(String url){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(COMPANY_BASE_URL,url);
        editor.commit();
    }
    public static String getCompanyBaseUrl(){
        String url = getShare().getString(COMPANY_BASE_URL,"-1");
        return TextUtils.equals(url,"-1")? AppContants.API_BASE_URL:url;
    }
    public static boolean isCompanyBaseUrlSetting(){
        String url = getShare().getString(COMPANY_BASE_URL,"-1");
        return TextUtils.equals(url,"-1")? false:true;
    }

    // 绑定公司账号
    public static void saveCompanyUser(String user){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(BindCompany,user);
        editor.commit();
    }
    public static String getCompanyUser(){
        String url = getShare().getString(BindCompany,"-1");
        return TextUtils.equals(url,"-1")? AppContants.API_BASE_URL:url;
    }
    public static boolean isCompanyUserBind(){
        String url = getShare().getString(BindCompany,"-1");
        return TextUtils.equals(url,"-1")? false:true;
    }
    public static void cleanBindComMsg(){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(BindCompany,"-1");
        editor.commit();
    }

    // token
    public static void saveToken(String token){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }
    public static String getToken(){
        String token = getShare().getString(TOKEN,"");
        return token;
    }
    public static boolean hasToken(){
        String token = getShare().getString(TOKEN,"");
        return TextUtils.equals(token,"")?false:true;
    }
    public static void cleanToken(){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(TOKEN,"");
        editor.commit();
    }

    //设备loginId
    public static void saveLoginId(int loginId){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putInt(LOGIN_ID,loginId);
        editor.commit();
    }
    public static int getLoginId(){
       return getShare().getInt(LOGIN_ID,-1);
    }

    //      设备通道号
    public static void saveChannel(int channel){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putInt(CHANNEL,channel);
        editor.commit();
    }
    public static int getChannel(){
        return getShare().getInt(CHANNEL,-1);
    }

    // 定时任务时间间隔
    public static void saveTaskTime(int time){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putInt(TIME_TASK,time);
        editor.commit();
    }
    public static int getTaskTime(){
        int time = getShare().getInt(TIME_TASK,-1);
        return time==-1? AppContants.TASK_DEFAULT_TIME:time;
    }

    // 定时任务开始时间
    public static void saveStartTaskTime(long time){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putLong(START_TIME_TASK,time);
        editor.commit();
    }
    public static String getStartTaskTime(){
        long time = getShare().getLong(START_TIME_TASK,-1);
        return time==-1? AppContants.START_TASK_DEFAULT_TIME:TimeUtils.millis2String(time,new SimpleDateFormat("HH:mm"));
    }

    //拍照质量
    public static void saveCaptureQuality(int quality){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putInt(CAPTURE_QUALITY,quality);
        editor.commit();
    }
    public static int getCaptureQuality(){
        int quality = getShare().getInt(CAPTURE_QUALITY,0);
        return quality;
    }

    // IP地址
    public static void saveIp(String ip){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(STR_IP,ip);
        editor.commit();
    }
    public static String getIp(){
        String ip = getShare().getString(STR_IP,"-1");
        return TextUtils.equals(ip,"-1")? AppContants.strIP:ip;
    }

    // 端口号
    public static void savePort(int port){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putInt(PORT,port);
        editor.commit();
    }
    public static int getPort(){
        int port = getShare().getInt(PORT,-1);
        return port==-1? AppContants.nPort:port;
    }

    // 账户名
    public static void saveUser(String user){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(USER,user);
        editor.commit();
    }
    public static String getUser(){
        String user = getShare().getString(USER,"-1");
        return TextUtils.equals(user,"-1")? AppContants.strUser:user;
    }

    // 密码
    public static void savePass(String pass){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(PASSWORD,pass);
        editor.commit();
    }
    public static String getPass(){
        String pass = getShare().getString(PASSWORD,"-1");
        return TextUtils.equals(pass,"-1")? AppContants.strPsd:pass;
    }

    // 平板密码(有无绑定基础设置依据)
    public static void saveAndroidPass(String pass){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(ANDROID_PASS,pass);
        editor.commit();
    }
    public static String getAndroidPass(){
        String pass = getShare().getString(ANDROID_PASS,"-1");
        return TextUtils.equals(pass,"-1")? AppContants.strIP:pass;
    }
    public static void cleanAndroidPass(){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putString(ANDROID_PASS,"-1");
        editor.commit();
    }
    public static boolean isSetAndroidPass(){
        String pass = getShare().getString(ANDROID_PASS,"-1");
        return TextUtils.equals(pass,"-1")? false:true;
    }

    //密码校验时间
    public static void savePassCheckTime(long time){
        SharedPreferences.Editor editor = getShare().edit();
        editor.putLong(ANDROID_PASS_CHECK_TIME,time);
        editor.commit();
    }

    public static boolean getIsPassChecked(){
        long time = getShare().getLong(ANDROID_PASS_CHECK_TIME,-1);
        long currentTime = System.currentTimeMillis();
        if (currentTime-time<=AppContants.PASS_CHECK_DEFAULT_TIME){
            return true;
        }else {
            return false;
        }
    }

    //是否登录
    public static boolean isLogin(){
        return getLoginId()==-1?false:true;
    }


}
