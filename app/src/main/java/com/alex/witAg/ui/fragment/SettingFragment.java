package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.App;
import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.bean.PicMessageBean;
import com.alex.witAg.bean.PicPathsBean;
import com.alex.witAg.presenter.SettingPresenter;
import com.alex.witAg.presenter.viewImpl.ISettingView;
import com.alex.witAg.utils.AppUpdateUtil;
import com.alex.witAg.utils.CapturePostUtil;
import com.alex.witAg.utils.FileUtils;
import com.alex.witAg.utils.MyAnimUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TaskServiceUtil;
import com.alex.witAg.utils.ToastUtils;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.litepal.crud.DataSupport;

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
    @BindView(R.id.tv_warning_select)
            TextView tvWarning;

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


    @OnClick({R.id.tv_account_modify, R.id.tv_project_setting,R.id.tv_ip_select,R.id.tv_reset,R.id.tv_phone_bind,R.id.tv_photo_select,R.id.tv_check_update,R.id.tv_warning_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_modify:  //账户修改（平板密码）
                tvSetAccount.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.SET_ACCOUNT_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_project_setting:
                tvPojectSetting.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()){
                    ARouter.getInstance().build(AppContants.ARouterUrl.SET_COMPANY_URL_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_ip_select:
                tvSetIp.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.SETIP_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_reset:
                tvReset.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.RESET_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_phone_bind:
                tvPhoneBind.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.BIND_PHONE_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_photo_select: //拍照选择
                tvPhotoSetting.startAnimation(MyAnimUtil.alphHalf2All());
                if (isCheckPass()) {
                    ARouter.getInstance().build(AppContants.ARouterUrl.TASK_SETTING_ACTIVITY)
                            .navigation();
                }
                break;
            case R.id.tv_check_update:

                AppUpdateUtil.check(true, true, false, true, true, 998, App.getAppContext(),true);    //检查新版本

                break;
            case R.id.tv_warning_select:
                //手动上传图片暂时放这里测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CapturePostUtil.findLocalPic();
                    }
                }).start();

                /*PicMessageBean messageBean = new PicMessageBean();
                messageBean.setName("aaa.jpg");
                messageBean.setUrl("20180518111914-A.jpg");
                CapturePostUtil.postPic(messageBean,"aaa.jpg");*/
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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter){
            return AnimationUtils.loadAnimation(getActivity(),R.anim.activity_anim_in);
        }else {
            return super.onCreateAnimation(transit,enter,nextAnim);
        }
    }

}
