package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-4-25 10:04
 * desc   : 分享好友实体类
 * version: 1.0
 */
public class ShareBean {


    /**
     * data : {"DETAILS":"啦啦啦啦啦啦啦啦啦","FX_logo":"http://192.168.5.242:8500/image/7.jpg","TITLE":"123456789","generateUrl":"http:192.168.2.138/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null"}
     * retCode : SUCCESS
     */

    private DataBean data;
    private String retCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class DataBean {
        /**
         * DETAILS : 啦啦啦啦啦啦啦啦啦
         * FX_logo : http://192.168.5.242:8500/image/7.jpg
         * TITLE : 123456789
         * generateUrl : http:192.168.2.138/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null
         */

        private String DETAILS;
        private String FX_logo;
        private String TITLE;
        private String generateUrl;

        public String getDETAILS() {
            return DETAILS;
        }

        public void setDETAILS(String DETAILS) {
            this.DETAILS = DETAILS;
        }

        public String getFX_logo() {
            return FX_logo;
        }

        public void setFX_logo(String FX_logo) {
            this.FX_logo = FX_logo;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getGenerateUrl() {
            return generateUrl;
        }

        public void setGenerateUrl(String generateUrl) {
            this.generateUrl = generateUrl;
        }
    }


}
