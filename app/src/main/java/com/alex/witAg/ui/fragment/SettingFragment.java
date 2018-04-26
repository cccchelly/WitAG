package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.SettingPresenter;
import com.alex.witAg.presenter.viewImpl.ISettingView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SettingFragment extends BaseFragment<SettingPresenter, ISettingView> implements ISettingView {
    @BindView(R.id.tv_account_modify)
    TextView tvSetAccount;
    @BindView(R.id.tv_ip_select)
    TextView tvSetIp;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_project_setting)
    TextView tvPojectSetting;
    @BindView(R.id.tv_phone_bind)
    TextView tvPhoneBind;
    @BindView(R.id.tv_photo_select)
    TextView tvPhotoSetting;
    DialogPlus dialogCheckPass;

    @Override
    protected void fetchData() {
    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        initDialogCheckPass();
    }
    //校验密码
    private void initDialogCheckPass() {
        dialogCheckPass = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.layout_check_pass))
                .setGravity(Gravity.CENTER)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        View view = dialogCheckPass.getHolderView();
        EditText edtPass = (EditText) view.findViewById(R.id.check_pass_edt_pass);
        TextView tvSure = (TextView) view.findViewById(R.id.check_pass_tv_sure);
        TextView tvCancle = (TextView) view.findViewById(R.id.check_pass_tv_cancle);
        tvSure.setOnClickListener(v -> {
            String pass = edtPass.getText().toString();
            getPresenter().checkPass(pass);
        });
        tvCancle.setOnClickListener(v -> hideDialogCheckPass());
    }


    @OnClick({R.id.tv_account_modify, R.id.tv_project_setting,R.id.tv_ip_select,R.id.tv_reset,R.id.tv_phone_bind,R.id.tv_photo_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_modify:  //账户修改（平板密码）
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.SET_ACCOUNT_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_project_setting:
                if (isCheckPass()){
                    ARouter.getInstance().build(AppContants.ARouterUrl.SET_COMPANY_URL_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_ip_select:
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.SETIP_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_reset:
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.RESET_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_phone_bind:
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.BIND_PHONE_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_photo_select: //拍照选择
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.TASK_SETTING_ACTIVITY)
                            .navigation();
                }
                break;
        }
    }

    private boolean isCheckPass(){
        if (ShareUtil.getIsPassChecked()){
            return true;
        }else {
            showCheckPass();
            return false;
        }
    }

    private void showCheckPass() {
        toast("请先输入设备密码");
        dialogCheckPass.show();
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void hideDialogCheckPass() {
        dialogCheckPass.dismiss();
    }

}
