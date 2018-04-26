package com.alex.witAg.http.loading;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-24.
 */

public interface NetLoadingInterface {

    void showLoadingView();

    void showLoadingView(String msg);

    void dissLoadingView();

    void releaseView();
}
