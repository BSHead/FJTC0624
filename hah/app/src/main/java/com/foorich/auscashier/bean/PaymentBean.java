package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-12 16:10
 * desc   : 支付方式报表
 * version: 1.0
 */
public class PaymentBean {


    /**
     * data : [{"color":"#7735FF","count":7,"payType":7,"payTypeDesc":"qq钱包","ratio":"7.29%"},{"color":"#FF0000","count":6,"payType":6,"payTypeDesc":" 京东钱包","ratio":"6.25%"},{"color":"#0000FF","count":14,"payType":14,"payTypeDesc":"支付宝","ratio":"14.58%"},{"color":"#EE82EE","count":29,"payType":19,"payTypeDesc":"翼支付","ratio":"30.21%"},{"color":"#0591FA","count":40,"payType":2,"payTypeDesc":"余额","ratio":"41.67%"}]
     * message : SUCCESS
     * retCode : SUCCESS
     */

    private String message;
    private String retCode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * color : #7735FF
         * count : 7
         * payType : 7
         * payTypeDesc : qq钱包
         * ratio : 7.29%
         */

        private String color ;
        private String count;
        private int payType;
        private String payTypeDesc;
        private String ratio;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getPayTypeDesc() {
            return payTypeDesc;
        }

        public void setPayTypeDesc(String payTypeDesc) {
            this.payTypeDesc = payTypeDesc;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }
    }
}
