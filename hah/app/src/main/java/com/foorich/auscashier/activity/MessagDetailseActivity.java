package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.MessageListAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.MessageListBean;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
 * date   : 2019-4-28  09:32
 * desc   : 消息详情页面
 * version: 1.0
 */
public class MessagDetailseActivity extends BaseActivity {


    //返回按钮
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    //消息列表
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    MessageListAdapter adapter;

    String URL;
    String Title;
    String type;
    String title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_messag_detailse;
    }

    @Override
    public void initPresenter() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        Intent intent = this.getIntent();
        //0/1
        type = intent.getStringExtra("type");
        //0/1
        title = intent.getStringExtra("title");
        mToolbarTitle.setText(title);
        int messageType;
        messageType = Integer.parseInt(type);
        if (messageType == 0) {
            initMessage(type);
        } else {
            initMessage(type);
        }
    }

    @Override
    public void initView() {
        rvMessage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mToolbarLeft.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }


    //消息推送0->系统公告 1->交易推送
    public void initMessage(String messageType) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            int page = 1;
            int rows = 1000;

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Integer.valueOf(BaseApplication.getuserId()));
            jsonObject.put("messageType", messageType);
            jsonObject.put("page", page);
            jsonObject.put("rows", rows);
            jsonObject.put("usertype", "1");


            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("messageType", messageType);
            map.put("page", String.valueOf(page));
            map.put("rows", String.valueOf(rows));
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
        HttpManager.getHttpManager().postMethod(ApiService.getMessage, new Observer<String>() {
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
                        MessageListBean messageListBean = gson.fromJson(json, MessageListBean.class);
                        if (messageListBean.getRetCode().equals("SUCCESS")) {
                            final List<MessageListBean.DataBean.MessageArrayBean> messageArrayBeans = messageListBean.getData().getMessageArray();
                            adapter = new MessageListAdapter(messageArrayBeans, MessagDetailseActivity.this);
                            rvMessage.setAdapter(adapter);
                            adapter.setOnItemClickListener(new MessageListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    //设置已读状态
                                    initUpdateFlag(messageArrayBeans.get(position).getMessageId());
                                    URL = messageArrayBeans.get(position).getUrl();//URL
                                    Title =messageArrayBeans.get(position).getMessagefirsttitle();//标题

                                }
                            });

                        } else {
                            ToastUitl.show("消息请求失败:"+messageListBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }


    //消息推送之后点击状态更新
    public void initUpdateFlag(int messageId) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Integer.valueOf(BaseApplication.getuserId()));
            jsonObject.put("messageId", messageId);
            jsonObject.put("flag", 2);


            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("messageId", String.valueOf(messageId));
            map.put("flag", String.valueOf(2));
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
        HttpManager.getHttpManager().postMethod(ApiService.updateFlag, new Observer<String>() {
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
                        CommonalityBean commonalityBean = gson.fromJson(json, CommonalityBean.class);
                        if (commonalityBean.getRetCode().equals("SUCCESS")) {
                            Intent intent = new Intent(MessagDetailseActivity.this, WebActivity.class);
                            intent.putExtra("url", URL);//URL
                            intent.putExtra("title", Title);//标题
                            intent.putExtra("type", title);//标题
                            startActivity(intent);
                        } else {

                        }
                    }
                }, body
        );
    }


    @OnClick({R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://返回
                // 广播通知
                Intent in = new Intent();
                in.setAction("action.refresh");
                sendBroadcast(in);
                AppManager.getAppManager().returnToActivity(MessageActivity.class);

                break;
        }


    }


    // 接收广播消息
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                initMessage(type);
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            // 广播通知
            Intent in = new Intent();
            in.setAction("action.refresh");
            sendBroadcast(in);
            AppManager.getAppManager().returnToActivity(MessageActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
