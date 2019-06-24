package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-10 10:52
 * desc   :
 * version: 1.0
 */
public class MessageList {

    /**
     * data : {"allIsRead":false,"jiaoYiIsRead":false,"xiTongIsRead":false}
     * message : SUCCESS
     * retCode : SUCCESS
     */

    private DataBean data;
    private String message;
    private String retCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * allIsRead : false
         * jiaoYiIsRead : false
         * xiTongIsRead : false
         */

        private String  allIsRead;
        private String jiaoYiIsRead;
        private String xiTongIsRead;

        public String getAllIsRead() {
            return allIsRead;
        }

        public void setAllIsRead(String allIsRead) {
            this.allIsRead = allIsRead;
        }

        public String getJiaoYiIsRead() {
            return jiaoYiIsRead;
        }

        public void setJiaoYiIsRead(String jiaoYiIsRead) {
            this.jiaoYiIsRead = jiaoYiIsRead;
        }

        public String getXiTongIsRead() {
            return xiTongIsRead;
        }

        public void setXiTongIsRead(String xiTongIsRead) {
            this.xiTongIsRead = xiTongIsRead;
        }
    }
}
