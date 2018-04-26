package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.SetCompanyUrlPresenter;
import com.alex.witAg.presenter.viewImpl.ISetComUrlView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = AppContants.ARouterUrl.SET_COMPANY_URL_ACTIVITY)
public class SetCompanyUrlActivity extends BaseActivity<SetCompanyUrlPresenter, ISetComUrlView> implements ISetComUrlView {

    @BindView(R.id.set_company_url_edt_pass)
    EditText mEdtPass;
    @BindView(R.id.set_company_url_tv_sure)
    TextView mTvSure;
    @BindView(R.id.set_company_url_tv_cancle)
    TextView mTvCancle;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mEdtPass.setText(AppContants.API_BASE_URL);
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_set_company_url;
    }

    @Override
    protected SetCompanyUrlPresenter initPresenter() {
        return new SetCompanyUrlPresenter();
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

    @OnClick({R.id.set_company_url_tv_sure, R.id.set_company_url_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_company_url_tv_sure:
                getPresenter().setUrl(mEdtPass.getText().toString());
                break;
            case R.id.set_company_url_tv_cancle:
                finish();
                break;
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void toBindCompany() {
        ARouter.getInstance().build(AppContants.ARouterUrl.BIND_COMPANY_ACTIVITY)
                .navigation();
        finish();
    }

    @Override
    public void toSplash() {
        ARouter.getInstance().build(AppContants.ARouterUrl.SPLASH_ACTIVITY)
                .navigation();
    }

}
