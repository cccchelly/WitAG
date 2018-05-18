package com.alex.witAg.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.base.BaseResponseObserver;
import com.alex.witAg.bean.PicMessageBean;
import com.alex.witAg.bean.PicPathsBean;
import com.alex.witAg.bean.QiNiuTokenBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/31.
 */
public class CapturePostUtil {

    public static void findLocalPic(){
        List<PicPathsBean> picPaths = DataSupport.findAll(PicPathsBean.class);
        Log.i(TAG,"图片数="+picPaths.size());
        for (PicPathsBean picpath:picPaths) {
            try{
                File file = FileUtils.getFileFromSdcard(picpath.getPath());
                postPic(file,picpath.getPath());
            }catch (NullPointerException e){
                //未找到图片（如人为删除了图片），从数据库清除图片地址
                //数据库删除文件名
                DataSupport.deleteAll(PicPathsBean.class,"path = ?",picpath.getPath());
            }

        }
    }
    private static String TAG =CapturePostUtil.class.getName();
    //七牛初始化
    static Configuration config =
            new Configuration.Builder()
                    /*.chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                    .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                    .connectTimeout(10)           // 链接超时。默认10秒
                    .useHttps(true)               // 是否使用https上传域名
                    .responseTimeout(60)          // 服务器响应超时。默认60秒
                    .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
                    .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录*/
                    .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                    .build();

    static UploadManager uploadManager = new UploadManager(config);
    // 初始化、执行上传
    private volatile boolean isCancelled = false;    //要取消上传时置为true

    public static void postPic(File file, String picName){
        //getView().showLoadingView("图片上传中...");

        AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                .getQiNiuToken(ShareUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<QiNiuTokenBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<QiNiuTokenBean> response) {
                        QiNiuTokenBean qiNiuTokenBean = response.getData();
                        Log.i(TAG,"获取七牛token："+qiNiuTokenBean.toString());
                        postToQiNiu(file,picName,qiNiuTokenBean.getToken());
                    }
                });
    }

    static void postToQiNiu(File data, String name, String token){
        uploadManager.put(data, name, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Log.i(TAG, "Upload Success");
                            PicMessageBean messageBean = new PicMessageBean();
                            //messageBean.setDeviceId(ShareUtil.getLoginId());
                            messageBean.setName(key.toString());
                            messageBean.setUrl(key.toString());
                            postPic(messageBean,name);
                            //getView().dissmissLoadingView();
                        } else {
                            Log.i(TAG, "Upload Fail");
                            //getView().dissmissLoadingView();
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i(TAG, key + ",\r\n " + info + ",\r\n " + res);
                    }
                },new UploadOptions(null, null, false,
                        new UpProgressHandler(){
                            public void progress(String key, double percent){
                                Log.i(TAG, key + ": " + percent);
                            }
                        }, null));
    }

   public static void postPic(PicMessageBean messageBean, String picName){
        AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                .postDevicePic(messageBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseResponseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody response) {
                        ToastUtils.showToast("图片:" + picName + "上传成功");
                        try {
                            //数据库删除文件名   删除文件
                            DataSupport.deleteAll(PicPathsBean.class, "path = ?", picName);
                            FileUtils.deleteFile(FileUtils.getFileFromSdcard(picName).getAbsolutePath());
                            Log.i("==fileName==",picName);
                            //Log.i("==fileAbsName==",FileUtils.getFileFromSdcard(picName).getAbsolutePath());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

    }

}
