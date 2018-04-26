package com.alex.witAg.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.witAg.App;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dth
 * Des: 一些公共的工具方法存放处
 * Date: 2017/10/17.
 */

public class CommonUtils {

    /**
     * 校验手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     *
     * @param context
     * @param value
     * @return
     */
    public static boolean checkUserName(Context context,String value){
        if (TextUtils.isEmpty(value)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 校验邮箱
     * @param context
     * @param value
     * @return
     */
    public static boolean checkMail(Context context,String value){
        if (TextUtils.isEmpty(value)){
            return false;
        }
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 校验身份证
     * @param context
     * @param value
     * @return
     */
    public static boolean checkIdCard(Context context,String value){
        if (TextUtils.isEmpty(value)){
            return false;
        }
        Pattern pattern = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取手机UserAgent Okhttp走的并不是原生的http请求 携带的是 okhttp/版本号
     * @return
     */
    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(App.getAppContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

        sb.append("@shopApp");//服务器要求单独加的标识
        return sb.toString();
    }

    /**
     *
     * @param str 总文字
     * @param targetStr 目标文字
     * @param color  颜色
     * @param tv view
     */
    public static void setTwoTextColor(String str,String targetStr,int color,TextView tv){
        try {
            int fstart = str.indexOf(targetStr);
            int fend = fstart + targetStr.length();
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(color), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tv.setText(style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTwoTextSize(String str,String targetStr,int size,TextView tv){
        int fstart = str.indexOf(targetStr);
        int fend = fstart + targetStr.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new AbsoluteSizeSpan(size,true), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 显示软键盘
        imm.showSoftInput(view,0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager immHide = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 隐藏软键盘
        immHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitmap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    /**
     * 获取字符串的 MD5
     */
    public static String md5Encode(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte messageDigest[] = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件的 MD5
     */
    public static String md5Encode(File file) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream inputStream = new FileInputStream(file);
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
            //必须把文件读取完毕才能拿到md5
            byte[] buffer = new byte[4096];
            while (digestInputStream.read(buffer) > -1) {
            }
            MessageDigest digest = digestInputStream.getMessageDigest();
            digestInputStream.close();
            byte[] md5 = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : md5) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取versionCode
     * @param context
     * @return
     */
    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static void openQQStranger(Context context, String qq) {
        if (TextUtils.isEmpty(qq)) {
            return;
        }
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qq;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(App.getAppContext(), "启动QQ失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过路由跳转本地链接
     * @param url
     */
    public static void startNativeActivity(String url) {
        ////AppUrl=app://host/foundation/goodsDetail?goodsId=16
        if (!TextUtils.isEmpty(url) && url.startsWith("app")) {
            Uri uri = Uri.parse(url);

            String path = uri.getPath();//   /foundation/goodsDetail
            String host = uri.getHost();
            Logger.d("path: " + path + " host: " + host);
            Set<String> parameterNames = uri.getQueryParameterNames();

            Postcard build = ARouter.getInstance().build(path);

            for (String parameterName : parameterNames) {
                build.withString(parameterName, uri.getQueryParameter(parameterName));
                Logger.d("path: " + path + "\n host: " + host + "\n   parameterName: " + parameterName + "  value: " + uri.getQueryParameter(parameterName));
            }
            build.navigation();
        }
    }
}
