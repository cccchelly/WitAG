package com.alex.witAg.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.bean.UpdateMsgBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.presenter.MainPresenter;
import com.alex.witAg.presenter.viewImpl.IMainView;
import com.alex.witAg.service.CaptureService;
import com.alex.witAg.service.PostPicService;
import com.alex.witAg.ui.fragment.AboutFragment;
import com.alex.witAg.ui.fragment.ControlFragment;
import com.alex.witAg.ui.fragment.DataFragment;
import com.alex.witAg.ui.fragment.HomeFragment;
import com.alex.witAg.ui.fragment.SettingFragment;
import com.alex.witAg.ui.test.CrashUtil;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.DeviceUtil;
import com.alex.witAg.utils.DevicesLoginUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TaskServiceUtil;
import com.alex.witAg.utils.eventbus.SerialBackMessage;
import com.alex.witAg.view.LeftTabView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.tablemanager.Connector;

import butterknife.BindView;
import ezy.boost.update.ICheckAgent;
import ezy.boost.update.IUpdateChecker;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path = AppContants.ARouterUrl.MAIN_ACTIVITY/*, extras = AppContants.LOGIN_INTERCEPTOR*/)
public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView {


    @BindView(R.id.left_tab_view)
    LeftTabView mLeftTabView;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    private HomeFragment    mHomeFragment;
    private SettingFragment mSettingFragment;
    private DataFragment    mDataFragment;
    private ControlFragment mControlFragment;
    private AboutFragment mAboutFragment;
    SQLiteDatabase db= Connector.getDatabase();

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(MainActivity.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(MainActivity.this);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        TaskServiceUtil.startTasks();
    }

    private void initListener() {
        mLeftTabView.setOnSelectedChangeListener((view, position) -> {
            Logger.d("position:  " +position);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            switch (position) {
                //首页
                case 0:
                    transaction.hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mHomeFragment)
                            .commitAllowingStateLoss();
                    break;
                //实时数据
                case 1:

                    transaction.hide(mHomeFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mDataFragment)
                            .commitAllowingStateLoss();
                    break;
                //系统设置
                case 2:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mSettingFragment)
                            .commitAllowingStateLoss();
                    break;
                //手动控制
                case 3:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mAboutFragment)
                            .show(mControlFragment)
                            .commitAllowingStateLoss();
                    break;
                //关于我们
                case 4:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .show(mAboutFragment)
                            .commitAllowingStateLoss();
                    break;
                default:
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        int currentTabPosition = 0;
        if (savedInstanceState != null) {//内存重启
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            mSettingFragment = (SettingFragment) getSupportFragmentManager().findFragmentByTag("SettingFragment");
            mDataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag("DataFragment");
            mControlFragment = (ControlFragment) getSupportFragmentManager().findFragmentByTag("ControlFragment");
            mAboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag("AboutFragment");
            currentTabPosition = savedInstanceState.getInt(AppContants.HOME_CURRENT_TAB_POSITION);
        } else {
            mHomeFragment = new HomeFragment();
            mSettingFragment = new SettingFragment();
            mDataFragment = new DataFragment();
            mControlFragment = new ControlFragment();
            mAboutFragment = new AboutFragment();
            transaction.add(R.id.fl_container, mHomeFragment, "HomeFragment");
            transaction.add(R.id.fl_container, mSettingFragment, "SettingFragment");
            transaction.add(R.id.fl_container, mDataFragment, "DataFragment");
            transaction.add(R.id.fl_container, mControlFragment, "ControlFragment");
            transaction.add(R.id.fl_container, mAboutFragment, "AboutFragment");
        }
        transaction.commit();

        initListener();
        mLeftTabView.setSelectPosition(currentTabPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //内存重启前保存位置
        Logger.e("onSaveInstanceState进来了1");
        if (mLeftTabView != null) {
            Logger.e("onSaveInstanceState进来了2");
            outState.putInt(AppContants.HOME_CURRENT_TAB_POSITION, mLeftTabView.getSelectPosition());
        }
    }
    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onSuccess(HomeBean homeBean) {

    }

}
