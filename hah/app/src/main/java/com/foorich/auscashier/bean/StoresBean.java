package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-18 13:04
 * desc   :
 * version: 1.0
 */
public class StoresBean {


    /**
     * data : {"OsType":"2","createTime":"2019-05-18 12:50:34","merchantCode":"518634802376975","merchantName":"永辉","status":"3"}
     * errorCode : 29
     * message : 保存成功
     * retCode : SUCCESS
     */

    private DataBean data;
    private String errorCode;
    private String message;
    private String retCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class DataBean {
        /**
         * OsType : 2
         * createTime : 2019-05-18 12:50:34
         * merchantCode : 518634802376975
         * merchantName : 永辉
         * status : 3
         */

        private String OsType;
        private String createTime;
        private String merchantCode;
        private String merchantName;
        private String status;

        public String getOsType() {
            return OsType;
        }

        public void setOsType(String OsType) {
            this.OsType = OsType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
