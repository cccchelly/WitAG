package com.alex.witAg.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.ShowPicsPresenter;
import com.alex.witAg.presenter.viewImpl.IShowPicView;
import com.alex.witAg.ui.test.CrashUtil;
import com.alex.witAg.view.ZoomableDraweeView;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppContants.ARouterUrl.SHOW_PIC)
public class ShowPicActivity extends BaseActivity<ShowPicsPresenter, IShowPicView> implements IShowPicView {
    @BindView(R.id.show_pic_img)
    ZoomableDraweeView mImgPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mImgPic.setOnClickListener(() -> onBackPressed());
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
        String url = bundle.getString(AppContants.SHOW_PIC_URL_KEY);
        mImgPic.setImageURI(Uri.parse(url));
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
