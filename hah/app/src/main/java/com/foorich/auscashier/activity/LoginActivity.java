package com.foorich.auscashier.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.LoginBean;
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
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.service.JPushMessageReceiver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 14:25
 * desc   : 登录页面
 * version: 1.0
 */
public class LoginActivity extends BaseActivity {

    //上下文
    Activity activity;
    //输入手机号
    @BindView(R.id.et_phone)
    MClearEditText mEdPhone;
    //输入密码
    @BindView(R.id.et_pd)
    MClearEditText mEdPassword;
    //登录
    @BindView(R.id.btn_login)
    Button Btn_login;
    //注册
    @BindView(R.id.btn_register)
    TextView Tv_register;
    //忘记密码
    @BindView(R.id.tv_forget_pd)
    TextView Tv_fortget;

    String phone;//输入框手机号
    String password;//输入框密码
    LoginBean loginBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //记录登录状态
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //如果是true第一次登录否则直接登录
        if (isFirstRun) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            //验证手机号和密码
        } else if (BaseApplication.getloginName() == null || BaseApplication.getloginName().equals("") && BaseApplication.getloginPass() == null || BaseApplication.getloginPass().equals("")) {
            //直接登录
        } else {
            startActivity(MainActivity.class);
            AppManager.getAppManager().finishActivity(LoginActivity.class);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        activity = this;
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.btn_login, R.id.btn_register, R.id.tv_forget_pd})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.btn_login://登录
                phone = mEdPhone.getText().toString().trim();
                password = mEdPassword.getText().toString().trim();
                //校验用户名密码
                if (phone.equals("")) {
                    ToastUitl.show("手机号不能为空", 0);
                } else if (password.equals("")) {
                    ToastUitl.show("密码不能为空", 0);
                } else {
                    //正则表达式（校验手机号是否正确）
                    if (CommonUtils.isMobile(phone)) {//校验手机号
                        SuccinctProgress.showSuccinctProgress(LoginActivity.this,
                                getString(R.string.loginlode), SuccinctProgress.THEME_ARC, false, true);
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
                            jsonObject.put("password", PD);
                            jsonObject.put("appType", "B");
                            //拆分data里面key、value进行拼接
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("loginName", phone);
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
                        HttpManager.getHttpManager().postMethod(ApiService.Login, new Observer<String>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        SuccinctProgress.dismiss();
                                        LogUtil.e("登录页面", "错误提示：" + e.getMessage());
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
                                            final String type="B_";
                                            final String toast=loginBean.getData().getLoginName() + "_"+loginBean.getData().getToken()+"_XFB";
                                            JPushInterface.setAlias(LoginActivity.this, type + toast,
                                                    new TagAliasCallback() {
                                                        @Override
                                                        public void gotResult(int responseCode,
                                                                              String alias, Set<String> tags) {
                                                            Log.e("responseCode", "+++responseCode++++" + responseCode);
                                                            if (responseCode == 0) {
//                                                                ToastUitl.show(type + toast, 0);
                                                            } else if (responseCode == 6002) {
                                                                Message obtain = Message.obtain();
                                                                obtain.what = 100;
                                                                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
//                                                                ToastUitl.show(type + toast, 0);
                                                            } else {
                                                                Message obtain = Message.obtain();
                                                                obtain.what = 100;
                                                                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
//                                                                ToastUitl.show(type + toast, 0);
                                                            }
                                                        }
                                                    });
                                            BaseApplication.saveloginName(loginBean.getData().getLoginName());//保存用户名
                                            BaseApplication.saveloginPass(mEdPassword.getText().toString().trim());//保存用户密码
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

                                            startActivity(MainActivity.class);
                                            AppManager.getAppManager().finishActivity();//销毁当前activity
                                        } else {
                                            SuccinctProgress.dismiss();
                                            ToastUitl.show(loginBean.getMessage(), 0);
                                        }
                                    }
                                }, body
                        );
                    } else {
                        ToastUitl.show("手机号不合法", 0);
                    }
                }
                break;

            case R.id.btn_register://注册
                startActivity(RegisterActivity.class);
                break;

            case R.id.tv_forget_pd://忘记密码
                startActivity(ForgetActivity.class);
                break;
        }
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