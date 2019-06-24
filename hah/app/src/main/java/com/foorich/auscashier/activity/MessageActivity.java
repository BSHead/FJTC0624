package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.Function;
import com.foorich.auscashier.bean.MessageList;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-07 14:14
 * desc   : 消息页面
 * version: 1.0
 */
public class MessageActivity extends BaseActivity {

    //返回按钮
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    @BindView(R.id.img_dot_system)
    ImageView mSystem;
    @BindView(R.id.img_dot_trading)
    ImageView mTrading;


    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initPresenter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void initView() {
        mToolbarLeft.setVisibility(View.VISIBLE);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        initUpdateFlag();
    }


    // 接收广播消息
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                initUpdateFlag();
            }

        }
    };

    @OnClick({R.id.toolbar_left, R.id.li_system, R.id.li_trading})
    public void Click(View view) {
        Intent intent;
        intent = new Intent(MessageActivity.this, MessagDetailseActivity.class);

        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity(MessageActivity.class);
                // 广播通知
                Intent in = new Intent();
                in.setAction("action.refresh");
                sendBroadcast(in);
                AppManager.getAppManager().returnToActivity(MainActivity.class);
                break;

            case R.id.li_system://系统交易
                intent.putExtra("type", "0");
                intent.putExtra("title", "系统公告");
                startActivity(intent);
                break;

            case R.id.li_trading://推送交易
                intent.putExtra("type", "1");
                intent.putExtra("title", "交易推送");
                startActivity(intent);
                break;

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            // 广播通知
            Intent in = new Intent();
            in.setAction("action.refresh");
            sendBroadcast(in);
            AppManager.getAppManager().returnToActivity(MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }


    public void initUpdateFlag() {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Integer.parseInt(BaseApplication.getuserId()));
            jsonObject.put("usertype", "1");

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("usertype", "1");
            map.put("key", BaseApplication.gettoken());
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(LinkStringByGet.createLinkStringByGet(map)));
            //MD5在次加密SHA1
            EncrypMD5 md5 = new EncrypMD5();
            byte[] resultBytes = md5.eccrypt(sha1);
            String md52 = EncrypMD5.hexString(resultBytes);
            //最终提交参数
            json.put("appVersion", "1.0");
            json.put("data", jsonObject);
            json.put("language", "zh");
            json.put("sign", md52);
            json.put("tenant", "platform");
            json.put("terminal", "Android");
            json.put("token", BaseApplication.gettoken());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.getMessageNum, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        MessageList messageList = gson.fromJson(json, MessageList.class);
                        if (messageList.getRetCode().equals("SUCCESS")) {
                            if (messageList.getData().getXiTongIsRead().equals("false")) {
                                mSystem.setVisibility(View.INVISIBLE);

                            } else {
                                mSystem.setVisibility(View.VISIBLE);
                            }

                            if (messageList.getData().getJiaoYiIsRead().equals("false")) {
                                mTrading.setVisibility(View.INVISIBLE);
                            } else {
                                mTrading.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }
                }, body
        );
    }
}
