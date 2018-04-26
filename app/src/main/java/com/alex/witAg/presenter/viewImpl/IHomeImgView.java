package com.alex.witAg.presenter.viewImpl;

import com.alex.witAg.base.IBaseView;
import com.alex.witAg.bean.PhotoDetailRecodeBean;
import com.alex.witAg.bean.PicListBean;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public interface IHomeImgView extends IBaseView{
    void updatePicture(PicListBean picListBean);
    void updateRecode(PhotoDetailRecodeBean photoDetailRecodeBean);
}
