package com.foorich.auscashier.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SharedPreferencesUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:42
 * desc   : MultiDexApplication防止方法数过多
 * version: 1.0
 */


import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Config;

import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;
    private static Context mContext = null;


    //保存用户id
    public static void saveuserId(String userId) {
        SharedPreferencesUtils.setValue(mContext, "userId", userId);
    }

    //获取用户id
    public static String getuserId() {
        return SharedPreferencesUtils.getString(mContext, "userId", null);
    }

    //保存用户名
    public static void saveloginName(String loginName) {
        SharedPreferencesUtils.setValue(mContext, "loginName", loginName);
    }

    //获取用户名
    public static String getloginName() {
        return SharedPreferencesUtils.getString(mContext, "loginName", null);
    }

    //保存用户密码
    public static void saveloginPass(String loginPass) {
        SharedPreferencesUtils.setValue(mContext, "loginPass", loginPass);
    }

    //获取用户密码
    public static String getloginPass() {
        return SharedPreferencesUtils.getString(mContext, "loginPass", null);
    }

    //保存用户头像
    public static void saveheadImgUrl(String headImgUrl) {
        SharedPreferencesUtils.setValue(mContext, "headImgUrl", headImgUrl);
    }

    //获取用户头像
    public static String getheadImgUrl() {
        return SharedPreferencesUtils.getString(mContext, "headImgUrl", null);
    }

    //保存商户名称
    public static void savemerchantName(String merchantName) {
        SharedPreferencesUtils.setValue(mContext, "merchantName", merchantName);
    }

    //获取商户名称
    public static String getmerchantName() {
        return SharedPreferencesUtils.getString(mContext, "merchantName", null);
    }

    //保存商户号
    public static void savemerchantCode(String merchantCode) {
        SharedPreferencesUtils.setValue(mContext, "merchantCode", merchantCode);
    }

    //获取商户号
    public static String getmerchantCode() {
        return SharedPreferencesUtils.getString(mContext, "merchantCode", null);
    }

    //保存营业执照号
    public static void savelicense(String license) {
        SharedPreferencesUtils.setValue(mContext, "license", license);
    }

    //获取营业执照号
    public static String getlicense() {
        return SharedPreferencesUtils.getString(mContext, "license", null);
    }

    //保存营业地址
    public static void saveprovinceCode(String provinceCode) {
        SharedPreferencesUtils.setValue(mContext, "provinceCode", provinceCode);
    }

    //获取营业地址
    public static String getprovinceCode() {
        return SharedPreferencesUtils.getString(mContext, "provinceCode", null);
    }

    //保存商户手机号
//    public static void savephone(String phone) {
//        SharedPreferencesUtils.setValue(mContext, "phone", phone);
//    }
//
//    //获取商户手机号
//    public static String getphone() {
//        return SharedPreferencesUtils.getString(mContext, "phone", null);
//    }


    //保存token
    public static void savetoken(String token) {
        SharedPreferencesUtils.setValue(mContext, "token", token);
    }

    //获取token
    public static String gettoken() {
        return SharedPreferencesUtils.getString(mContext, "token", null);
    }


    //判断是否是商户
    public static void saveostype(String ostype) {
        SharedPreferencesUtils.setValue(mContext, "ostype", ostype);
    }

    //判断是否是商户
    public static String getostype() {
        return SharedPreferencesUtils.getString(mContext, "ostype", null);
    }

    //保存用户支付密码
    public static void saveexistpaypwd(String existpaypwd) {
        SharedPreferencesUtils.setValue(mContext, "existpaypwd", existpaypwd);
    }

    //获取用户支付密码
    public static String getexistpaypwd() {
        return SharedPreferencesUtils.getString(mContext, "existpaypwd", null);
    }

    //保存商户状态
    public static void savestatus(String status) {
        SharedPreferencesUtils.setValue(mContext, "status", status);
    }

    //获取商户状态
    public static String getstatus() {
        return SharedPreferencesUtils.getString(mContext, "status", null);
    }

    //保存门店号
    public static void savestoreCode(String status) {
        SharedPreferencesUtils.setValue(mContext, "storeCode", status);
    }

    //获取门店号
    public static String getstoreCode() {
        return SharedPreferencesUtils.getString(mContext, "storeCode", null);
    }

    //保存商户静态码
    public static void saveoneYardPayLink(String oneYardPayLink) {
        SharedPreferencesUtils.setValue(mContext, "oneYardPayLink", oneYardPayLink);
    }

    //获取商户静态码
    public static String getoneYardPayLink() {
        return SharedPreferencesUtils.getString(mContext, "oneYardPayLink", null);
    }
    //保存提现银行卡
    public static void saveAccountCode(String accountCode) {
        SharedPreferencesUtils.setValue(mContext, "accountCode", accountCode);
    }

    //获取提现银行卡
    public static String getAccountCode() {
        return SharedPreferencesUtils.getString(mContext, "accountCode", null);
    }

    //保存用户昵称
    public static void saveNickName(String nickName) {
        SharedPreferencesUtils.setValue(mContext, "nickName", nickName);
    }

    //获取用户昵称
    public static String getnickName() {
        return SharedPreferencesUtils.getString(mContext, "nickName", null);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
        //Log开关
        LogUtil.init(LogUtil.VERBOSE, "XFB");
        mContext = getApplicationContext();


        //极光推送
        JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);//初始化

//        Config.DEBUG = true;
        UMShareAPI.get(this);

        //友盟分享
        UMConfigure.init(this, "55bee0eae0f55a100700180a"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        //微信分享wx8dfac1599cf8894d
        PlatformConfig.setWeixin("wx307a1b9f0ed90ade", "bjjrztkjyxgs201611281058dbwxl8hh");
        //微博分享
        PlatformConfig.setSinaWeibo("3586265609", "4b875439f7ff903353b61fe813efa43b", "http://sns.whalecloud.com");
        //qq分享
        PlatformConfig.setQQZone("1104433936", "S5SLSYkU4KKo3yut");

    }

    public static Context getContext() {
        return mContext;
    }


    public static Context getAppContext() {
        return baseApplication;
    }
}
