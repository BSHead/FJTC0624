package com.foorich.auscashier.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.LoginBean;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
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
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-06 18:00
 * desc   : 设置注册登录密码页面
 * version: 1.0
 */
public class SetRegisterPdActivity extends BaseActivity {


    //输入密码框
    @BindView(R.id.ed_set_new_password)
    MClearEditText mNewPd;
    //确认密码
    @BindView(R.id.ed_affirm_new_password)
    MClearEditText mAffirmNewPd;
    //下一步
    @BindView(R.id.btn_set_pd)
    Button mSetPd;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //手机号
    String phone;
    //弹框
    private AlertDialog myDialog;
    LoginBean loginBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_password;
    }

    @Override
    public void initPresenter() {
        myDialog = new AlertDialog(this).builder();
        Intent intent = this.getIntent();
        phone = intent.getStringExtra("phone");
    }

    @Override
    public void initView() {
        mToolbarLeft.setVisibility(View.VISIBLE);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left, R.id.btn_set_pd})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.btn_set_pd:
                if (mNewPd.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入新密码", 0);
                } else if (mAffirmNewPd.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入确认密码", 0);
                } else {
                    if (mNewPd.getText().toString().trim().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$") && mAffirmNewPd.getText().toString().trim().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
                        if (mNewPd.getText().toString().trim().equals(mAffirmNewPd.getText().toString().trim())) {
                            Register(mNewPd.getText().toString().trim());
                        } else {
                            ToastUitl.show("两次密码不一样", 0);
                        }
                    } else {
                        ToastUitl.show("6到12个字符，含字母、数字、符号至少两种", 1);
                    }
                }
                break;
        }
    }

    //设置登录密码
    public void Register(String password) {
        SuccinctProgress.showSuccinctProgress(SetRegisterPdActivity.this,
                "注册中···", SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //加密密码
            EncrypMD5 md5pd = new EncrypMD5();
            byte[] resultBytesPd = md5pd.eccrypt(password);
            String PD = EncrypMD5.hexString(resultBytesPd);
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginName", phone);
            jsonObject.put("osType", "1");
            jsonObject.put("appType", "B");
            jsonObject.put("password", PD);
            jsonObject.put("key", "1102130405061708");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("loginName", phone);
            map.put("osType", "1");
            map.put("password", PD);
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
        HttpManager.getHttpManager().postMethod(ApiService.Register, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SuccinctProgress.dismiss();
                        LogUtil.e("设置注册登录密码页面", "错误提示：" + e.getMessage());
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

                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();

                        loginBean = gson.fromJson(json, LoginBean.class);
                        if (loginBean.getRetCode().equals("SUCCESS")) {
                            if (loginBean.getRetCode().equals("SUCCESS")) {
                                final String type="B_";
                                final String toast=loginBean.getData().getLoginName() + "_"+loginBean.getData().getToken()+"_XFB";
                                JPushInterface.setAlias(SetRegisterPdActivity.this, type + toast,
                                        new TagAliasCallback() {
                                            @Override
                                            public void gotResult(int responseCode,
                                                                  String alias, Set<String> tags) {
                                                Log.e("responseCode", "+++responseCode++++" + responseCode);
                                                if (responseCode == 0) {
                                                    ToastUitl.show(type + toast, 0);
                                                } else if (responseCode == 6002) {
                                                    Message obtain = Message.obtain();
                                                    obtain.what = 100;
                                                    mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
                                                    ToastUitl.show(type + toast, 0);
                                                } else {
                                                    Message obtain = Message.obtain();
                                                    obtain.what = 100;
                                                    mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
                                                    ToastUitl.show(type + toast, 0);
                                                }
                                            }
                                        });
                                BaseApplication.saveloginName(loginBean.getData().getLoginName());//保存用户名
                                BaseApplication.saveloginPass(mNewPd.getText().toString().trim());//保存用户密码
                                BaseApplication.saveheadImgUrl(loginBean.getData().getHeadImgUrl());//保存用户头像
                                BaseApplication.savetoken(loginBean.getData().getToken());//保存token
                                BaseApplication.savemerchantCode(loginBean.getData().getMerchantCode());//保存商户号
                                BaseApplication.savemerchantName(loginBean.getData().getMerchantName());//保存商户名称
                                BaseApplication.savelicense(loginBean.getData().getLicense());//保存营业执照号
                                BaseApplication.saveprovinceCode(loginBean.getData().getProvinceCode());//保存营业地址
                                BaseApplication.saveuserId(String.valueOf(loginBean.getData().getId()));//保存id
                                BaseApplication.saveostype(String.valueOf(loginBean.getData().getOsType()));//保存id
                                BaseApplication.saveexistpaypwd(String.valueOf(loginBean.getData().isB_existPayPwd()));//保存支付密码
                                BaseApplication.savestatus(loginBean.getData().getStatus());//保存商户状态
                                BaseApplication.savestoreCode(loginBean.getData().getStoreCode());//保存商户状态
                                BaseApplication.saveAccountCode(loginBean.getData().getAccountCode());//保存提现银行卡号
                                BaseApplication.saveNickName(loginBean.getData().getNickName());//保存用户昵称
                                BaseApplication.saveoneYardPayLink("");//设置商户收款码为空值
                                myDialog.setGone().setTitle("注册成功").setCancelable(false).setNegativeButton("知道了", R.color.orange, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(MainActivity.class);
                                        AppManager.getAppManager().finishAllActivity();
                                    }
                                }).show();
                            }
                        } else {

                            ToastUitl.show(loginBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                JPushInterface.setAlias(getApplicationContext(), 1, loginBean.getData().getLoginName());
            }
        }
    };
}
