package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.SplashPresenter;
import com.alex.witAg.presenter.viewImpl.ISplashView;
import com.alex.witAg.utils.ShareUtil;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */
@Route(path = AppContants.ARouterUrl.SPLASH_ACTIVITY)
public class SplashActivity extends BaseActivity<SplashPresenter,ISplashView> implements ISplashView {
    private static final String TAG = SplashActivity.class.getName();
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected void onResume() {
        Log.i(TAG,"==splashactivity_onresume==");
        getPresenter().autoLogin();
        //enterMain();
        super.onResume();
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void enterLogin() {
        ARouter.getInstance().build(AppContants.ARouterUrl.LOGIN_ACTIVITY)
                .navigation();

    }

    @Override
    public void enterMain() {
        ARouter.getInstance().build(AppContants.ARouterUrl.MAIN_ACTIVITY)
                .navigation();

    }

    @Override
    public void timeCountDown(long time) {
        mTvTime.setText(String.format(Locale.getDefault(),"wait %d Seconds", time));
    }
    //初始化配置
    @Override
    public void setUrl() {
        ARouter.getInstance().build(AppContants.ARouterUrl.SET_COMPANY_URL_ACTIVITY)
                .navigation();

    }

    @Override
    public void toSetAccount() {
        ARouter.getInstance().build(AppContants.ARouterUrl.SET_ACCOUNT_ACTIVITY)
                .navigation();
    }

}
