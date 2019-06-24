package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-8 16:33
 * desc   : 商户所在类型
 * version: 1.0
 */
public class MerchantsTypeBean {


    /**
     * data : [{"id":1,"limit":10,"mcc":"0742","page":1,"rows":10,"service":"兽医服务","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":2,"limit":10,"mcc":"0763","page":1,"rows":10,"service":"农业合作","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":3,"limit":10,"mcc":"0780","page":1,"rows":10,"service":"景观美化及园艺服务","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":4,"limit":10,"mcc":"1520","page":1,"rows":10,"service":"一般承包商－住宅与商业楼","tenant":"","tradeCode":"0002","tradeType":"房地产与金融业"},{"id":5,"limit":10,"mcc":"4011","page":1,"rows":10,"service":"铁路运输","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":6,"limit":10,"mcc":"4111","page":1,"rows":10,"service":"本市和市郊通勤旅客运输（包括轮渡）","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":7,"limit":10,"mcc":"4119","page":1,"rows":10,"service":"救护车服务","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":8,"limit":10,"mcc":"4121","page":1,"rows":10,"service":"计程车服务","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":9,"limit":10,"mcc":"4131","page":1,"rows":10,"service":"公路客运","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"},{"id":10,"limit":10,"mcc":"4214","page":1,"rows":10,"service":"货物搬运和托运\u2014当地和长途,移动和存储公司,以及当地递送服务","tenant":"","tradeCode":"0001","tradeType":"居民服务与商务服务"}]
     * errorCode : 028
     * message : 查询成功
     * retCode : SUCCESS
     */

    private String errorCode;
    private String message;
    private String retCode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * limit : 10
         * mcc : 0742
         * page : 1
         * rows : 10
         * service : 兽医服务
         * tenant :
         * tradeCode : 0001
         * tradeType : 居民服务与商务服务
         */

        private int id;
        private int limit;
        private String mcc;
        private int page;
        private int rows;
        private String service;
        private String tenant;
        private String tradeCode;
        private String tradeType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getMcc() {
            return mcc;
        }

        public void setMcc(String mcc) {
            this.mcc = mcc;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getTradeCode() {
            return tradeCode;
        }

        public void setTradeCode(String tradeCode) {
            this.tradeCode = tradeCode;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }
    }
}
