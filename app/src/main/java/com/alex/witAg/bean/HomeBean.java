package com.alex.witAg.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/13.
 */

public class HomeBean {


    private List<PlatformEntity> platform;
    private List<NoticeEntity>   notice;
    private List<DisGoodsEntity> dis_goods;
    private List<NowGoodsEntity> now_goods;

    public List<PlatformEntity> getPlatform() {
        return platform;
    }

    public void setPlatform(List<PlatformEntity> platform) {
        this.platform = platform;
    }

    public List<NoticeEntity> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeEntity> notice) {
        this.notice = notice;
    }

    public List<DisGoodsEntity> getDis_goods() {
        return dis_goods;
    }

    public void setDis_goods(List<DisGoodsEntity> dis_goods) {
        this.dis_goods = dis_goods;
    }

    public List<NowGoodsEntity> getNow_goods() {
        return now_goods;
    }

    public void setNow_goods(List<NowGoodsEntity> now_goods) {
        this.now_goods = now_goods;
    }

    public static class PlatformEntity {
        /**
         * adv_title : 商城推广图
         * adv_url : http://www..com.cn
         * adv_image : upload/advertising/1497067441.png
         * slide_sort : 0
         */

        private String adv_title;
        private String adv_url;
        private String adv_image;
        private int    slide_sort;

        public String getAdv_title() {
            return adv_title;
        }

        public void setAdv_title(String adv_title) {
            this.adv_title = adv_title;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_image() {
            return adv_image;
        }

        public void setAdv_image(String adv_image) {
            this.adv_image = adv_image;
        }

        public int getSlide_sort() {
            return slide_sort;
        }

        public void setSlide_sort(int slide_sort) {
            this.slide_sort = slide_sort;
        }
    }

    public static class NoticeEntity {
        /**
         * id : 11
         * notice_message : 手机端首页公告
         * shop_name : 官方直营店
         */

        private int id;
        private String notice_message;
        private String shop_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNotice_message() {
            return notice_message;
        }

        public void setNotice_message(String notice_message) {
            this.notice_message = notice_message;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }

    public static class DisGoodsEntity {
        /**
         * goods_id : 413
         * discount : 0.90
         * goods_name : iphone11
         * master : upload/advertising/1497069853.png
         * price : 10.00
         * promotion_price : 10.00
         */

        private int goods_id;
        private String discount;
        private String goods_name;
        private String master;
        private String price;
        private String promotion_price;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String pic_cover) {
            this.master = pic_cover;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }
    }

    public static class NowGoodsEntity {
        /**
         * goods_id : 407
         * goods_name : 测试商品
         * price : 1000.00
         * promotion_price : 1000.00
         * pic_cover : upload/advertising/1497069865.png
         */

        private int goods_id;
        private String goods_name;
        private String price;
        private String promotion_price;
        private String master;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }
    }

}

