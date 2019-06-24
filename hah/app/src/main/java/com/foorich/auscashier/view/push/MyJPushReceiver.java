package com.foorich.auscashier.view.push;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.PushBean;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.NotifyUtil;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-15 14:19
 * desc   :
 * version: 1.0
 */
public class MyJPushReceiver extends BroadcastReceiver {

    private static final String TAG = "tuisong";
    public static String regId;
//    PushBean pushBean;
    public String type = "";


    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle bundle = intent.getExtras();

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyJPushReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                Log.d(TAG, "[MyJPushReceiver] 接收到推送下来的自定义消息(内容为): " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                String extra_json = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if (!TextUtils.isEmpty(extra_json)) {
                    Gson gson = new Gson();
                    PushBean pushBean = gson.fromJson(extra_json, PushBean.class);
                    Log.d(TAG, "[MyJPushReceiver] 后台推送：" + extra_json);

//                    if (pushBean.getApplyType().equals("C")) {
//                        if (pushBean.getApplyType().equals("04")) {//实名审核
//                            ToastUitl.show("实名审核——跳转到首页", 0);
//                        } else if (pushBean.getYeWuType().equals("05")) {//提现
//                            ToastUitl.show("提现——跳转到提现记录", 0);
//                        } else if (pushBean.getYeWuType().equals("06")) {//用户充值
//                            ToastUitl.show("充值——跳转到充值记录", 0);
//                        } else if (pushBean.getYeWuType().equals("07")) {//用户扣款
//                            ToastUitl.show("账单——跳转到账单明细", 0);
//                        } else if (pushBean.getYeWuType().equals("08")) {//用户转账
//                            ToastUitl.show("转账——跳转到转账记录", 0);
//                        } else if (pushBean.getYeWuType().equals("09")) {//系统公告
//                            ToastUitl.show("公告——跳转到系统公告", 0);
//                        }
//                    }
                }

                // 自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
                // 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyJPushReceiver] 接收到推送下来的通知：");

//                Log.d(TAG, "[MyJPushReceiver] 接收到推送下来的自定义消息(内容为): " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                Log.d(TAG, "[MyJPushReceiver] 极光官网推送: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//

                // 可以利用附加字段来区别Notication,指定不同的动作,extra_json是个json字符串
                // 通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyJPushReceiver] 用户点击打开了通知");

//                if (pushBean.getApplyType().equals("04")) {//实名审核
//                    ToastUitl.show("实名审核——跳转到首页", 0);
//                } else if (pushBean.getApplyType().equals("05")) {//提现
//                    ToastUitl.show("提现——跳转到提现记录", 0);
//                } else if (pushBean.getApplyType().equals("06")) {//用户充值
//                    ToastUitl.show("充值——跳转到充值记录", 0);
//                } else if (pushBean.getApplyType().equals("07")) {//用户扣款
//                    ToastUitl.show("账单——跳转到账单明细", 0);
//                } else if (pushBean.getApplyType().equals("08")) {//用户转账
//                    ToastUitl.show("转账——跳转到转账记录", 0);
//                } else if (pushBean.getApplyType().equals("09")) {//系统公告
//                    ToastUitl.show("公告——跳转到系统公告", 0);
//                }
                // 在这里根据 JPushInterface.EXTRA_EXTRA(附加字段) 的内容处理代码，
                // 比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyJPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyJPushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyJPushReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
