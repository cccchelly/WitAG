package com.alex.witAg.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.GetTokenBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.ISetComUrlView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class SetCompanyUrlPresenter extends BasePresenter<ISetComUrlView> {

    public void setUrl(String url){
        if (TextUtils.isEmpty(url)){
            ToastUtils.showToast("请输入地址");
        }else {
            ShareUtil.saveCompanyBaseUrl(url);
            //获取token
            mView.showLoadingView("加载中...");
            AppDataManager.getInstence(Net.URL_KIND_BASE)
                    .getToken(AppMsgUtil.getIMEI(App.getAppContext()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<GetTokenBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<GetTokenBean> response) {
                            Log.i("==gettoken==",response.toString());
                            if (response.getCode()==BaseResponse.RESULT_CODE_SUCCESS){
                                //得到token
                                ShareUtil.saveToken(response.getData().getToken());
                                getView().toSplash();
                            }else if (response.getCode()>0){
                                getView().toBindCompany();
                            }
                        }
                    });

        }
    }

}
