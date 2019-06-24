package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-22 10:32
 * desc   :
 * version: 1.0
 */
public class CaptureBean {


    /**
     * data : {"agentId":1,"agentId2":0,"agentId3":0,"agentId4":0,"agentId5":0,"agentName":"祥付宝代理商","agentProfit":0,"agentProfit2":0,"agentProfit3":0,"agentProfit4":0,"agentProfit5":0,"agentrate":0,"agentrate2":0,"agentrate3":0,"agentrate4":0,"agentrate5":0,"amt":600,"amtt":0,"cardNum":"577538276160700416","channelCode":"BALANCE","channelPayTypeId":75,"code":"837004168540786432","costPoundage":0,"costRate":0,"countCard":0,"coupon":0,"createTime":1558492211654,"divideType":0,"gradeType":0,"id":1052121,"isUnfreezeResidualAmount":0,"merchantName":"123","merchantNo":"897641045093803","modifyTime":1558492211654,"oneCardAmt":0,"orderFee":0,"orderNum":"580703954229264384","page":1,"payType":2,"poundage":0,"profit":0,"profitRate":0,"profitRate2":0,"profitRate3":0,"profitRate4":0,"profitRate5":0,"profitsetId":0,"reconciliation":0,"refundableAmount":600,"remarks":"暂无","requestType":1,"rows":10,"salesmanId":0,"score":0,"sendWay":0,"serviceFlg":0,"settleNo":0,"settlement":0,"storeCode":"074603966328574","storeName":"123总店","tenant":"","tenantSource":"platform","termianlSN":"","tradeState":9,"userLogin":"295"}
     * retCode : SUCCESS
     */

    private DataBean data;
    private String retCode;
    private String message;

