package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-10 11:07
 * desc   : 消息列表
 * version: 1.0
 */
public class MessageListBean {


    /**
     * data : {"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"messageArray":[{"creatTime":"2019-01-14 16:37:41","isRead":2,"message":"测试2","messageId":2,"messagefirsttitle":"系统公告一级标题","messagetype":1,"url":"https://blog.csdn.net/wo541075754/article/details/88084491","amt":1,"paytype":1,"tradestate":1},{"amt":1,"creatTime":"2019-01-14 18:03:55","isRead":2,"message":"测试12","messageId":1,"messagefirsttitle":"系统公告一级标题","messagetype":1,"paytype":1,"tradestate":1,"url":"https://blog.csdn.net/wo541075754/article/details/88084491"}],"nextPage":2,"pageNum":1,"pageSize":10,"prePage":0,"size":2,"total":2}
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
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * messageArray : [{"creatTime":"2019-01-14 16:37:41","isRead":2,"message":"测试2","messageId":2,"messagefirsttitle":"系统公告一级标题","messagetype":1,"url":"https://blog.csdn.net/wo541075754/article/details/88084491"},{"amt":1,"creatTime":"2019-01-14 18:03:55","isRead":2,"message":"测试12","messageId":1,"messagefirsttitle":"系统公告一级标题","messagetype":1,"paytype":1,"tradestate":1,"url":"https://blog.csdn.net/wo541075754/article/details/88084491"}]
         * nextPage : 2
         * pageNum : 1
         * pageSize : 10
         * prePage : 0
         * size : 2
         * total : 2
         */

        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private int nextPage;
        private int pageNum;
        private int pageSize;
        private int prePage;
        private int size;
        private int total;
        private List<MessageArrayBean> messageArray;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<MessageArrayBean> getMessageArray() {
            return messageArray;
        }

        public void setMessageArray(List<MessageArrayBean> messageArray) {
            this.messageArray = messageArray;
        }

        public static class MessageArrayBean {
            /**
             * creatTime : 2019-01-14 16:37:41
             * isRead : 2
             * message : 测试2
             * messageId : 2
             * messagefirsttitle : 系统公告一级标题
             * messagetype : 1
             * url : https://blog.csdn.net/wo541075754/article/details/88084491
             * amt : 1
             * paytype : 1
             * tradestate : 1
             */

            private String creatTime;
            private int isRead;
            private String message;
            private int messageId;
            private String messagefirsttitle;
            private int messagetype;
            private String url;
            private int amt;
            private int paytype;
            private int tradestate;

            public String getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(String creatTime) {
                this.creatTime = creatTime;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public String getMessagefirsttitle() {
                return messagefirsttitle;
            }

            public void setMessagefirsttitle(String messagefirsttitle) {
                this.messagefirsttitle = messagefirsttitle;
            }

            public int getMessagetype() {
                return messagetype;
            }

            public void setMessagetype(int messagetype) {
                this.messagetype = messagetype;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getAmt() {
                return amt;
            }

            public void setAmt(int amt) {
                this.amt = amt;
            }

            public int getPaytype() {
                return paytype;
            }

            public void setPaytype(int paytype) {
                this.paytype = paytype;
            }

            public int getTradestate() {
                return tradestate;
            }

            public void setTradestate(int tradestate) {
                this.tradestate = tradestate;
            }
        }
    }
}
