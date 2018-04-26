package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.BindCompanyPresenter;
import com.alex.witAg.presenter.viewImpl.IBindCompanyView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = AppContants.ARouterUrl.BIND_COMPANY_ACTIVITY)
public class BindCompanyActivity extends BaseActivity<BindCompanyPresenter, IBindCompanyView> implements IBindCompanyView {

    @BindView(R.id.bind_company_url_edt_username)
    EditText mEdtUsername;
    @BindView(R.id.bind_company_url_tv_sure)
    TextView mTvSure;
    @BindView(R.id.bind_company_url_tv_cancle)
    TextView mTvCancle;
    @BindView(R.id.bind_company_url_edt_address)
    EditText mEdtAddress;
    @BindView(R.id.bind_company_url_edt_main_tain)
    EditText mEdtMainTain;
    @BindView(R.id.bind_company_url_edt_name)
    EditText mEdtName;
    @BindView(R.id.bind_company_url_edt_tel)
    EditText mEdtTel;
    @BindView(R.id.bind_company_url_edt_password)
    EditText mEdtPassword;


    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_bind_company;
    }

    @Override
    protected BindCompanyPresenter initPresenter() {
        return new BindCompanyPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bind_company_url_tv_sure, R.id.bind_company_url_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_company_url_tv_sure:
                String accountName = mEdtUsername.getText().toString();
                String address = mEdtAddress.getText().toString();
                String maintain = mEdtMainTain.getText().toString();
                String name = mEdtName.getText().toString();
                String tel = mEdtTel.getText().toString();
                String pass =  mEdtPassword.getText().toString();
                getPresenter().bind(accountName,address,maintain,name,tel,pass);
                break;
            case R.id.bind_company_url_tv_cancle:
                finish();
                break;
        }
    }

    @Override
    public void toSplash() {
        ARouter.getInstance().build(AppContants.ARouterUrl.SPLASH_ACTIVITY)
                .navigation();
    }

}
