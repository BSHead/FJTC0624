package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-21 9:38
 * desc   : 商户收款码
 * version: 1.0
 */
public class GatheringCodeBean {


    /**
     * data : {"id":52,"limit":10,"merchantNo":"959416992596287","oneYardPayLink":"bbf3e83474d54253a56513840901b7d0","orgcode":"89f89bec-11b3-4ced-997e-4b20cf8f776c","page":1,"rows":10,"storeCode":"074603966328574","tenant":""}
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
         * id : 52
         * limit : 10
         * merchantNo : 959416992596287
         * oneYardPayLink : bbf3e83474d54253a56513840901b7d0
         * orgcode : 89f89bec-11b3-4ced-997e-4b20cf8f776c
         * page : 1
         * rows : 10
         * storeCode : 074603966328574
         * tenant :
         */

        private int id;
        private int limit;
        private String merchantNo;
        private String oneYardPayLink;
        private String orgcode;
        private int page;
        private int rows;
        private String storeCode;
        private String tenant;

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

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getOneYardPayLink() {
            return oneYardPayLink;
        }

        public void setOneYardPayLink(String oneYardPayLink) {
            this.oneYardPayLink = oneYardPayLink;
        }

        public String getOrgcode() {
            return orgcode;
        }

        public void setOrgcode(String orgcode) {
            this.orgcode = orgcode;
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

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }
    }
}
