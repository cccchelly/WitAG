package com.alex.witAg.presenter;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BindPhoneResponseBean;
import com.alex.witAg.bean.SendSmsResponseBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IBindPhoneView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class BindPhonePresenter extends BasePresenter<IBindPhoneView> {
    //发送验证码
    public void sendCode(String phone){
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast("请填写手机号码");
        }else {
            new TimeCount(60000,1000).start();
            mView.showLoadingView("发送中...");
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .sendSms(ShareUtil.getToken(),phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<SendSmsResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<SendSmsResponseBean> response) {
                            Log.i("==sendsms==",response.toString());
                            if (response.getCode()==BaseResponse.RESULT_CODE_SUCCESS){
                                ToastUtils.showToast("发送验证码成功");
                            }
                        }
                    });
        }

    }

    //绑定手机号
    public void bindPhone(String phone, String code) {
        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(code)){
            ToastUtils.showToast("请输入手机号或验证码");
        }else {
            mView.showLoadingView("绑定中...");
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .bindPhone(ShareUtil.getToken(),phone,code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<BindPhoneResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<BindPhoneResponseBean> response) {
                            Log.i("==bindphone==",response.toString());
                            if (response.getCode()==BaseResponse.RESULT_CODE_SUCCESS){
                                ToastUtils.showToast("绑定成功");
                                getView().finishActivity();
                            }
                        }
                    });
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getView().sendClose();
            getView().setSendCodeText(millisUntilFinished/1000+"S后重发");
        }

        @Override
        public void onFinish() {
            getView().sendOpen();
            getView().setSendCodeText("重新发送");
        }
    }

}
