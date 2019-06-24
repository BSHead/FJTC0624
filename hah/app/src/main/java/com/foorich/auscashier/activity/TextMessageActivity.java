package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.MClearEditText;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-18 10:26
 * desc   : 短信验证页面
 * version: 1.0
 */
public class TextMessageActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    @BindView(R.id.tv_message_phone)
    TextView mMessagePhone;
    @BindView(R.id.ed_message)
    MClearEditText mEdMessage;
    @BindView(R.id.tv_residue)
    TextView mResidue;
    @BindView(R.id.btn_message_next)
    TextView mbtnMessage;

    String phone;
    String name;
    String number;
    String bankNameSpecies;
    String type;//发送验证码类型

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_message;
    }

    @Override
    public void initPresenter() {
        Intent intent = this.getIntent();
        mMessagePhone.setText(intent.getStringExtra("phone"));
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        bankNameSpecies = intent.getStringExtra("bankNameSpecies");
        type = intent.getStringExtra("type");
    }

    @Override
    public void initView() {
        mToolbarLeft.setOnClickListener(this);
        mToolbarLeft.setVisibility(View.VISIBLE);
        mbtnMessage.setOnClickListener(this);
        mResidue.setOnClickListener(this);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
        myCountDownTimer.start();
        Mdsmssend(type);
        ToastUitl.show("发送验证码:" + phone, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left:
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.btn_message_next:
                if (mbtnMessage.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入验证码", 0);
                } else {
                    Checkmessage(type);//验证忘记支付密码
                }
                break;

            case R.id.tv_residue:
                MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
                myCountDownTimer.start();
                Mdsmssend(type);
                break;

        }
    }

    //发送验证码
    public void Mdsmssend(String type) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("type", type);
            jsonObject.put("appType", "C");
            jsonObject.put("key", "1102130405061708");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", phone);
            map.put("type", type);
            map.put("appType", "C");
            map.put("key", "1102130405061708");

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

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.Mdsmssend, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("短信验证页面", "错误提示：" + e.getMessage());
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

                        } else {
                            ToastUitl.show("发送验证码失败", 0);
                        }
                    }
                }, body
        );
    }


    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            mResidue.setClickable(false);
            mResidue.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            mResidue.setText("重新获取");
            //设置可点击
            mResidue.setClickable(true);
        }
    }


    //验证短信验证码1注册2找回密码3忘记支付密码
    public void Checkmessage(final String type) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("type", type);
            jsonObject.put("appType", "C");
            jsonObject.put("code", mEdMessage.getText().toString().trim());
            jsonObject.put("key", "1102130405061708");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", phone);
            map.put("type", type);
            map.put("appType", "C");
            map.put("code", mEdMessage.getText().toString().trim());
            map.put("key", "1102130405061708");

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


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.Checkmessage, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("短信验证页面", "错误提示：" + e.getMessage());
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常，请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常，请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        CommonalityBean bankListBean = gson.fromJson(json, CommonalityBean.class);
                        if (bankListBean.getRetCode().equals("SUCCESS")) {
                            if (type.equals("3")) {
                                startActivity(SetPaymentPdActivity.class);
                            }
                        } else {
                            ToastUitl.show("验证码错误", 0);
                        }
                    }
                }, body
        );
    }
}
