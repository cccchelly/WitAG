package com.alex.witAg.bean;

/**
 * Created by Administrator on 2018-05-03.
 */

public class MqttMsgBean {

    /**
     * o : op_camera
     * d : {"cmd":"open"}
     */

    private String o;
    private DBean d;

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * cmd : open
         */

        private String cmd;

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }
    }
}
