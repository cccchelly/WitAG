package com.alex.witAg.presenter.viewImpl;

import com.alex.witAg.base.IBaseView;

/**
 * Created by Administrator on 2018-04-11.
 */

public interface IBindPhoneView extends IBaseView {
    void finishActivity();
    void sendClose();
    void sendOpen();
    void setSendCodeText(String text);
}
