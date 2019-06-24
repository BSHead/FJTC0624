package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 17:14
 * desc   : 公共通用
 * version: 1.0
 */
public class CommonalityBean {

    /**
     * retCode : SUCCESS
     * message : null
     * data : null
     */

    private String retCode;
    private String message;
    private Object data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
