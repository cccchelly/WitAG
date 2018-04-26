package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-04-17.
 */

public class GetTokenBean {

    @Override
    public String toString() {
        return "GetTokenBean{" +
                "token='" + token + '\'' +
                '}';
    }

    /**
     * token : 8c5d866a-e116-4ec3-a18f-d0f9595502f9
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
