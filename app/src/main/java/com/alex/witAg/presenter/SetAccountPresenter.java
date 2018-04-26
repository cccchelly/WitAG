package com.alex.witAg.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BaseSettingRequestBean;
import com.alex.witAg.bean.BaseSettingResponseBean;
import com.alex.witAg.bean.BindComRequestBean;
import com.alex.witAg.bean.BindComResponseBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IsetAccountView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class SetAccountPresenter extends BasePresenter<IsetAccountView> {
    //设置设备密码
    public void setAccount(String address, String maintain, String name, String tel, String pass) {
        if (TextUtils.isEmpty(address)||TextUtils.isEmpty(maintain)||TextUtils.isEmpty(name)||TextUtils.isEmpty(tel)
                ||TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请输入完整信息");
        }else {
            BaseSettingRequestBean requestBean = new BaseSettingRequestBean();
           // requestBean.setImei(AppMsgUtil.getIMEI(App.getAppContext()));
            //requestBean.setAccountName(accountName);
            requestBean.setAddress(address);
            //requestBean.setDeviceId(0);
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
                    .setBaseSetting(requestBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<BaseSettingResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<BaseSettingResponseBean> response) {
                            Log.i("====",response.toString());
                            ToastUtils.showToast("设置成功");
                            ShareUtil.saveAndroidPass(pass);
                            getView().finishActivity();
                        }
                    });
        }
    }
}
