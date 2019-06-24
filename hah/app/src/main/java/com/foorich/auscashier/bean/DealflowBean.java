package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-4 14:20
 * desc   : 商户流水
 * version: 1.0
 */
public class DealflowBean {


    /**
     * data : {"items":{"item0":{"countPen":14,"date":"2019-06-04","orderList":[{"amt":0.02,"channelCode":"YOP","createTime":"2019-06-04 13:48:08","leaveMessage":"","orderNum":"585464811442143232","payType":"支付宝条码","picture":"http://192.168.5.242:8500/image/7.jpg","tradeState":"退款申请中"}],"sumamt":0.27}},"page":20,"rows":1}
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
         * items : {"item0":{"countPen":14,"date":"2019-06-04","orderList":[{"amt":0.02,"channelCode":"YOP","createTime":"2019-06-04 13:48:08","leaveMessage":"","orderNum":"585464811442143232","payType":"支付宝条码","picture":"http://192.168.5.242:8500/image/7.jpg","tradeState":"退款申请中"}],"sumamt":0.27}}
         * page : 20
         * rows : 1
         */

        private ItemsBean items;
        private int page;
        private int rows;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
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

        public static class ItemsBean {
            /**
             * item0 : {"countPen":14,"date":"2019-06-04","orderList":[{"amt":0.02,"channelCode":"YOP","createTime":"2019-06-04 13:48:08","leaveMessage":"","orderNum":"585464811442143232","payType":"支付宝条码","picture":"http://192.168.5.242:8500/image/7.jpg","tradeState":"退款申请中"}],"sumamt":0.27}
             */

            private Item0Bean item0;

            public Item0Bean getItem0() {
                return item0;
            }

            public void setItem0(Item0Bean item0) {
                this.item0 = item0;
            }

            public static class Item0Bean {
                /**
                 * countPen : 14
                 * date : 2019-06-04
                 * orderList : [{"amt":0.02,"channelCode":"YOP","createTime":"2019-06-04 13:48:08","leaveMessage":"","orderNum":"585464811442143232","payType":"支付宝条码","picture":"http://192.168.5.242:8500/image/7.jpg","tradeState":"退款申请中"}]
                 * sumamt : 0.27
                 */

                private int countPen;
                private String date;
                private double sumamt;
                private List<OrderListBean> orderList;

                public int getCountPen() {
                    return countPen;
                }

                public void setCountPen(int countPen) {
                    this.countPen = countPen;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public double getSumamt() {
                    return sumamt;
                }

                public void setSumamt(double sumamt) {
                    this.sumamt = sumamt;
                }

                public List<OrderListBean> getOrderList() {
                    return orderList;
                }

                public void setOrderList(List<OrderListBean> orderList) {
                    this.orderList = orderList;
                }

                public static class OrderListBean {
                    /**
                     * amt : 0.02
                     * channelCode : YOP
                     * createTime : 2019-06-04 13:48:08
                     * leaveMessage :
                     * orderNum : 585464811442143232
                     * payType : 支付宝条码
                     * picture : http://192.168.5.242:8500/image/7.jpg
                     * tradeState : 退款申请中
                     */

                    private String amt;
                    private String channelCode;
                    private String createTime;
                    private String leaveMessage;
                    private String orderNum;
                    private String payType;
                    private String picture;
                    private String tradeState;

                    public String getAmt() {
                        return amt;
                    }

                    public void setAmt(String amt) {
                        this.amt = amt;
                    }

                    public String getChannelCode() {
                        return channelCode;
                    }

                    public void setChannelCode(String channelCode) {
                        this.channelCode = channelCode;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getLeaveMessage() {
                        return leaveMessage;
                    }

                    public void setLeaveMessage(String leaveMessage) {
                        this.leaveMessage = leaveMessage;
                    }

                    public String getOrderNum() {
                        return orderNum;
                    }

                    public void setOrderNum(String orderNum) {
                        this.orderNum = orderNum;
                    }

                    public String getPayType() {
                        return payType;
                    }

                    public void setPayType(String payType) {
                        this.payType = payType;
                    }

                    public String getPicture() {
                        return picture;
                    }

                    public void setPicture(String picture) {
                        this.picture = picture;
                    }

                    public String getTradeState() {
                        return tradeState;
                    }

                    public void setTradeState(String tradeState) {
                        this.tradeState = tradeState;
                    }
                }
            }
        }
    }
}
