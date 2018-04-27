package com.alex.witAg.utils;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alex.witAg.App;
import com.alex.witAg.R;

/**
 * Created by Administrator on 2018-04-26.
 */

public class MyAnimUtil {

    public static Animation alph02All(){
        return AnimationUtils.loadAnimation(App.getAppContext(), R.anim.alph02all);
    }

    public static  Animation alphHalf2All(){
        return AnimationUtils.loadAnimation(App.getAppContext(),R.anim.alph_half2all);
    }





}
