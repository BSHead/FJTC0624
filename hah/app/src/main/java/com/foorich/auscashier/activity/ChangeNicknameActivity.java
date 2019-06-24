package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
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

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-4-19 9:09
 * desc   : 修改商户昵称
 * version: 1.0
 */
public class ChangeNicknameActivity extends BaseActivity {


    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //修改用户昵称
    @BindView(R.id.ed_changenickname)
    MClearEditText mMClearEditText;
    //完成
    @BindView(R.id.btn_changenickname_next)
    Button mNext;


    @Override
    public int getLayoutId() {
        return R.layout.activity_changenickname;
    }

    @Override
    public void initPresenter() {

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
    @OnClick({R.id.toolbar_left, R.id.btn_changenickname_next})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                Intent intent = new Intent();
                intent.setAction("action.refresh");
                sendBroadcast(intent);
                AppManager.getAppManager().returnToActivity(IinformationActivity.class);
                break;

            case R.id.btn_changenickname_next://完成
                if (mMClearEditText.getText().toString().trim().equals("")) {
                    ToastUitl.show("昵称不能为空呀！", 0);
                } else {
                    SetNickName(mMClearEditText.getText().toString().trim());
                }
                break;
        }
    }


    //用户修改昵称
    public void SetNickName(String nickname) {
        {
            SuccinctProgress.showSuccinctProgress(ChangeNicknameActivity.this,
                    "修改中···", SuccinctProgress.THEME_ARC, false, true);
            //提交后台参数
            JSONObject json = new JSONObject();
            try {
                //data里面参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", BaseApplication.getuserId());
                jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
                jsonObject.put("nickname", nickname);
                jsonObject.put("distinguish", "0");
                jsonObject.put("nicknameType", "0");

                //拆分data里面key、value进行拼接cardnum
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", BaseApplication.getuserId());
                map.put("merchantCode", BaseApplication.getmerchantCode());
                map.put("nickname", nickname);
                map.put("distinguish", "0");
                map.put("nicknameType", "0");
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
            HttpManager.getHttpManager().postMethod(ApiService.nickname, new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("修改昵称页面","错误提示：" + e.getMessage());
                            if (e.getMessage().equals("connect timed out")) {
                                ToastUitl.show("网络连接超时", 0);
                            }else if(e.getMessage().contains("Failed to connect to")){
                                ToastUitl.show("服务器异常,请重试", 0);
                            }else{
                                ToastUitl.show("服务器异常,请重试", 0);
                            }
                            SuccinctProgress.dismiss();
                        }

                        @Override
                        public void onNext(String json) {
                            SuccinctProgress.dismiss();
                            Gson gson = new Gson();
                            CommonalityBean commonalityBean = gson.fromJson(json, CommonalityBean.class);
                            if (commonalityBean.getRetCode().equals("SUCCESS")) {
                                BaseApplication.saveNickName(mMClearEditText.getText().toString().trim());
                                ToastUitl.show(commonalityBean.getMessage(), 0);
                                Intent intent = new Intent();
                                intent.setAction("action.refresh");
                                sendBroadcast(intent);
                                AppManager.getAppManager().returnToActivity(IinformationActivity.class);
                            } else {
                                ToastUitl.show(commonalityBean.getMessage(), 0);
                            }
                        }
                    }, body
            );
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            Intent intent = new Intent();
            intent.setAction("action.refresh");
            sendBroadcast(intent);
            AppManager.getAppManager().returnToActivity(IinformationActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
