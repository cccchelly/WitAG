package com.alex.witAg.http.cache;

import com.alex.witAg.App;
import com.alex.witAg.http.network.Net;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class AppCache implements IAcache{

    private static AppCache mAppCache;
    private final ACache mACache;

    public static AppCache getInstence() {
        if (mAppCache == null) {
            synchronized (AppCache.class) {
                if (mAppCache == null) {
                    mAppCache = new AppCache();
                }
            }
        }
        return mAppCache;
    }

    private AppCache() {
        mACache = ACache.get(App.getAppContext());
    }
}
