package com.alex.witAg.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.PositionPresenter;
import com.alex.witAg.presenter.viewImpl.IPositionView;

public class PositionActivity extends BaseActivity<PositionPresenter,IPositionView> implements IPositionView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_position;
    }

    @Override
    protected PositionPresenter initPresenter() {
        return new PositionPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


}
