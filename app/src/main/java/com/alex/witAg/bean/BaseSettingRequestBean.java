package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-04-19.
 */

public class BaseSettingRequestBean {

    /**
     * accountName : string
     * address : string
     * deviceId : 0
     * imei : string
     * latitude : string
     * longitude : string
     * maintain : string
     * name : string
     * password : string
     * phone : string
     * photoInterval : 0
     * photoMark : 0
     * photoQuality : 0
     * photoStart : string
     */

    private String accountName;
    private String address;
    private int deviceId;
    private String imei;
    private String latitude;
    private String longitude;
    private String maintain;
    private String name;
    private String password;
    private String phone;
    private int photoInterval;
    private int photoMark;
    private int photoQuality;
    private String photoStart;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhotoInterval() {
        return photoInterval;
    }

    public void setPhotoInterval(int photoInterval) {
        this.photoInterval = photoInterval;
    }

    public int getPhotoMark() {
        return photoMark;
    }

    public void setPhotoMark(int photoMark) {
        this.photoMark = photoMark;
    }

    public int getPhotoQuality() {
        return photoQuality;
    }

    public void setPhotoQuality(int photoQuality) {
        this.photoQuality = photoQuality;
    }

    public String getPhotoStart() {
        return photoStart;
    }

    public void setPhotoStart(String photoStart) {
        this.photoStart = photoStart;
    }
}
