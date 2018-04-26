package com.alex.witAg.http.loading;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alex.witAg.R;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by dth
 * Des: 网络请求loading框
 * Date: 2017/10/31.
 */

public class LoadingHolder extends ViewHolder {

    private TextView mTvTip;
    private ProgressBar mProgressBar;
    private String mMsg;

    public LoadingHolder() {
        super(R.layout.dialog_loading);
    }

    public LoadingHolder(String msg) {
        super(R.layout.dialog_loading);
        mMsg = msg;
    }

    @Override
    public View getInflatedView() {
        View view = super.getInflatedView();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mTvTip = (TextView) view.findViewById(R.id.tv_tip);
        if (mMsg != null) {
            mTvTip.setText(mMsg);
        }
        return view;
    }

    public void setTextTip(String msg) {
        if (mTvTip != null) {
            mTvTip.setText(msg);
        }
    }
}
