package com.alex.witAg.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-04-17.
 */

public class SendSmsResponseBean {

    /**
     * auditor : string
     * confirm : 0
     * confirmTime : 2018-04-17T09:46:42.967Z
     * countDate : 2018-04-17T09:46:42.967Z
     * countUser : string
     * createDate : 2018-04-17T09:46:42.967Z
     * createId : 0
     * deviceId : 0
     * id : 0
     * mibile : string
     * mode : 0
     * name : string
     * opt : string
     * pestCount : 0
     * photoDate : 2018-04-17T09:46:42.967Z
     * recordSaveRequests : [{"children":[{"id":0,"parentId":0,"position":{"x":0,"y":0},"sex":0,"type":"string"}],"color":"string","count":0,"id":0,"name":"string","photoId":0}]
     * updateDate : 2018-04-17T09:46:42.967Z
     * updateId : 0
     * url : string
     * version : 0
     * weather : 0
     */

    private String auditor;
    private int confirm;
    private String confirmTime;
    private String countDate;
    private String countUser;
    private String createDate;
    private int createId;
    private int deviceId;
    private int id;
    private String mibile;
    private int mode;
    private String name;
    private String opt;
    private int pestCount;
    private String photoDate;
    private String updateDate;
    private int updateId;
    private String url;
    private int version;
    private int weather;
    private List<RecordSaveRequestsBean> recordSaveRequests;

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String getCountUser() {
        return countUser;
    }

    public void setCountUser(String countUser) {
        this.countUser = countUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMibile() {
        return mibile;
    }

    public void setMibile(String mibile) {
        this.mibile = mibile;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public int getPestCount() {
        return pestCount;
    }

    public void setPestCount(int pestCount) {
        this.pestCount = pestCount;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public List<RecordSaveRequestsBean> getRecordSaveRequests() {
        return recordSaveRequests;
    }

    public void setRecordSaveRequests(List<RecordSaveRequestsBean> recordSaveRequests) {
        this.recordSaveRequests = recordSaveRequests;
    }

    public static class RecordSaveRequestsBean {
        /**
         * children : [{"id":0,"parentId":0,"position":{"x":0,"y":0},"sex":0,"type":"string"}]
         * color : string
         * count : 0
         * id : 0
         * name : string
         * photoId : 0
         */

        private String color;
        private int count;
        private int id;
        private String name;
        private int photoId;
        private List<ChildrenBean> children;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPhotoId() {
            return photoId;
        }

        public void setPhotoId(int photoId) {
            this.photoId = photoId;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 0
             * parentId : 0
             * position : {"x":0,"y":0}
             * sex : 0
             * type : string
             */

            private int id;
            private int parentId;
            private PositionBean position;
            private int sex;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public PositionBean getPosition() {
                return position;
            }

            public void setPosition(PositionBean position) {
                this.position = position;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class PositionBean {
                /**
                 * x : 0
                 * y : 0
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }
        }
    }
}
