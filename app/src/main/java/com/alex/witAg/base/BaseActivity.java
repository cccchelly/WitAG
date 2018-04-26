package com.alex.witAg.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.http.loading.NetLoadingHelper;
import com.alex.witAg.utils.ToastUtils;
import com.alex.witAg.widget.VaryViewHelper;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public abstract class BaseActivity<T extends BasePresenter<V>,V extends BaseMvpView> extends AppCompatActivity implements IBaseView {

    private   VaryViewHelper        mVaryViewHelper;
    protected Activity              mActivity;
    protected T                     mPresenter;
    private   Unbinder              mUnbinder;
    private NetLoadingHelper mNetLoadingHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        setContentView(tellMeLayout());

        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
        mUnbinder = ButterKnife.bind(this);

        //bundle
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            getBundleExtras(bundle);
        }

        mNetLoadingHelper = new NetLoadingHelper(this);
        if (getStatusTargetView() != null) {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(getStatusTargetView())//如果根部局无效，套一层父布局即可
                    //                    .setLoadingView(LayoutInflater.from(mContext).inflate(R.layout.layout_loadingview, null))
                    .setEmptyView(LayoutInflater.from(mActivity).inflate(R.layout.layout_emptyview, null))
                    .setErrorView(LayoutInflater.from(mActivity).inflate(R.layout.layout_errorview, null))
                    .setRefreshListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRetryListener();
                        }
                    })
                    .build();
        }

        init(savedInstanceState);
    }

    /**
     * 初始化方法
     */
    protected abstract void init(@Nullable Bundle savedInstanceState);

    /**
     * 传递bundle数据
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);

    /**
     * 布局
     * @return
     */
    protected abstract int tellMeLayout();

    protected abstract T initPresenter();

    protected T getPresenter() {
        if (mPresenter == null) {
            throw new RuntimeException("presenter cannot be initialized!");
        }
        return mPresenter;
    }


    /**
     * 点击错误页面重新加载数据
     */
    protected  abstract void onRetryListener();

    /**
     *
     * @return
     */
    protected  abstract View getStatusTargetView();

    @Override
    public void addDisposable(Disposable disposable) {
        mPresenter.addDisposable(disposable);
    }

    public void startActivityWithAnim(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_anim_in,R.anim.activity_anim_stay);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_anim_stay,R.anim.activity_anim_out);
    }


    @Override
    public void showLoadingView(String showText){
        mNetLoadingHelper.showLoadingView(showText);
    }


    @Override
    public void dissmissLoadingView(){
        mNetLoadingHelper.dissLoadingView();
    }

    @Override
    public void showErrorView(){
        dissmissLoadingView();
        if (mVaryViewHelper != null) {
            mVaryViewHelper.showErrorView();
        }
    }

    @Override
    public void showEmptyView(){
        dissmissLoadingView();
        if (mVaryViewHelper != null) {
            mVaryViewHelper.showEmptyView();
        }
    }

    @Override
    public void showDataView(){
        dissmissLoadingView();
        if (mVaryViewHelper != null) {
            mVaryViewHelper.showDataView();
        }
    }

    @Override
    public void showException(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {

        if (mVaryViewHelper != null){
            mVaryViewHelper.releaseVaryView();
            mVaryViewHelper = null;
        }

        if (mNetLoadingHelper != null) {
            mNetLoadingHelper.releaseView();
            mNetLoadingHelper = null;
        }

        if (mPresenter != null) {
            mPresenter.unDisposable();
            mPresenter.detachView();
            mPresenter = null;
        }
        mUnbinder.unbind();
        mActivity = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