    public String getMessage() {
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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class DataBean {
        /**
         * agentId : 1
         * agentId2 : 0
         * agentId3 : 0
         * agentId4 : 0
         * agentId5 : 0
         * agentName : 祥付宝代理商
         * agentProfit : 0
         * agentProfit2 : 0
         * agentProfit3 : 0
         * agentProfit4 : 0
         * agentProfit5 : 0
         * agentrate : 0.0
         * agentrate2 : 0.0
         * agentrate3 : 0.0
         * agentrate4 : 0.0
         * agentrate5 : 0.0
         * amt : 600
         * amtt : 0.0
         * cardNum : 577538276160700416
         * channelCode : BALANCE
         * channelPayTypeId : 75
         * code : 837004168540786432
         * costPoundage : 0
         * costRate : 0.0
         * countCard : 0
         * coupon : 0
         * createTime : 1558492211654
         * divideType : 0
         * gradeType : 0
         * id : 1052121
         * isUnfreezeResidualAmount : 0
         * merchantName : 123
         * merchantNo : 897641045093803
         * modifyTime : 1558492211654
         * oneCardAmt : 0
         * orderFee : 0
         * orderNum : 580703954229264384
         * page : 1
         * payType : 2
         * poundage : 0.0
         * profit : 0
         * profitRate : 0.0
         * profitRate2 : 0.0
         * profitRate3 : 0.0
         * profitRate4 : 0.0
         * profitRate5 : 0.0
         * profitsetId : 0
         * reconciliation : 0
         * refundableAmount : 600
         * remarks : 暂无
         * requestType : 1
         * rows : 10
         * salesmanId : 0
         * score : 0
         * sendWay : 0
         * serviceFlg : 0
         * settleNo : 0
         * settlement : 0
         * storeCode : 074603966328574
         * storeName : 123总店
         * tenant :
         * tenantSource : platform
         * termianlSN :
         * tradeState : 9
         * userLogin : 295
         */

        private int agentId;
        private int agentId2;
        private int agentId3;
        private int agentId4;
        private int agentId5;
        private String agentName;
        private int agentProfit;
        private int agentProfit2;
        private int agentProfit3;
        private int agentProfit4;
        private int agentProfit5;
        private double agentrate;
        private double agentrate2;
        private double agentrate3;
        private double agentrate4;
        private double agentrate5;
        private int amt;
        private double amtt;
        private String cardNum;
        private String channelCode;
        private int channelPayTypeId;
        private String code;
        private int costPoundage;
        private double costRate;
        private int countCard;
        private int coupon;
        private long createTime;
        private int divideType;
        private int gradeType;
        private int id;
        private int isUnfreezeResidualAmount;
        private String merchantName;
        private String merchantNo;
        private long modifyTime;
        private int oneCardAmt;
        private int orderFee;
        private String orderNum;
        private int page;
        private int payType;
        private double poundage;
        private int profit;
        private double profitRate;
        private double profitRate2;
        private double profitRate3;
        private double profitRate4;
        private double profitRate5;
        private int profitsetId;
        private int reconciliation;
        private int refundableAmount;
        private String remarks;
        private int requestType;
        private int rows;
        private int salesmanId;
        private int score;
        private int sendWay;
        private int serviceFlg;
        private int settleNo;
        private int settlement;
        private String storeCode;
        private String storeName;
        private String tenant;
        private String tenantSource;
        private String termianlSN;
        private int tradeState;
        private String userLogin;

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getAgentId2() {
            return agentId2;
        }

        public void setAgentId2(int agentId2) {
            this.agentId2 = agentId2;
        }

        public int getAgentId3() {
            return agentId3;
        }

        public void setAgentId3(int agentId3) {
            this.agentId3 = agentId3;
        }

        public int getAgentId4() {
            return agentId4;
        }

        public void setAgentId4(int agentId4) {
            this.agentId4 = agentId4;
        }

        public int getAgentId5() {
            return agentId5;
        }

        public void setAgentId5(int agentId5) {
            this.agentId5 = agentId5;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getAgentProfit() {
            return agentProfit;
        }

        public void setAgentProfit(int agentProfit) {
            this.agentProfit = agentProfit;
        }

        public int getAgentProfit2() {
            return agentProfit2;
        }

        public void setAgentProfit2(int agentProfit2) {
            this.agentProfit2 = agentProfit2;
        }

        public int getAgentProfit3() {
            return agentProfit3;
        }

        public void setAgentProfit3(int agentProfit3) {
            this.agentProfit3 = agentProfit3;
        }

        public int getAgentProfit4() {
            return agentProfit4;
        }

        public void setAgentProfit4(int agentProfit4) {
            this.agentProfit4 = agentProfit4;
        }

        public int getAgentProfit5() {
            return agentProfit5;
        }

        public void setAgentProfit5(int agentProfit5) {
            this.agentProfit5 = agentProfit5;
        }

        public double getAgentrate() {
            return agentrate;
        }

        public void setAgentrate(double agentrate) {
            this.agentrate = agentrate;
        }

        public double getAgentrate2() {
            return agentrate2;
        }

        public void setAgentrate2(double agentrate2) {
            this.agentrate2 = agentrate2;
        }

        public double getAgentrate3() {
            return agentrate3;
        }

        public void setAgentrate3(double agentrate3) {
            this.agentrate3 = agentrate3;
        }

        public double getAgentrate4() {
            return agentrate4;
        }

        public void setAgentrate4(double agentrate4) {
            this.agentrate4 = agentrate4;
        }

        public double getAgentrate5() {
            return agentrate5;
        }

        public void setAgentrate5(double agentrate5) {
            this.agentrate5 = agentrate5;
        }

        public int getAmt() {
            return amt;
        }

        public void setAmt(int amt) {
            this.amt = amt;
        }

        public double getAmtt() {
            return amtt;
        }

        public void setAmtt(double amtt) {
            this.amtt = amtt;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public int getChannelPayTypeId() {
            return channelPayTypeId;
        }

        public void setChannelPayTypeId(int channelPayTypeId) {
            this.channelPayTypeId = channelPayTypeId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getCostPoundage() {
            return costPoundage;
        }

        public void setCostPoundage(int costPoundage) {
            this.costPoundage = costPoundage;
        }

        public double getCostRate() {
            return costRate;
        }

        public void setCostRate(double costRate) {
            this.costRate = costRate;
        }

        public int getCountCard() {
            return countCard;
        }

        public void setCountCard(int countCard) {
            this.countCard = countCard;
        }

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDivideType() {
            return divideType;
        }

        public void setDivideType(int divideType) {
            this.divideType = divideType;
        }

        public int getGradeType() {
            return gradeType;
        }

        public void setGradeType(int gradeType) {
            this.gradeType = gradeType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsUnfreezeResidualAmount() {
            return isUnfreezeResidualAmount;
        }

        public void setIsUnfreezeResidualAmount(int isUnfreezeResidualAmount) {
            this.isUnfreezeResidualAmount = isUnfreezeResidualAmount;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getOneCardAmt() {
            return oneCardAmt;
        }

        public void setOneCardAmt(int oneCardAmt) {
            this.oneCardAmt = oneCardAmt;
        }

        public int getOrderFee() {
            return orderFee;
        }

        public void setOrderFee(int orderFee) {
            this.orderFee = orderFee;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public double getPoundage() {
            return poundage;
        }

        public void setPoundage(double poundage) {
            this.poundage = poundage;
        }

        public int getProfit() {
            return profit;
        }

        public void setProfit(int profit) {
            this.profit = profit;
        }

        public double getProfitRate() {
            return profitRate;
        }

        public void setProfitRate(double profitRate) {
            this.profitRate = profitRate;
        }

        public double getProfitRate2() {
            return profitRate2;
        }

        public void setProfitRate2(double profitRate2) {
            this.profitRate2 = profitRate2;
        }

        public double getProfitRate3() {
            return profitRate3;
        }

        public void setProfitRate3(double profitRate3) {
            this.profitRate3 = profitRate3;
        }

        public double getProfitRate4() {
            return profitRate4;
        }

        public void setProfitRate4(double profitRate4) {
            this.profitRate4 = profitRate4;
        }

        public double getProfitRate5() {
            return profitRate5;
        }

        public void setProfitRate5(double profitRate5) {
            this.profitRate5 = profitRate5;
        }

        public int getProfitsetId() {
            return profitsetId;
        }

        public void setProfitsetId(int profitsetId) {
            this.profitsetId = profitsetId;
        }

        public int getReconciliation() {
            return reconciliation;
        }

        public void setReconciliation(int reconciliation) {
            this.reconciliation = reconciliation;
        }

        public int getRefundableAmount() {
            return refundableAmount;
        }

        public void setRefundableAmount(int refundableAmount) {
            this.refundableAmount = refundableAmount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getRequestType() {
            return requestType;
        }

        public void setRequestType(int requestType) {
            this.requestType = requestType;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getSalesmanId() {
            return salesmanId;
        }

        public void setSalesmanId(int salesmanId) {
            this.salesmanId = salesmanId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSendWay() {
            return sendWay;
        }

        public void setSendWay(int sendWay) {
            this.sendWay = sendWay;
        }

        public int getServiceFlg() {
            return serviceFlg;
        }

        public void setServiceFlg(int serviceFlg) {
            this.serviceFlg = serviceFlg;
        }

        public int getSettleNo() {
            return settleNo;
        }

        public void setSettleNo(int settleNo) {
            this.settleNo = settleNo;
        }

        public int getSettlement() {
            return settlement;
        }

        public void setSettlement(int settlement) {
            this.settlement = settlement;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getTenantSource() {
            return tenantSource;
        }

        public void setTenantSource(String tenantSource) {
            this.tenantSource = tenantSource;
        }

        public String getTermianlSN() {
            return termianlSN;
        }

        public void setTermianlSN(String termianlSN) {
            this.termianlSN = termianlSN;
        }

        public int getTradeState() {
            return tradeState;
        }

        public void setTradeState(int tradeState) {
            this.tradeState = tradeState;
        }

        public String getUserLogin() {
            return userLogin;
        }

        public void setUserLogin(String userLogin) {
            this.userLogin = userLogin;
        }
    }
}
