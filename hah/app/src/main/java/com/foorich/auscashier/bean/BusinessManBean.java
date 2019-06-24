package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-18 11:57
 * desc   :
 * version: 1.0
 */
public class BusinessManBean {


    /**
     * message : 保存成功
     * retCode : SUCCESS
     * errorCode : 29
     * data : {"merchantCode":"238008696978276"}
     */

    private String message;
    private String retCode;
    private String errorCode;
    private DataBean data;

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * merchantCode : 238008696978276
         */

        private String merchantCode;

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }
    }
}
