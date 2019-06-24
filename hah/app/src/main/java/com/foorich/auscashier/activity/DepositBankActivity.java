package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.AllMerchantAdapter;
import com.foorich.auscashier.adapter.DepositAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.DepositBean;
import com.foorich.auscashier.bean.MerchantsTypeBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
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
 * date   : 2019-5-7 16:56
 * desc   : 开户银行页面
 * version: 1.0
 */
public class DepositBankActivity extends BaseActivity {

    Activity activity;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    //开户银行列表
    @BindView(R.id.re_deposit)
    RecyclerView ReDeposit;
    DepositAdapter adapter;
    String bankName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_deposit_bank;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        activity = this;
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("开户银行");
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        getBankList();

    }

    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                startActivity(BankCardActivity.class);
                AppManager.getAppManager().finishActivity();
                break;

        }
    }


    //获取开户支行
    public void getBankList() {
//        SuccinctProgress.showSuccinctProgress(activity,
//                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("key", BaseApplication.gettoken());

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
        HttpManager.getHttpManager().postMethod(ApiService.getBankList, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SuccinctProgress.dismiss();
                        LogUtil.e("开户银行页面", "错误提示：" + e.getMessage());
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                        SuccinctProgress.dismiss();
                    }

                    @Override
                    public void onNext(String json) {
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        DepositBean depositBean = gson.fromJson(json, DepositBean.class);
                        if (depositBean.getRetCode().equals("SUCCESS")) {
                            final List<DepositBean.DataBean> list = depositBean.getData();
                            ReDeposit.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            ReDeposit.setLayoutManager(new GridLayoutManager(activity, 1));
                            adapter = new DepositAdapter(list, activity);
                            ReDeposit.setAdapter(adapter);
                            adapter.setOnItemClickListener(new DepositAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    bankName = list.get(position).getBankCategoryNumber();
                                    Intent intent = new Intent(activity, BankCardActivity.class);
                                    intent.putExtra("bankName", list.get(position).getBankCategoryNumber());
                                    startActivity(intent);
                                    AppManager.getAppManager().finishActivity();
                                }
                            });

                        } else {
                            ToastUitl.show(depositBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            startActivity(BankCardActivity.class);
            AppManager.getAppManager().finishActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

}
