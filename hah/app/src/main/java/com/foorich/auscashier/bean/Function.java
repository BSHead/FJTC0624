package com.foorich.auscashier.bean;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 18:38
 * desc   :
 * version: 1.0
 */
public class Function {


    private String title;
    private int imageId;
    private String version;

    public Function(String title, int imageId, String version) {
        this.title = title;
        this.imageId = imageId;
        this.version = version;
    }

    public Function(String title, String version) {
        this.title = title;
        this.imageId = imageId;
        this.version = version;
    }

    public Function(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public Function(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
