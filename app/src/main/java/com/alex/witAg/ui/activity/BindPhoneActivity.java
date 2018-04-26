package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.BindPhonePresenter;
import com.alex.witAg.presenter.viewImpl.IBindPhoneView;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = AppContants.ARouterUrl.BIND_PHONE_ACTIVITY)
public class BindPhoneActivity extends BaseActivity<BindPhonePresenter, IBindPhoneView> implements IBindPhoneView {

    @BindView(R.id.bind_phone_edt_input_phone)
    EditText mEdtInputPhone;
    @BindView(R.id.bind_phone_edt_input_code)
    EditText mEdtInputCode;
    @BindView(R.id.bind_phone_tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.bind_phone_tv_sure)
    TextView mTvSure;
    @BindView(R.id.bind_phone_tv_cancle)
    TextView mTvCancle;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected BindPhonePresenter initPresenter() {
        return new BindPhonePresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void finishActivity() {
        onBackPressed();
    }

    @Override
    public void sendClose() {
        mTvSendCode.setClickable(false);
    }

    @Override
    public void sendOpen() {
        mTvSendCode.setClickable(true);
    }

    @Override
    public void setSendCodeText(String text) {
        mTvSendCode.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bind_phone_tv_send_code, R.id.bind_phone_tv_sure, R.id.bind_phone_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_phone_tv_send_code:
                getPresenter().sendCode(mEdtInputPhone.getText().toString());
                break;
            case R.id.bind_phone_tv_sure:
                String phone = mEdtInputPhone.getText().toString();
                String code = mEdtInputCode.getText().toString();
                getPresenter().bindPhone(phone, code);
                break;
            case R.id.bind_phone_tv_cancle:
                onBackPressed();
                break;
        }
    }

}
