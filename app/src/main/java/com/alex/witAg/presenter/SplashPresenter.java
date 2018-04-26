package com.alex.witAg.presenter;

import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.GetTokenBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.ISplashView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SplashPresenter extends BasePresenter<ISplashView>{

    /**
     * 自动登录
     */
    public void autoLogin() {
        Log.i("==token==","token = "+ShareUtil.getToken());
        if (ShareUtil.hasToken()) {
            //弱化基础设置
           /* if (ShareUtil.isSetAndroidPass()) {   //完成基础设置
                getView().enterMain();
            }else {
                getView().toSetAccount();
            }*/
            getView().enterMain();
        }else {
            getToken();
        }
        /*addDisposable(Observable.just(true)
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> countDown())
                .subscribe(isLogin -> {
                    if (isLogin) {
                        getView().enterMain();
                    } else {
                        getView().enterLogin();
                    }
                }));*/

    }


    public void getToken(){
        //获取token
        AppDataManager.getInstence(Net.URL_KIND_BASE)
                .getToken(AppMsgUtil.getIMEI(App.getAppContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<GetTokenBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<GetTokenBean> response) {
                        Log.i("==gettoken==",response.toString());
                        if (response.getCode()==BaseResponse.RESULT_CODE_SUCCESS){
                            //得到token
                            ShareUtil.saveToken(response.getData().getToken());
                            getView().enterMain();
                        }else if (response.getCode()>0){
                            getView().setUrl();
                            //ToastUtils.showToast("获取token错误："+response.getMsg());
                        }
                    }
                });
    }


    /**
     * 登录倒计时
     */
    private void countDown() {
        addDisposable(Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                .map(count -> (3 - count))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> getView().timeCountDown(count)));
    }
}
