package com.alex.witAg.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.TaskSettingPresenter;
import com.alex.witAg.presenter.viewImpl.ITaskSettingView;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.view.EaseSwitchButton;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.angmarch.views.NiceSpinner;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = AppContants.ARouterUrl.TASK_SETTING_ACTIVITY)
public class TaskSettingActivity extends BaseActivity<TaskSettingPresenter, ITaskSettingView> implements ITaskSettingView {

    @BindView(R.id.set_capture_tv_choose_time)
    TextView mTvChooseTime;
    @BindView(R.id.set_capture_edt_time)
    EditText mEdtTime;
    @BindView(R.id.set_capture_tv_sure)
    TextView mTvSure;
    @BindView(R.id.set_capture_tv_cancle)
    TextView mTvCancle;
    @BindView(R.id.set_capture_swt_btn_logo)
    EaseSwitchButton mSwtBtnLogo;
    @BindView(R.id.set_capture_swt_spinner)
    NiceSpinner mSwtSpinner;
    private TimePickerView timePickerView;
    private Date date;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initTimeDialog();
        initSpinner();
        initSwitchBtn();
    }

    private void initSwitchBtn() {
        mSwtBtnLogo.setOnSwitchListener(new EaseSwitchButton.OnSwitchListener() {
            @Override
            public void onSwitchChange(boolean isOpen) {
                //
            }
        });
    }

    private void initSpinner() {
        mSwtSpinner.attachDataSource(new LinkedList<>(Arrays.asList("高","中","低")));
    }

    private void initTimeDialog() {
        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                TaskSettingActivity.this.date = date;
                mTvChooseTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("HH:mm")));
                timePickerView.dismiss();
            }
        }).setType(new boolean[]{false, false, false, true, true, false})// 显示时分
                .build();
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_task_setting;
    }

    @Override
    protected TaskSettingPresenter initPresenter() {
        return new TaskSettingPresenter();
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
        ButterKnife.bind(this);
    }

    @OnClick({R.id.set_capture_tv_choose_time, R.id.set_capture_tv_sure, R.id.set_capture_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_capture_tv_choose_time:
                timePickerView.show();
                break;
            case R.id.set_capture_tv_sure:
                String delay = mEdtTime.getText().toString();
                boolean isLogo = mSwtBtnLogo.isSwitchOpen();
                int quality = mSwtSpinner.getSelectedIndex();
                getPresenter().setTask(date, delay,isLogo,quality);
                break;
            case R.id.set_capture_tv_cancle:
                onBackPressed();
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
