package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-17 17:01
 * desc   :
 * version: 1.0
 */
public class DepositBean {


    /**
     * data : [{"bankCategoryNumber":"中国工商银行","bankName":"中国工商银行白城经济开发区支行","city":"白城市","id":1,"page":1,"province":"吉林省","rows":10,"tenant":"","unionPayNumber":"102247000204"},{"bankCategoryNumber":"中国工商银行","bankName":"中国工商银行白城平台分理处","city":"白城市","id":2,"page":1,"province":"吉林省","rows":10,"tenant":"","unionPayNumber":"102247000237"},{"bankCategoryNumber":"中国工商银行","bankName":"中国工商银行白城洮南支行","city":"白城市","id":3,"page":1,"province":"吉林省","rows":10,"tenant":"","unionPayNumber":"102247200011"},{"bankCategoryNumber":"中国工商银行","bankName":"中国工商银行白城洮南光明分理处","city":"白城市","id":4,"page":1,"province":"吉林省","rows":10,"tenant":"","unionPayNumber":"102247200038"}]
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
         * bankCategoryNumber : 中国工商银行
         * bankName : 中国工商银行白城经济开发区支行
         * city : 白城市
         * id : 1
         * page : 1
         * province : 吉林省
         * rows : 10
         * tenant :
         * unionPayNumber : 102247000204
         */

        private String bankCategoryNumber;
        private String bankName;
        private String city;
        private int id;
        private int page;
        private String province;
        private int rows;
        private String tenant;
        private String unionPayNumber;

        public String getBankCategoryNumber() {
            return bankCategoryNumber;
        }

        public void setBankCategoryNumber(String bankCategoryNumber) {
            this.bankCategoryNumber = bankCategoryNumber;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getUnionPayNumber() {
            return unionPayNumber;
        }

        public void setUnionPayNumber(String unionPayNumber) {
            this.unionPayNumber = unionPayNumber;
        }
    }
}
