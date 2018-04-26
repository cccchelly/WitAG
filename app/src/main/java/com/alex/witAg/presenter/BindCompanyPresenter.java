package com.alex.witAg.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BindComRequestBean;
import com.alex.witAg.bean.BindComResponseBean;
import com.alex.witAg.bean.GetTokenBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IBindCompanyView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class BindCompanyPresenter extends BasePresenter<IBindCompanyView> {
    public void bind(String accountName, String address, String maintain, String name, String tel, String pass) {
        if (TextUtils.isEmpty(accountName)||TextUtils.isEmpty(address)||TextUtils.isEmpty(maintain)||TextUtils.isEmpty(name)||TextUtils.isEmpty(tel)
                ||TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请输入完整信息");
        }else {
            BindComRequestBean requestBean = new BindComRequestBean();
            requestBean.setImei(AppMsgUtil.getIMEI(App.getAppContext()));
            requestBean.setAccountName(accountName);
            requestBean.setAddress(address);
            requestBean.setDeviceId(0);
            requestBean.setMaintain(maintain);
            requestBean.setName(name);
            requestBean.setPhone(tel);
            requestBean.setPassword(pass);
            /*requestBean.setLatitude("1111111");
            requestBean.setLongitude("2222222");
            requestBean.setPhotoInterval(1);
            requestBean.setPhotoMark(1);
            requestBean.setPhotoQuality(1);
            requestBean.setPhotoStart("12:00");*/
            mView.showLoadingView("加载中...");
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .bindCompany(requestBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<BindComResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<BindComResponseBean> response) {
                            Log.i("==bindcompany==",response.toString());
                            int code = response.getCode();
                            if (code==0){
                                ShareUtil.saveAndroidPass(pass);
                                getToken();
                                //绑定  --  成功
                                ShareUtil.saveCompanyUser(accountName);
                            }else {
                                ToastUtils.showToast("绑定公司信息错误："+response.getMsg());
                            }
                        }
                    });
        }
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
                            getView().toSplash();
                        }else if (response.getCode()>0){
                            ToastUtils.showToast("获取token错误："+response.getMsg());
                        }
                    }
                });
    }
}
