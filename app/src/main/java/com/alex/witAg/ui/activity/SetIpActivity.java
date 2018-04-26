package com.alex.witAg.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.SetIpPresenter;
import com.alex.witAg.presenter.viewImpl.ISetIpView;
import com.alex.witAg.utils.ShareUtil;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.umeng.analytics.MobclickAgent;

@Route(path = AppContants.ARouterUrl.SETIP_ACTIVITY)
public class SetIpActivity extends BaseActivity<SetIpPresenter,ISetIpView> implements ISetIpView {


    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        EditText edtIp = (EditText)findViewById(R.id.set_ip_edt_ip);
        EditText edtPort = (EditText)findViewById(R.id.set_ip_edt_port);
        EditText edtUser = (EditText)findViewById(R.id.set_ip_edt_username);
        EditText edtPass = (EditText)findViewById(R.id.set_ip_edt_pass);
        TextView tvSure = (TextView)findViewById(R.id.set_ip_tv_sure);
        TextView tvCancle = (TextView)findViewById(R.id.set_ip_tv_cancle);
        edtIp.setText(ShareUtil.getIp());
        edtPort.setText(ShareUtil.getPort()+"");
        edtUser.setText(ShareUtil.getUser());
        edtPass.setText(ShareUtil.getPass());
        tvSure.setOnClickListener(v -> {
            String ip = edtIp.getText().toString();
            String port = edtPort.getText().toString();
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();
            getPresenter().setIp(ip,port,user,pass);
        });
        tvCancle.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_set_ip;
    }

    @Override
    protected SetIpPresenter initPresenter() {
        return new SetIpPresenter();
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

}
