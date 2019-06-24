package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 20:03
 * desc   : 登录实体类
 * version: 1.0
 */
public class LoginBean {


    /**
     * retCode : SUCCESS
     * errorCode : null
     * message : null
     * data : {"id":255,"loginName":"18201097621","nickName":null,"phone":"18201097621","merchantcode":null,"receivalesQrCode":null,"existPayPwd":false,"verified":"","isfrozen":false,"token":"25520190605201551","osType":"2","merchantName":null,"createTime":null,"merchantCode":null,"storeName":null,"storeCode":null,"status":null,"headImgUrl":null,"license":null,"provinceCode":null,"accountCode":null,"customer_telephone":"400-800-8090","c_headimgUrl":"","accountId":"255","gender":"","dateOfBirth":"","b_existPayPwd":false,"c_nickName":"182****7621","balanceCardNum":null,"whetherBankCard":null,"c_versionNumber":null,"b_versionNumber":null}
     */

    private String retCode;
    private Object errorCode;
    private String message;
    private DataBean data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public String  getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 255
         * loginName : 18201097621
         * nickName : null
         * phone : 18201097621
         * merchantcode : null
         * receivalesQrCode : null
         * existPayPwd : false
         * verified :
         * isfrozen : false
         * token : 25520190605201551
         * osType : 2
         * merchantName : null
         * createTime : null
         * merchantCode : null
         * storeName : null
         * storeCode : null
         * status : null
         * headImgUrl : null
         * license : null
         * provinceCode : null
         * accountCode : null
         * customer_telephone : 400-800-8090
         * c_headimgUrl :
         * accountId : 255
         * gender :
         * dateOfBirth :
         * b_existPayPwd : false
         * c_nickName : 182****7621
         * balanceCardNum : null
         * whetherBankCard : null
         * c_versionNumber : null
         * b_versionNumber : null
         */

        private int id;
        private String loginName;
        private String nickName;
        private String phone;
        private Object merchantcode;
        private Object receivalesQrCode;
        private boolean existPayPwd;
        private String verified;
        private boolean isfrozen;
        private String token;
        private String osType;
        private String merchantName;
        private Object createTime;
        private String merchantCode;
        private Object storeName;
        private String storeCode;
        private String status;
        private String headImgUrl;
        private String license;
        private String provinceCode;
        private String accountCode;
        private String customer_telephone;
        private String c_headimgUrl;
        private String accountId;
        private String gender;
        private String dateOfBirth;
        private boolean b_existPayPwd;
        private String c_nickName;
        private Object balanceCardNum;
        private Object whetherBankCard;
        private Object c_versionNumber;
        private Object b_versionNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getMerchantcode() {
            return merchantcode;
        }

        public void setMerchantcode(Object merchantcode) {
            this.merchantcode = merchantcode;
        }

        public Object getReceivalesQrCode() {
            return receivalesQrCode;
        }

        public void setReceivalesQrCode(Object receivalesQrCode) {
            this.receivalesQrCode = receivalesQrCode;
        }

        public boolean isExistPayPwd() {
            return existPayPwd;
        }

        public void setExistPayPwd(boolean existPayPwd) {
            this.existPayPwd = existPayPwd;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public boolean isIsfrozen() {
            return isfrozen;
        }

        public void setIsfrozen(boolean isfrozen) {
            this.isfrozen = isfrozen;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOsType() {
            return osType;
        }

        public void setOsType(String osType) {
            this.osType = osType;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public Object getStoreName() {
            return storeName;
        }

        public void setStoreName(Object storeName) {
            this.storeName = storeName;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String  getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public String getCustomer_telephone() {
            return customer_telephone;
        }

        public void setCustomer_telephone(String customer_telephone) {
            this.customer_telephone = customer_telephone;
        }

        public String getC_headimgUrl() {
            return c_headimgUrl;
        }

        public void setC_headimgUrl(String c_headimgUrl) {
            this.c_headimgUrl = c_headimgUrl;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public boolean isB_existPayPwd() {
            return b_existPayPwd;
        }

        public void setB_existPayPwd(boolean b_existPayPwd) {
            this.b_existPayPwd = b_existPayPwd;
        }

        public String getC_nickName() {
            return c_nickName;
        }

        public void setC_nickName(String c_nickName) {
            this.c_nickName = c_nickName;
        }

        public Object getBalanceCardNum() {
            return balanceCardNum;
        }

        public void setBalanceCardNum(Object balanceCardNum) {
            this.balanceCardNum = balanceCardNum;
        }

        public Object getWhetherBankCard() {
            return whetherBankCard;
        }

        public void setWhetherBankCard(Object whetherBankCard) {
            this.whetherBankCard = whetherBankCard;
        }

        public Object getC_versionNumber() {
            return c_versionNumber;
        }

        public void setC_versionNumber(Object c_versionNumber) {
            this.c_versionNumber = c_versionNumber;
        }

        public Object getB_versionNumber() {
            return b_versionNumber;
        }

        public void setB_versionNumber(Object b_versionNumber) {
            this.b_versionNumber = b_versionNumber;
        }
    }
}
