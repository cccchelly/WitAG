package com.alex.witAg.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.PhotoSetResponseBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.ITaskSettingView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TaskServiceUtil;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.utils.ToastUtils;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-08.
 */

public class TaskSettingPresenter extends BasePresenter<ITaskSettingView> {
    public void setTask(Date date,String time,boolean isLogo,int quality){
        int logoFlag = 0;
        if (isLogo){
            logoFlag = -1;
        }else {
            logoFlag = 1;
        }
        if (TextUtils.isEmpty(time)){
            ToastUtils.showToast("请输入间隔时间");
        }else if (date==null){
            ToastUtils.showToast("请选择执行时间");
        }else/* if (TimeUtils.date2Millis(date)<=System.currentTimeMillis()){
            ToastUtils.showToast("所选时间已过期");
        }else*/ {
            mView.showLoadingView("加载中...");
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .setPhotoTask(ShareUtil.getToken(),TimeUtils.date2Millis(date)+"",Integer.parseInt(time),logoFlag,quality)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<PhotoSetResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<PhotoSetResponseBean> response) {
                            Log.i("==settingPhotoTask==",response.toString());
                            if (response.getCode()==BaseResponse.RESULT_CODE_SUCCESS){
                                ShareUtil.saveTaskTime((int) (Double.parseDouble(time)*60*60*1000));
                                ShareUtil.saveStartTaskTime(TimeUtils.date2Millis(date));
                                ShareUtil.saveCaptureQuality(quality);
                                TaskServiceUtil.resetTasks();
                                ToastUtils.showToast("设置成功");
                                getView().finishActivity();
                            }
                        }
                    });

        }
    }
}
