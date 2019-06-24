package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-12 10:28
 * desc   : 报表字段
 * version: 1.0
 */
public class IncomeBean {


    /**
     * data : {"avgAmt":0,"list":[{"incomeAmt":0,"settlementDay":"06.03"},{"incomeAmt":0,"settlementDay":"06.04"},{"incomeAmt":0,"settlementDay":"06.05"},{"incomeAmt":0,"settlementDay":"06.06"},{"incomeAmt":0,"settlementDay":"06.07"},{"incomeAmt":0,"settlementDay":"06.08"},{"incomeAmt":0,"settlementDay":"06.09"}],"maxAmt":0,"ratio":"0.00%","totalAmt":0}
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
         * avgAmt : 0.0
         * list : [{"incomeAmt":0,"settlementDay":"06.03"},{"incomeAmt":0,"settlementDay":"06.04"},{"incomeAmt":0,"settlementDay":"06.05"},{"incomeAmt":0,"settlementDay":"06.06"},{"incomeAmt":0,"settlementDay":"06.07"},{"incomeAmt":0,"settlementDay":"06.08"},{"incomeAmt":0,"settlementDay":"06.09"}]
         * maxAmt : 0.0
         * ratio : 0.00%
         * totalAmt : 0.0
         */

        private double avgAmt;
        private double maxAmt;
        private String ratio;
        private double totalAmt;
        private List<ListBean> list;

        public double getAvgAmt() {
            return avgAmt;
        }

        public void setAvgAmt(double avgAmt) {
            this.avgAmt = avgAmt;
        }

        public double getMaxAmt() {
            return maxAmt;
        }

        public void setMaxAmt(double maxAmt) {
            this.maxAmt = maxAmt;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public double getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(double totalAmt) {
            this.totalAmt = totalAmt;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * incomeAmt : 0.0
             * settlementDay : 06.03
             */

            private double incomeAmt;
            private String settlementDay;

            public double getIncomeAmt() {
                return incomeAmt;
            }

            public void setIncomeAmt(double incomeAmt) {
                this.incomeAmt = incomeAmt;
            }

            public String getSettlementDay() {
                return settlementDay;
            }

            public void setSettlementDay(String settlementDay) {
                this.settlementDay = settlementDay;
            }
        }
    }
}
