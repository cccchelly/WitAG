package com.alex.witAg.presenter.viewImpl;

import android.app.Activity;
import android.content.Context;

import com.alex.witAg.base.IBaseView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public interface ILoginView extends IBaseView{
    Activity getContext();
    void  back();
}
