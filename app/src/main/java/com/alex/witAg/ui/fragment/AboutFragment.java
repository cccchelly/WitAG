package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.AboutPresenter;
import com.alex.witAg.presenter.viewImpl.IAboutView;
import com.alex.witAg.utils.AppMsgUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class AboutFragment extends BaseFragment<AboutPresenter, IAboutView> implements IAboutView {
    @BindView(R.id.about_imei)
    TextView mImei;
    Unbinder unbinder;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        mImei.setText("设备号："+AppMsgUtil.getIMEI(getActivity()));
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_about;
    }

    @Override
    protected AboutPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.activity_anim_in);
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
