package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-22 14:47
 * desc   : 首页初始化
 * version: 1.0
 */
public class InitializationBean {


    /**
     * data : {"penReceipts":31,"penRecharge":0,"receiptsAmt":141,"rechargeAmt":0}
     * errorCode : 028
     * message : 查询成功
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
         * penReceipts : 31
         * penRecharge : 0
         * receiptsAmt : 141.0
         * rechargeAmt : 0.0
         */

        private int penReceipts;
        private int penRecharge;
        private double receiptsAmt;
        private double rechargeAmt;

        public int getPenReceipts() {
            return penReceipts;
        }

        public void setPenReceipts(int penReceipts) {
            this.penReceipts = penReceipts;
        }

        public int getPenRecharge() {
            return penRecharge;
        }

        public void setPenRecharge(int penRecharge) {
            this.penRecharge = penRecharge;
        }

        public double getReceiptsAmt() {
            return receiptsAmt;
        }

        public void setReceiptsAmt(double receiptsAmt) {
            this.receiptsAmt = receiptsAmt;
        }

        public double getRechargeAmt() {
            return rechargeAmt;
        }

        public void setRechargeAmt(double rechargeAmt) {
            this.rechargeAmt = rechargeAmt;
        }
    }
}
