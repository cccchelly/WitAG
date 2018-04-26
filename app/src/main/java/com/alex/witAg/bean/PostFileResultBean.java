package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-03-29.
 */

public class PostFileResultBean {

    /**
     * code : 0
     * data : {"hash":"Fg901dnqpHSy8ZBZJLYXrXJbJlTp","key":"2867667018650352"}
     * msg : 操作成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * hash : Fg901dnqpHSy8ZBZJLYXrXJbJlTp
         * key : 2867667018650352
         */

        private String hash;
        private String key;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
