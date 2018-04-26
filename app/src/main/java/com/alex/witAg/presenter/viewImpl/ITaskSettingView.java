package com.alex.witAg.presenter.viewImpl;

import android.app.Activity;

import com.alex.witAg.base.IBaseView;

/**
 * Created by Administrator on 2018-04-08.
 */

public interface ITaskSettingView extends IBaseView {
    Activity getActivity();
    void finishActivity();
}
