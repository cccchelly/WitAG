package com.alex.witAg.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/3/30.
 */
public class PicPathsBean extends DataSupport{
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
