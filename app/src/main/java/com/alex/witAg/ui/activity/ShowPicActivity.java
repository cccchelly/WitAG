package com.alex.witAg.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.ShowPicsPresenter;
import com.alex.witAg.presenter.viewImpl.IShowPicView;
import com.alex.witAg.ui.test.CrashUtil;
import com.alex.witAg.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class ShowPicActivity extends BaseActivity<ShowPicsPresenter,IShowPicView> implements IShowPicView {
    @BindView(R.id.show_pic_img)
    ImageView mImgPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashUtil crashUtil = CrashUtil.getInstance();
        crashUtil.init(this);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
       // getPresenter().getBitmap(bundle);
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_show_pic;
    }

    @Override
    protected ShowPicsPresenter initPresenter() {
        return new ShowPicsPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void showPic(Bitmap bitmap) {
        mImgPic.setImageBitmap(bitmap);
    }

}
