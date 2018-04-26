package com.alex.witAg.http;

import com.alex.witAg.http.cache.IAcache;
import com.alex.witAg.http.network.IApi;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public interface IDataManager extends IApi, IAcache{

    IApi getApi();

    IAcache getAcache();
}
