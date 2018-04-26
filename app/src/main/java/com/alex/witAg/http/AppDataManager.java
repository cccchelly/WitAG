package com.alex.witAg.http;

import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BaseSettingRequestBean;
import com.alex.witAg.bean.BaseSettingResponseBean;
import com.alex.witAg.bean.BindComRequestBean;
import com.alex.witAg.bean.BindComResponseBean;
import com.alex.witAg.bean.BindPhoneResponseBean;
import com.alex.witAg.bean.GetTokenBean;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.bean.PhotoDetailRecodeBean;
import com.alex.witAg.bean.PhotoSetResponseBean;
import com.alex.witAg.bean.PicListBean;
import com.alex.witAg.bean.PicMessageBean;
import com.alex.witAg.bean.PostLocationResponseBean;
import com.alex.witAg.bean.QiNiuTokenBean;
import com.alex.witAg.bean.SendSmsResponseBean;
import com.alex.witAg.bean.UpdateMsgBean;
import com.alex.witAg.http.cache.AppCache;
import com.alex.witAg.http.cache.IAcache;
import com.alex.witAg.http.network.IApi;
import com.alex.witAg.http.network.Net;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by dth
 * Des: 统一的数据管理类
 * Date: 2018-01-23.
 */

public final class AppDataManager implements IDataManager{


    private static AppDataManager mAppDataManager;
    private final  IApi           mIApi;
    private final AppCache mAppCache;

    private AppDataManager(String url) {
        mIApi = Net.getInstence(url).create();
        mAppCache = AppCache.getInstence();
    }

    public static AppDataManager getInstence(String url) {
        if (mAppDataManager == null) {
            synchronized (AppDataManager.class) {
                if (mAppDataManager == null) {
                    mAppDataManager = new AppDataManager(url);
                }
            }
        }
        return mAppDataManager;
    }

    @Override
    public IApi getApi() {
        return mIApi;
    }

    @Override
    public IAcache getAcache() {
        return mAppCache;
    }

    @Override
    public Observable<BaseResponse<HomeBean>> getHomePageData() {
        return mIApi.getHomePageData();
    }

    @Override
    public Observable<BaseResponse<UpdateMsgBean>> getVersion(String token, String versionCode) {
        return mIApi.getVersion(token,versionCode);
    }


    @Override
    public Observable<BaseResponse<GetTokenBean>> getToken(String imei) {
        return mIApi.getToken(imei);
    }

    @Override
    public Observable<BaseResponse<BindComResponseBean>> bindCompany(BindComRequestBean bindComRequestBean) {
        return mIApi.bindCompany(bindComRequestBean);
    }

    @Override
    public Observable<BaseResponse<BaseSettingResponseBean>> setBaseSetting(BaseSettingRequestBean baseSettingRequestBean) {
        return mIApi.setBaseSetting(baseSettingRequestBean);
    }

    @Override
    public Observable<BaseResponse<PhotoSetResponseBean>> setPhotoTask(String token,String photoStart, Integer photoInterval, Integer photoMark, Integer photoQuality) {
        return mIApi.setPhotoTask(token,photoStart,photoInterval,photoMark,photoQuality);
    }

    @Override
    public Observable<BaseResponse<SendSmsResponseBean>> sendSms(String token, String phone) {
        return mIApi.sendSms(token,phone);
    }

    @Override
    public Observable<BaseResponse<BindPhoneResponseBean>> bindPhone(String token, String phone, String code) {
        return mIApi.bindPhone(token,phone,code);
    }

    @Override
    public Observable<BaseResponse<PostLocationResponseBean>> postLocation(String Latitude, String Longitude) {
        return mIApi.postLocation(Latitude,Longitude);
    }

    @Override
    public Observable<BaseResponse<QiNiuTokenBean>> getQiNiuToken(String token) {
        return mIApi.getQiNiuToken(token);
    }


    @Override
    public Observable<ResponseBody> postDevicePic(PicMessageBean messageBean) {
        return mIApi.postDevicePic(messageBean);
    }

    @Override
    public Observable<BaseResponse<PicListBean>> getPicListData(String token, String date) {
        return mIApi.getPicListData(token,date);
    }

    @Override
    public Observable<BaseResponse<PhotoDetailRecodeBean>> getRecodeByPhoto(String id, String token) {
        return mIApi.getRecodeByPhoto(id,token);
    }


}
