package com.alex.witAg.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-27.
 */

public class SerialInforStrUtil {
    public static final String STA_OPEN_POSITIVE = "1";  //相机打开并翻转正面
    public static final String STA_OPEN_OPPOSITE = "2";  //相机打开并翻转反面
    public static final String STA_CLOSE_RESET = "0";    //相机关闭并复位

  /*   public static String getResetStaStr(){
        return  "{sta:0}";
    }    //复位状态匹配
    public static String getRiseStaStr(){
        return  "{sta:1}";
    }    //翻转到正面的状态匹配*/
    public static String getDeclineStr(){
        return  "{sta:2}";
    }      // 翻转到反面
    public static String getForceRestartStr(){
        return  "{sta:3}";
    }      //强制复位

    //高度调节到HIGHsta位置
    public static String getHighStr1(){
        return  "<CAMsta:0,HIGHsta:1>";
    }
    public static String getHighStr2(){
        return  "<CAMsta:0,HIGHsta:2>";
    }
    public static String getHighStr3(){
        return  "<CAMsta:0,HIGHsta:3>";
    }
    public static String getHighStr4(){
        return  "<CAMsta:0,HIGHsta:4>";
    }
    public static String getHighStr5(){
        return  "<CAMsta:0,HIGHsta:5>";
    }
    public static String openCamTurnPositive(){
        return  "<CAMsta:1,HIGHsta:0>";
    }  //打开摄像机并翻转到正面

   /* public static String closeCamer(){
        return  "<CAMsta:0,HIGHsta:0>";
    }*/

    public static Map<String,String> getBackMapInfo(String info){  //将返回的信息串拆分为map形式
        Map<String,String> map = new HashMap();
        if (TextUtils.isEmpty(info)||!info.startsWith("{")||!info.endsWith("}")){
            return map;
        }
        String[] strings = info.substring(1, info.length() - 1).split(",");
        for (String str:strings) {
            String[] keyVal = str.split(":");
            map.put(keyVal[0],keyVal[1]);
        }
        return map;
    }

    /*
    * flag    1 电池电压    2太阳能电压    3 状态   4 错误
    **/
    public static String getValue(String backInfo,int flag ){  //返回值value
        String info = "";
        for (Map.Entry<String, String> entry :getBackMapInfo(backInfo) .entrySet()) {
            switch (flag){
                case 1:
                    if (TextUtils.equals(entry.getKey(),"batvol")){
                        info = entry.getValue();
                    }
                    break;
                case 2:
                    if (TextUtils.equals(entry.getKey(),"sunvol")){
                        info = entry.getValue();
                    }
                    break;
                case 3:
                    if (TextUtils.equals(entry.getKey(),"sta")){
                        info = entry.getValue();
                    }
                    break;
                case 4:
                    if (TextUtils.equals(entry.getKey(),"error")){
                        info = entry.getValue();
                    }
                    break;
            }
        }
        return info;
    }

}
