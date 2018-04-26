package com.alex.witAg.presenter.viewImpl;

import android.app.Activity;

import com.alex.witAg.base.IBaseView;
import com.alex.witAg.bean.HomeBean;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public interface IMainView extends IBaseView{
    Activity getActivity();
    void onSuccess(HomeBean homeBean);
}
