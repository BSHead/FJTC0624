package com.foorich.auscashier.view.keyboard;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-18 20:35
 * desc   : 自定义键盘bean
 * version: 1.0
 */

public class KeyModel {

    private Integer code;
    private String lable;

    public KeyModel(Integer code, String lable) {
        this.code = code;
        this.lable = lable;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }


}
