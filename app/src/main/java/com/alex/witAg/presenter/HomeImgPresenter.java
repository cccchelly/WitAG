package com.alex.witAg.presenter;

import android.util.Log;

import com.alex.witAg.AppContants;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.bean.PhotoDetailRecodeBean;
import com.alex.witAg.bean.PicListBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IHomeImgView;
import com.alex.witAg.ui.fragment.HomeImgFragment;
import com.alex.witAg.utils.ShareUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeImgPresenter extends BasePresenter<IHomeImgView> {

    private static final String TAG = HomeImgFragment.class.getName();

    public void getHomeImgList(String time){
        //time = "2018-03-14 00:00:00";
        AppDataManager.getInstence(Net.URL_KIND_COMPANY).getPicListData(ShareUtil.getToken(),time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PicListBean>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<PicListBean> response) {
                        PicListBean data = response.getData();
                        mView.updatePicture(data);
                    }
                });

    }
     public void getRecode(String id){
         AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                 .getRecodeByPhoto(id,ShareUtil.getToken())
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new BaseObserver<BaseResponse<PhotoDetailRecodeBean>>() {
                     @Override
                     public void onSuccess(BaseResponse<PhotoDetailRecodeBean> response) {
                         PhotoDetailRecodeBean photoDetailRecodeBean = response.getData();
                         Log.i(TAG,photoDetailRecodeBean.toString());
                         getView().updateRecode(photoDetailRecodeBean);
                     }
                 });
     }
}
