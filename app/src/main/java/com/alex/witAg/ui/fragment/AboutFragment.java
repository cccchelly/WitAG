package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.AboutPresenter;
import com.alex.witAg.presenter.viewImpl.IAboutView;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class AboutFragment extends BaseFragment<AboutPresenter,IAboutView> implements IAboutView {
    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

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
        if (enter){
            return AnimationUtils.loadAnimation(getActivity(),R.anim.activity_anim_in);
        }else {
            return super.onCreateAnimation(transit,enter,nextAnim);
        }
    }
}
