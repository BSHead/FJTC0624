package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-4-16 19:25
 * desc   : 上传图片后台返回实体类
 * version: 1.0
 */
public class UploadPhotoBean {


    /**
     * data : {"url":"http://192.168.5.242:8500/image//1555413719196.jpg"}
     * message : 文件上传成功
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
         * url : http://192.168.5.242:8500/image//1555413719196.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
