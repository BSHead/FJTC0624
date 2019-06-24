package com.foorich.auscashier.activity;

import android.content.Intent;
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
 * date   : 2019-3-16 16:25
 * desc   : 验证原登录密码页面
 * version: 1.0
 */
public class ChangeLoginPdActivity extends BaseActivity implements View.OnClickListener {


    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //完成
    @BindView(R.id.btn_changelogin_next)
    Button mNext;
    //密码输入
    @BindView(R.id.ed_changelogin_password)
    MClearEditText mEdChangPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_changeloginpd;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mToolbarLeft.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mToolbarLeft.setVisibility(View.VISIBLE);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //完成
            case R.id.btn_changelogin_next:
                if (mEdChangPassword.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入原密码", 0);
                } else {

                    chekUserPassword(mEdChangPassword.getText().toString().trim());
                }
                break;

            //返回
            case R.id.toolbar_left:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    //验证原密码
    public void chekUserPassword(String password) {

        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //加密密码
            EncrypMD5 md5pd = new EncrypMD5();
            byte[] resultBytesPd = md5pd.eccrypt(password);
            String PD = EncrypMD5.hexString(resultBytesPd);

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("password", PD);
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("password", PD);
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
        HttpManager.getHttpManager().postMethod(ApiService.chekUserPassword, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("验证原登录密码页面", "错误提示：" + e.getMessage());
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
                            Intent intent = new Intent(ChangeLoginPdActivity.this, SetLoginPdActivity.class);
                            intent.putExtra("phone", BaseApplication.getloginName());
                            startActivity(intent);
                        } else {
                            ToastUitl.show(commonalityBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
