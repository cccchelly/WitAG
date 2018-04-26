package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.HomePresenter;
import com.alex.witAg.presenter.viewImpl.IHomeView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class HomeFragment extends BaseFragment<HomePresenter, IHomeView> implements IHomeView {


    @BindView(R.id.rb_img)
    RadioButton mRbImg;
    @BindView(R.id.rb_line)
    RadioButton mRbLine;
    @BindView(R.id.radio_group)
    RadioGroup  mRadioGroup;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    private HomeImgFragment mHomeImgFragment;
    private HomeLineFragment mHomeLineFragment;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Logger.d("checkedImg: ",checkedId == R.id.rb_img);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            switch (checkedId) {
                case R.id.rb_img:
                    transaction.hide(mHomeLineFragment)
                            .show(mHomeImgFragment)
                            .commitAllowingStateLoss();
                    break;
                case R.id.rb_line:
                    transaction.hide(mHomeImgFragment)
                            .show(mHomeLineFragment)
                            .commitAllowingStateLoss();
                    break;
                default:
            }
        });

        initFragment(savedInstanceState);

    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (savedInstanceState != null) {

            mHomeImgFragment = (HomeImgFragment) getChildFragmentManager().findFragmentByTag("HomeImgFragment");
            mHomeLineFragment = (HomeLineFragment) getChildFragmentManager().findFragmentByTag("HomeLineFragment");
        } else {

            mHomeImgFragment = new HomeImgFragment();
            mHomeLineFragment = new HomeLineFragment();

            transaction.add(R.id.fl_container, mHomeImgFragment, "HomeImgFragment");
            transaction.add(R.id.fl_container, mHomeLineFragment, "HomeLineFragment");
        }
        transaction.commit();
        mRbImg.setChecked(true);
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

}
