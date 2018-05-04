package com.alex.witAg.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.witAg.App;
import com.alex.witAg.R;
import com.alex.witAg.adapter.DeviceAdapter;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.ControlPresenter;
import com.alex.witAg.presenter.DataPresenter;
import com.alex.witAg.presenter.LoginPresenter;
import com.alex.witAg.presenter.viewImpl.IControlView;
import com.alex.witAg.ui.test.CrashUtil;
import com.alex.witAg.ui.test.PlaySurfaceView;
import com.alex.witAg.ui.test.jna.HCNetSDKJNAInstance;
import com.alex.witAg.utils.CapturePostUtil;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.DensityUtil;
import com.alex.witAg.utils.DevicesLoginUtil;
import com.alex.witAg.utils.FileUtils;
import com.alex.witAg.utils.MyAnimUtil;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.utils.ToastUtils;
import com.alex.witAg.view.EaseSwitchButton;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.INT_PTR;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_JPEGPARA;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealDataCallBack;
import com.hikvision.netsdk.StdDataCallBack;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;

import org.MediaPlayer.PlayM4.Player;
import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class ControlFragment extends BaseFragment<ControlPresenter, IControlView>  implements IControlView{
    @BindView(R.id.tv_rise)
    LinearLayout mTvRise;
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
    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
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
                if (isOpen){
                    captureTaskUtil.initDevice(getActivity());
                }else {
                    captureTaskUtil.destoryDevice();
                }
            }
        });
    }

    private void initSpinner() {
        spinner.attachDataSource(new LinkedList<>(Arrays.asList("待调节高度","高度1","高度2","高度3","高度4","高度5")));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (App.getIsTaskRun()){
                    ToastUtils.showToast("定时任务执行中，请稍后再试");
                    spinner.setSelectedIndex(0);
                }else {
                    if (getPresenter().isRun || !TextUtils.equals(ShareUtil.getDeviceStatue(), "0")) {
                        ToastUtils.showToast("请在复位状态操作");
                        spinner.setSelectedIndex(0);
                        return;
                    }
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            captureTaskUtil.sendSure(SerialInforStrUtil.getHighStr1());
                            break;
                        case 2:
                            captureTaskUtil.sendSure(SerialInforStrUtil.getHighStr2());
                            break;
                        case 3:
                            captureTaskUtil.sendSure(SerialInforStrUtil.getHighStr3());
                            break;
                        case 4:
                            captureTaskUtil.sendSure(SerialInforStrUtil.getHighStr4());
                            break;
                        case 5:
                            captureTaskUtil.sendSure(SerialInforStrUtil.getHighStr5());
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

    @OnClick({R.id.tv_rise, R.id.tv_decline, R.id.tv_take_photo, R.id.ic_reset, R.id.tv_serial,R.id.ic_open})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_rise:
                rise();
                break;
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

    private boolean isDeviceRun(){
        if (getPresenter().isRun){
            ToastUtils.showToast("机器运行中，请稍后操作");
            return true;
        }else {
            return false;
        }
    }
    private boolean isTaskRun(){
        if (App.getIsTaskRun()){
            ToastUtils.showToast("定时任务执行中，请稍后再试");
            return true;
        }else {
            return false;
        }
    }
    private boolean isCapOpen(){
        if (captureTaskUtil.isCaptureOpen()){
            ToastUtils.showToast("相机已打开");
            return true;
        }else {
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
        if (!isTaskRun()&&!isCapOpen()) {
            new Thread(() -> captureTaskUtil.openCapture()).start();
        }
    }

    private void toReset() {
        mIcReset.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun()&&!isDeviceRun()) {
                new Thread(() -> captureTaskUtil.send(SerialInforStrUtil.getResetStr())).start();
        }
    }

    private void takePhoto() {
        mTvTakePhoto.startAnimation(MyAnimUtil.alphHalf2All());
        //拍照
        if (!isTaskRun()) {
            if (captureTaskUtil.isCaptureOpen()) {
                captureTaskUtil.login();
                captureTaskUtil.capture(CaptureTaskUtil.FROM_Hand);
                //getPresenter().clossCapture();
            }else {
                ToastUtils.showToast("请先打开相机");
            }
                    /*captureTaskUtil.login();
                    captureTaskUtil.capture(CaptureTaskUtil.FROM_Hand);*/
        }
    }

    private void decline() {
        mTvDecline.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun()&&!isDeviceRun()) {
                new Thread(() -> captureTaskUtil.send(SerialInforStrUtil.getDeclineStr())).start();
        }
    }

    private void rise() {
        mTvRise.startAnimation(MyAnimUtil.alphHalf2All());
        if (!isTaskRun()&&!isDeviceRun()) {
            new Thread(() -> captureTaskUtil.send(SerialInforStrUtil.getRiseStr())).start();
        }

    }

    @Override
    public Activity getACtivity() {
        return getActivity();
    }

    @Override
    public void showDialog(DeviceAdapter mDeviceAdapter,SerialPortManager mSerialPortManager) {
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
                .setContentHolder(new ViewHolder(R.layout.activity_show_pic))
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
                ImageView imageView = (ImageView) dialogPlus.getHolderView().findViewById(R.id.show_pic_img);
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
        if (checked){
            mSeraSwtBtn.openSwitch();
        }else {
            mSeraSwtBtn.closeSwitch();
        }
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
