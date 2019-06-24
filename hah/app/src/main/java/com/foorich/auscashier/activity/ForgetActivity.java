package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
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
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-10 14:36
 * desc   : 忘记密码页面
 * version: 1.0
 */
public class ForgetActivity extends BaseActivity {


    //上下文
    Activity activity;
    //手机号
    @BindView(R.id.et_fg_phone)
    MClearEditText mPhone;
    //发送验证码
    @BindView(R.id.et_fg_verification)
    MClearEditText mVerification;
    //下一步
    @BindView(R.id.btn_fg_next)
    Button mNext;
    //验证码
    @BindView(R.id.tv_fg_verification)
    TextView mForgetAuth;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView Toolbar_left;


    @Override
    public int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        activity = this;
        Toolbar_left.setVisibility(View.VISIBLE);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.btn_fg_next, R.id.tv_fg_verification, R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.btn_fg_next://下一步
                if (CommonUtils.isMobile(mPhone.getText().toString().trim())) {
                    Checkmessage(mPhone.getText().toString().trim(), mVerification.getText().toString().trim());
                } else if (mVerification.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入验证码", 0);
                } else {
                    ToastUitl.show("手机号格式有误", 0);
                }
                break;

            case R.id.tv_fg_verification://发送验证码
                if (CommonUtils.isMobile(mPhone.getText().toString().trim())) {
                    //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
                    //设置Button点击事件触发倒计时
                    Mdsmssend(mPhone.getText().toString().trim());//发送验证码
                    myCountDownTimer.start();
                    ToastUitl.show("发送验证码:" + mPhone.getText().toString().trim(), 0);
                } else {
                    ToastUitl.show("请输入正确手机号", 0);
                }
                break;

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    //发送验证码
    public void Mdsmssend(String phone) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("type", "2");
            jsonObject.put("appType", "B");
            jsonObject.put("key", "1102130405061708");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", phone);
            map.put("type", "2");
            map.put("appType", "B");
            map.put("key", "1102130405061708");
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(CommonUtils.CreateLinkStringByGet(map)));
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
                        LogUtil.e("忘记密码页面", "错误提示：" + e.getMessage());
                    }

                    @Override
                    public void onNext(String json) {
                        LogUtil.e("后台发送短信验证码返回" + json);
                        Gson gson = new Gson();
//                        BankListBean bankListBean = gson.fromJson(json, BankListBean.class);
                    }
                }, body
        );
    }


    //验证短信验证码
    public void Checkmessage(String phone, String note) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("type", "2");
            jsonObject.put("code", note);
            jsonObject.put("appType", "B");
            jsonObject.put("key", "1102130405061708");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", phone);
            map.put("type", "2");
            map.put("code", note);
            map.put("appType", "B");
            map.put("key", "1102130405061708");
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(CommonUtils.CreateLinkStringByGet(map)));
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
                        LogUtil.e("忘记密码页面", "错误提示：" + e.getMessage());
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        }else if(e.getMessage().contains("Failed to connect to")){
                            ToastUitl.show("服务器异常,请重试", 0);
                        }else{
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        CommonalityBean commonalityBean = gson.fromJson(json, CommonalityBean.class);
                        if (commonalityBean.getRetCode().equals("SUCCESS")) {
                            Intent intent = new Intent(ForgetActivity.this, SetLoginPdActivity.class);
                            intent.putExtra("phone", mPhone.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            ToastUitl.show(commonalityBean.getMessage(), 0);
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
            mForgetAuth.setClickable(false);
            mForgetAuth.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            mForgetAuth.setText("重新获取");
            //设置可点击
            mForgetAuth.setClickable(true);
        }
    }
}
