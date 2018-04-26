package com.alex.witAg.http.network;

import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.BuildConfig;
import com.alex.witAg.utils.ShareUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class Net {

    private static Net mNet;
    private static Retrofit mRetrofit;
    public static  String URL_KIND_COMPANY = "company";
    public static  String URL_KIND_BASE = "base";

    private Net(String url) {
        OkHttpClient okHttpClient = provideOkHttpClient(new CustomInterceptor());
        mRetrofit = provideRetrofit(okHttpClient,url);
    }

    public static Net getInstence(String url) {
        if (mNet == null) {
            synchronized (Net.class) {
                if (mNet == null) {
                    mNet = new Net(url);
                }
            }
        }
        return mNet;
    }

    public IApi create() {

        return mRetrofit.create(IApi.class);
    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient, String url) {

        String finalUrl ;
      /*  if (isRootUrl){
            url = AppContants.API_BASE_URL;
        }else {
            url = ShareUtil.getCompanyBaseUrl();
        }*/
        finalUrl = AppContants.API_BASE_URL;
        return new Retrofit.Builder()
                .baseUrl(finalUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient provideOkHttpClient(CustomInterceptor customInterceptor) {
        // TODO: 17-8-30
        // 1. 基本授权
        // 2. 基础Url设置
        // 3. 重试方案 For https://github.com/airbnb/okreplay library, recording and replaying server responses.
        // 4. ...
        // 5. 添加 log 打印

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(AppContants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(AppContants.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(AppContants.READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(customInterceptor)
                .addInterceptor((new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG || Log.isLoggable("OkHttp", Log.VERBOSE) ?
                                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)));
        return builder.build();
    }
}
