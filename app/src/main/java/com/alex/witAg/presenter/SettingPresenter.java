package com.alex.witAg.presenter;

import android.text.TextUtils;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.ISettingView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SettingPresenter extends BasePresenter<ISettingView>{


    //校验密码
    public void checkPass(String pass){
        if (TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请输入密码");
        }else {
            if (TextUtils.equals(pass,ShareUtil.getAndroidPass())||TextUtils.equals(pass,"android")){ //密码与本地密码一致或者与默认密码一致
                getView().hideDialogCheckPass();
                getView().toast("密码校验成功");
                ShareUtil.savePassCheckTime(System.currentTimeMillis());
            }else { //网络校验密码
               /* //校验成功
                getView().hideDialogCheckPass();
                getView().toast("密码校验成功");
                ShareUtil.savePassCheckTime(System.currentTimeMillis());
                //不匹配
                getView().toast("密码错误");
                getView().hideDialogCheckPass();*/
               getView().toast("密码错误");
               getView().hideDialogCheckPass();
            }

        }
    }



}
