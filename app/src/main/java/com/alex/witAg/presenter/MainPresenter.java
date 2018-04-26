package com.alex.witAg.presenter;

import com.alex.witAg.AppContants;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IMainView;
import com.alex.witAg.ui.activity.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class MainPresenter extends BasePresenter<IMainView>{
    private static String TAG = MainActivity.class.getName();

    public void getHomePageData() {
        mView.showLoadingView("加载中...");
        AppDataManager.getInstence(Net.URL_KIND_COMPANY).getHomePageData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HomeBean>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<HomeBean> response) {
                        HomeBean data = response.getData();
                        mView.onSuccess(data);
                    }
                });
    }


}
