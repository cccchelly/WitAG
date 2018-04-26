package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-04-12.
 */

public class UpdateMsgBean {

    @Override
    public String toString() {
        return "{" +
                "\"hasUpdate\":" + hasUpdate +","+
                "\"code\":" + code +","+
                "\"size\":" + size +","+
                "\"name\":\"" + name + '\"' +","+
                "\"content\":\"" + content + '\"' +","+
                "\"url\":\"" + url + '\"' +","+
                "\"md5\":\"" + md5 + '\"' +
                '}';
    }

    /**
     * hasUpdate : true
     * code : 10001
     * size : 29348064
     * name : 1.0.1
     * content : 测试版本2
     * url : http://p3o0oo73j.bkt.clouddn.com/witag.apk
     * md5 : 3F067C882940B4E49AEE4C6B9EB18510
     */

    private boolean hasUpdate;
    private int code;
    private int size;
    private String name;
    private String content;
    private String url;
    private String md5;

    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
