package com.alex.witAg.ui.activity;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.LoginPresenter;
import com.alex.witAg.presenter.viewImpl.ILoginView;
import com.alex.witAg.runtimepermission.PermissionsManager;
import com.alex.witAg.runtimepermission.PermissionsResultAction;
import com.alex.witAg.utils.ToastUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

@Route(path = AppContants.ARouterUrl.LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity<LoginPresenter, ILoginView> implements ILoginView {

    @BindView(R.id.username)
    EditText     mUsername;
    @BindView(R.id.password)
    EditText     mPassword;
    @BindView(R.id.tv_commit)
    TextView     mTvCommit;
    @BindView(R.id.tvRegister)
    TextView     mTvRegister;
    @BindView(R.id.tv_forget)
    TextView     mTvForget;
    @BindView(R.id.loginForm)
    LinearLayout mLoginForm;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        getPresenter().login();
        //StatusBarUtil.setTranslucentForImageView(this, Color.TRANSPARENT, null);
        //requestPermissions();
        //int startDelay = getResources().getInteger(android.R.integer.config_mediumAnimTime) + 100;
        //LayoutTransition transition = new LayoutTransition();
        //transition.enableTransitionType(LayoutTransition.CHANGING);
        //transition.setStartDelay(LayoutTransition.APPEARING, startDelay);
        //transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, startDelay);
       // mLoginForm.setLayoutTransition(transition);
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @OnClick({R.id.tv_commit, R.id.tvRegister, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                break;
            case R.id.tvRegister:
                break;
            case R.id.tv_forget:
                break;
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                //				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Logger.d("permission: "+permission);
                //                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void back() {
        ToastUtils.showToast("登录成功");
        onBackPressed();
    }

}
