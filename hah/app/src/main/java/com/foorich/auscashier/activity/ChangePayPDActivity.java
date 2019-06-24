package com.foorich.auscashier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

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
import com.foorich.auscashier.view.SeparatedEditText;
import com.foorich.auscashier.view.keyboard.KeyboardAdd;
import com.foorich.auscashier.view.keyboard.KeyboardUtils;
import com.foorich.auscashier.view.keyboard.MyKeyBoardView;
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
 * date   : 2019-3-19 14:26
 * desc   : 修改支付密码
 * version: 1.0
 */

public class ChangePayPDActivity extends BaseActivity implements View.OnClickListener {


    //返回
    @BindView(R.id.toolbar_left)
    ImageView Toolbar_left_img;
    //输入密码
    @BindView(R.id.ed_original_pay)
    SeparatedEditText mEditText;
    //自定义键盘
    @BindView(R.id.keyboard_view)
    MyKeyBoardView mKeyboardView;
    //判断密码隐藏
    boolean showContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pay_pd;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //自定义键盘显示，隐藏系统键盘
        final KeyboardAdd keyboard = new KeyboardAdd(this);
        KeyboardUtils.bindEditTextEvent(keyboard, mEditText);

        Toolbar_left_img.setOnClickListener(this);
        Toolbar_left_img.setVisibility(View.VISIBLE);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        //设置密码隐藏
        mEditText.setPassword(!showContent);

        //监听输入框数字
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
                if (mEditText.getText().toString().length() >= 6) {
                    OriginalPassword(mEditText.getText().toString().trim());
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }


    //验证原支付密码
    public void OriginalPassword(String PayPassword) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //加密密码
            EncrypMD5 md5pd = new EncrypMD5();
            byte[] resultBytesPd = md5pd.eccrypt(PayPassword);
            String PD = EncrypMD5.hexString(resultBytesPd);
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginName", BaseApplication.getloginName());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("payPassword", PD);
            jsonObject.put("PasswordType", "1");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("loginName", BaseApplication.getloginName());
            map.put("userId", BaseApplication.getuserId());
            map.put("payPassword", PD);
            map.put("PasswordType", "1");
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
        HttpManager.getHttpManager().postMethod(ApiService.chekUserPay, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("验证原支付密码页面","错误提示：" + e.getMessage());
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
                        CommonalityBean paypassword = gson.fromJson(json, CommonalityBean.class);
                        if (paypassword.getRetCode().equals("SUCCESS")) {
                            startActivity(SetPaymentPdActivity.class);
                            AppManager.getAppManager().finishActivity();
                        } else {
                            mEditText.setText("");
                            ToastUitl.show(paypassword.getMessage() , 1);
                        }
                    }
                }, body
        );
    }
}
