package com.alex.witAg.presenter;

import android.text.TextUtils;

import com.alex.witAg.App;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.ISetIpView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

/**
 * Created by Administrator on 2018-04-11.
 */

public class SetIpPresenter extends BasePresenter<ISetIpView> {
    //设置登录摄像头参数
    public void setIp(String ip, String port, String user, String pass){
        if(TextUtils.isEmpty(ip)||TextUtils.isEmpty(port)||TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请填写完整的信息");
        }else {
            ShareUtil.saveIp(ip);
            ShareUtil.savePort(Integer.parseInt(port));
            ShareUtil.saveUser(user);
            ShareUtil.savePass(pass);
            ToastUtils.showToast("修改成功");
            getView().finishActivity();
            App.setIsNeedReLogin(true);
        }
    }
}
