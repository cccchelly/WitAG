package com.alex.witAg.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-04-10.
 */

public class CaptureInfoStrUtil {
    static CaptureInfoStrUtil captureInfoStrUtil = null;

    private CaptureInfoStrUtil(){};

    public static CaptureInfoStrUtil getInstance(){
        if (captureInfoStrUtil==null){
            return  new CaptureInfoStrUtil();
        }else {
            return captureInfoStrUtil;
        }
    }

    public  Map<String,String> getBackMapInfo(String info){  //将返回的信息串拆分为map形式
        Map<String,String> map = new HashMap();
        if (TextUtils.isEmpty(info)||!info.startsWith("<")||!info.endsWith(">")){
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
    * flag    1 摄像头电源开启/关闭状态    2绿板高度状态    3错误
    **/
    public  String getCapValue(String backInfo,int flag ){  //返回值value
        String info = "";
        for (Map.Entry<String, String> entry :getBackMapInfo(backInfo) .entrySet()) {
            switch (flag){
                case 1:
                    if (TextUtils.equals(entry.getKey(),"CAMsta")){
                        info = entry.getValue();
                    }
                    break;
                case 2:
                    if (TextUtils.equals(entry.getKey(),"HIGHsta")){
                        info = entry.getValue();
                    }
                    break;
                case 3:
                    if (TextUtils.equals(entry.getKey(),"error")){
                        info = entry.getValue();
                    }
                    break;
            }
        }
        return info;
    }

}
