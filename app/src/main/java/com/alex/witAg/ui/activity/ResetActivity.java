package com.alex.witAg.ui.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.ResetPresenter;
import com.alex.witAg.presenter.viewImpl.IResetView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.umeng.analytics.MobclickAgent;

@Route(path = AppContants.ARouterUrl.RESET_ACTIVITY)
public class ResetActivity extends BaseActivity<ResetPresenter,IResetView> implements IResetView {

    DialogPlus dialogPlus;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initView();
        initDialog();
    }

    private void initDialog() {
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHolder(new ViewHolder(R.layout.reset_dialog_sure))
                .setGravity(Gravity.CENTER)
                .create();
        View view = dialogPlus.getHolderView();
        TextView tvSure = (TextView) view.findViewById(R.id.set_reset_secend_tv_sure);
        TextView tvCancle = (TextView) view.findViewById(R.id.set_reset_secend_tv_cancle);
        tvSure.setOnClickListener(v -> {
            getPresenter().resetSetting();
            dialogPlus.dismiss();
        });
        tvCancle.setOnClickListener(v -> {
            dialogPlus.dismiss();
        });
    }

    private void initView() {
        TextView tvSure = (TextView) findViewById(R.id.set_reset_tv_sure);
        TextView tvCancle = (TextView) findViewById(R.id.set_reset_tv_cancle);
        tvSure.setOnClickListener(v -> {
            dialogPlus.show();
        });
        tvCancle.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_reset;
    }

    @Override
    protected ResetPresenter initPresenter() {
        return new ResetPresenter();
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
    public void toSplashActivity() {
        ARouter.getInstance().build(AppContants.ARouterUrl.SPLASH_ACTIVITY)
                .navigation();
    }

}
