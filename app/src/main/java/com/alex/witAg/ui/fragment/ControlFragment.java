package com.alex.witAg.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.witAg.App;
import com.alex.witAg.R;
import com.alex.witAg.adapter.DeviceAdapter;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.ControlPresenter;
import com.alex.witAg.presenter.viewImpl.IControlView;
import com.alex.witAg.taskqueue.SeralTask;
import com.alex.witAg.taskqueue.TaskQueue;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.DensityUtil;
import com.alex.witAg.utils.MyAnimUtil;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;
import com.alex.witAg.view.EaseSwitchButton;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class ControlFragment extends BaseFragment<ControlPresenter, IControlView> implements IControlView {
    @BindView(R.id.tv_decline)
    LinearLayout mTvDecline;
    @BindView(R.id.tv_take_photo)
    LinearLayout mTvTakePhoto;
    @BindView(R.id.ic_reset)
    LinearLayout mIcReset;
    @BindView(R.id.tv_serial)
    TextView mTvSearal;
    @BindView(R.id.ic_open)
    LinearLayout mTvOpen;
    @BindView(R.id.control_spinner)
    NiceSpinner spinner;
    @BindView(R.id.control_swtbtn)
    EaseSwitchButton mSeraSwtBtn;
    @BindView(R.id.control_tv_sera_statues)
    TextView tvSeraStu;
    private CaptureTaskUtil captureTaskUtil;

    TaskQueue taskQueue;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        taskQueue = TaskQueue.getInstance();

        captureTaskUtil = CaptureTaskUtil.instance();
        captureTaskUtil.initDevice(getActivity());
        initSpinner();
        initSwtBtn();
        captureTaskUtil.setOnCaptureFinishListener((bitmap, file, name) -> {
            showCapture(bitmap);
            //CapturePostUtil.postPic(file,name);
        });
        //getPresenter().initDevice(getActivity());
        //getPresenter().login();
    }

    private void initSwtBtn() {
        mSeraSwtBtn.closeSwitch();
        mSeraSwtBtn.setOnSwitchListener(new EaseSwitchButton.OnSwitchListener() {
            @Override
            public void onSwitchChange(boolean isOpen) {
                if (isOpen) {
                    captureTaskUtil.initDevice(getActivity());
                } else {
                    captureTaskUtil.destoryDevice();
                }
            }
        });
    }

    private void initSpinner() {
        spinner.attachDataSource(new LinkedList<>(Arrays.asList("待调节高度", "高度1", "高度2", "高度3", "高度4", "高度5")));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (App.getIsTaskRun()) {
                    ToastUtils.showToast("定时任务执行中，请稍后再试");
                    spinner.setSelectedIndex(0);
                } else {
                    if (getPresenter().isRun || !TextUtils.equals(ShareUtil.getDeviceStatue(), "0")) {
                        ToastUtils.showToast("请在复位状态操作");
                        spinner.setSelectedIndex(0);
                        return;
                    }
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            taskQueue.add(new SeralTask(SerialInforStrUtil.getHighStr1()));
                            break;
                        case 2:
                            taskQueue.add(new SeralTask(SerialInforStrUtil.getHighStr2()));
                            break;
                        case 3:
                            taskQueue.add(new SeralTask(SerialInforStrUtil.getHighStr3()));
                            break;
                        case 4:
                            taskQueue.add(new SeralTask(SerialInforStrUtil.getHighStr4()));
                            break;
                        case 5:
                            taskQueue.add(new SeralTask(SerialInforStrUtil.getHighStr5()));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_control;
    }

    @Override
    protected ControlPresenter initPresenter() {
        return new ControlPresenter();
    }

    @Override
    protected void onRetryListener() {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void onDestroy() {
        //getPresenter().destoryDevice();
        super.onDestroy();
    }

    @OnClick({R.id.tv_decline, R.id.tv_take_photo, R.id.ic_reset, R.id.tv_serial, R.id.ic_open})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_decline:
                decline();
                break;
            case R.id.tv_take_photo:
                takePhoto();
                break;
            case R.id.ic_reset:
                toReset();
                break;
            case R.id.ic_open:
                toOpen();
                break;
            case R.id.tv_serial:
                resetLocal();
                break;
        }
    }

    private boolean isDeviceRun() {
        if (getPresenter().isRun) {
            ToastUtils.showToast("机器运行中，请稍后操作");
            return true;
        } else {
            return false;
        }
    }

    private boolean isTaskRun() {
        if (App.getIsTaskRun()) {
            ToastUtils.showToast("定时任务执行中，请稍后再试");
            return true;
        } else {
            return false;
        }
    }

    private boolean isCapOpen() {
        if (captureTaskUtil.isCaptureOpen()) {
            ToastUtils.showToast("相机已打开");
            return true;
        } else {
            return false;
        }
    }

    private void resetLocal() {
        mTvSearal.startAnimation(MyAnimUtil.alphHalf2All());
        getPresenter().restLocalMsg();
        //captureTaskUtil.sendSure(SerialInforStrUtil.getRestartStr());
        //getPresenter().getDeviceList();
    }

    private void toOpen() {
        mTvOpen.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun() && !isCapOpen()) {
            new Thread(() -> {
                getActivity().runOnUiThread(() -> ToastUtils.showToast("准备打开摄像机并将沾虫板翻转至正面"));
                captureTaskUtil.openCaptureTurnPositive();
            }).start();
        }
    }

    private void toReset() {
        mIcReset.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun() && !isDeviceRun()) {
            new Thread(() -> {
                getActivity().runOnUiThread(() -> ToastUtils.showToast("准备将沾虫板复位并关闭相机"));
                taskQueue.add(new SeralTask(SerialInforStrUtil.getForceRestartStr()));
            }).start();
        }
    }

    private void takePhoto() {
        mTvTakePhoto.startAnimation(MyAnimUtil.alphHalf2All());
        //拍照
        if (!isTaskRun()) {
            if (captureTaskUtil.isCaptureOpen()) {
                new Thread(() -> {
                    int errCode = captureTaskUtil.loginCapture();  //登录摄像机
                    if (errCode == 0) {  //没有错误
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        captureTaskUtil.capture(CaptureTaskUtil.FROM_Hand); //执行拍照任务
                    } else if (errCode == 1) {
                        getActivity().runOnUiThread(() -> ToastUtils.showToast("账号密码错误！"));
                    } else {
                        getActivity().runOnUiThread(() -> ToastUtils.showToast("连接摄像机失败！"));
                    }
                }).start();
            } else {
                ToastUtils.showToast("请先打开相机");
            }
                    /*captureTaskUtil.login();
                    captureTaskUtil.capture(CaptureTaskUtil.FROM_Hand);*/
        }
    }

    private void decline() {
        mTvDecline.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun() && !isDeviceRun()) {
            new Thread(() ->
            {
                if (captureTaskUtil.isCaptureOpen()) {
                    getActivity().runOnUiThread(() -> ToastUtils.showToast("准备将沾虫板翻转至反面"));
                    taskQueue.add(new SeralTask(SerialInforStrUtil.getDeclineStr()));
                } else {
                    getActivity().runOnUiThread(() -> ToastUtils.showToast("请先启动摄像机"));
                }
            }).start();
        }
    }

    @Override
    public Activity getACtivity() {
        return getActivity();
    }

    @Override
    public void showDialog(DeviceAdapter mDeviceAdapter, SerialPortManager mSerialPortManager) {
        DialogPlus.newDialog(getContext())
                .setContentHolder(new ListHolder())
                .setAdapter(mDeviceAdapter)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(DensityUtil.dip2px(600))
                .setOnItemClickListener((dialog, item, view1, position) -> {
                    mSerialPortManager.closeSerialPort();
                    Device mDevice = mDeviceAdapter.getItem(position);
                    getPresenter().openDevice(mDevice);
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    public void showOpenMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showCapture(Bitmap bitmap) {
        DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.show_pic_imageview))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogPlus.show();
                ImageView imageView = (ImageView) dialogPlus.getHolderView().findViewById(R.id.show_pic_imageview);
                imageView.setOnClickListener(v -> dialogPlus.dismiss());
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void showSeraStatus(String sta) {
        tvSeraStu.setText(sta);
    }

    @Override
    public void setSwtBtnChecked(boolean checked) {
        if (checked) {
            mSeraSwtBtn.openSwitch();
        } else {
            mSeraSwtBtn.closeSwitch();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.activity_anim_in);
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

}
