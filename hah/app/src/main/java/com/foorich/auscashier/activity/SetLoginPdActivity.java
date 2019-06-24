package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.utils.AlertDialog;
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
 * desc   : 设置密码页面
 * version: 1.0
 */
public class SetLoginPdActivity extends BaseActivity {


    //第一次密码
    @BindView(R.id.ed_new_password)
    MClearEditText mNewPd;
    //确认密码
    @BindView(R.id.ed_affirm_new_password)
    MClearEditText mAffirmNewPd;
    //下一步
    @BindView(R.id.btn_change_pd)
    Button mChangePd;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //弹框
    private AlertDialog myDialog;
    Activity activity;
    String password;
    String phone;


    @Override
    public int getLayoutId() {
        return R.layout.activity_set_login_pd;
    }

    @Override
    public void initPresenter() {
        myDialog = new AlertDialog(this).builder();
        Intent intent = this.getIntent();
        phone = intent.getStringExtra("phone");
    }

    @Override
    public void initView() {
        activity = this;
        mToolbarLeft.setVisibility(View.VISIBLE);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }



    //多个控件具有相同的点击事件
    @OnClick({R.id.btn_change_pd, R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.btn_change_pd:
                if (mNewPd.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入新密码", 0);
                } else if (mAffirmNewPd.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入确认密码", 0);
                } else {
                    if (mNewPd.getText().toString().trim().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$") && mAffirmNewPd.getText().toString().trim().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
                        if (mNewPd.getText().toString().trim().equals(mAffirmNewPd.getText().toString().trim())) {
                            password = mNewPd.getText().toString().trim();
                            UpdateUserPassword(password);
                        } else {
                            ToastUitl.show("两次密码不一样", 0);
                        }
                    }
                    else {
                        ToastUitl.show("6到12个字符，含字母、数字、符号至少两种", 1);
                    }
                }
                break;

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;
        }
    }


    //设置登录密码
    public void UpdateUserPassword(String password) {
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
            jsonObject.put("key", "1102130405061708");
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
        HttpManager.getHttpManager().postMethod(ApiService.UpdateUserPassword, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("设置登录密码页面", "错误提示：" + e.getMessage());
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
                            myDialog.setGone().setCancelable(false).setMsg("修改登录密码成功").setPositiveButton("确定", R.color.orange, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    BaseApplication.saveloginPass("");//保存用户密码
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("share", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isFirstRun", true);
                                    editor.commit();
                                    startActivity(LoginActivity.class);
                                    AppManager.getAppManager().finishAllActivity();
                                }
                            }).show();
                        }else{
                            ToastUitl.show(commonalityBean.getMessage(),0);
                        }
                    }
                }, body
        );
    }
}
