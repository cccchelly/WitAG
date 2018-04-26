package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-03-29.
 */

public class PicMessageBean {
    private int deviceId;
    private String name;
    private String url;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PicMessageBean{" +
                "deviceId=" + deviceId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
