package com.alex.witAg.utils;

import android.content.Context;

import com.alex.witAg.AppContants;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;

/**
 * Created by dth
 * Des: 登录跳转拦截器
 * Date: 2018-01-25.
 */

@Interceptor(priority = 7)
public class LoginInterceptor implements IInterceptor{
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        int extra = postcard.getExtra();
        Logger.d("extra: %d" , extra);
        if (extra == AppContants.LOGIN_INTERCEPTOR) {//拦截登录，需要验证是否登录
            // TODO: 2018-01-25 判断登录
//            if (isLogin) {
//                callback.onContinue(postcard);
//            } else {
//                callback.onInterrupt(new RuntimeException("user not login!"));
//                ToastUtils.showToast("请先登录...");
//            }
            ARouter.getInstance().build(AppContants.ARouterUrl.LOGIN_ACTIVITY)
                    .navigation();
            Logger.w("LoginInterceptor: %s" , "user not login!");
            callback.onInterrupt(new RuntimeException("user not login!"));
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
