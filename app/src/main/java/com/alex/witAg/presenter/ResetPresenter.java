package com.alex.witAg.presenter;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.IResetView;
import com.alex.witAg.utils.CleanUtil;
import com.alex.witAg.utils.ShareUtil;

/**
 * Created by Administrator on 2018-04-11.
 */

public class ResetPresenter extends BasePresenter<IResetView> {
    //恢复出厂设置
    public void resetSetting() {
        CleanUtil.resetSetting();
        getView().finishActivity();
        getView().toSplashActivity();
    }
}
