package com.foorich.auscashier.view.zing.bean;

import java.io.Serializable;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-21 17:35
 * desc   : zxing配置类
 * version: 1.0
 */
public class ZxingConfig implements Serializable {


    /*是否播放声音*/
    private boolean isPlayBeep = true;
    /*是否震动*/
    private boolean isShake = false;
    /*是否显示下方的其他功能布局*/
    private boolean isShowbottomLayout = true;
    /*是否显示闪光灯按钮*/
    private boolean isShowFlashLight = true;
    /*是否显示相册按钮*/
    private boolean isShowAlbum = true;



    public boolean isPlayBeep() {
        return isPlayBeep;
    }

    public void setPlayBeep(boolean playBeep) {
        isPlayBeep = playBeep;
    }

    public boolean isShake() {
        return isShake;
    }

    public void setShake(boolean shake) {
        isShake = shake;
    }

    public boolean isShowbottomLayout() {
        return isShowbottomLayout;
    }

    public void setShowbottomLayout(boolean showbottomLayout) {
        isShowbottomLayout = showbottomLayout;
    }

    public boolean isShowFlashLight() {
        return isShowFlashLight;
    }

    public void setShowFlashLight(boolean showFlashLight) {
        isShowFlashLight = showFlashLight;
    }

    public boolean isShowAlbum() {
        return isShowAlbum;
    }

    public void setShowAlbum(boolean showAlbum) {
        isShowAlbum = showAlbum;
    }

}
