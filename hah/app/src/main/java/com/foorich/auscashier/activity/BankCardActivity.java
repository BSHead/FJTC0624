package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.AllMerchantAdapter;
import com.foorich.auscashier.adapter.DepositAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.DepositBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.BottomPopupOption;
import com.foorich.auscashier.view.MClearEditText;
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
 * desc   : 银行卡信息
 * version: 1.0
 */
public class BankCardActivity extends BaseActivity {


    Activity activity;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    //开户人
    @BindView(R.id.ed_account_holder)
    MClearEditText mAccountHolder;
    //开户银行
    @BindView(R.id.tv_deposit_bank)
    TextView mDepositBank;
    //开户行支行
    @BindView(R.id.ed_account_branch)
    MClearEditText mAccountBranch;
    //银行卡号
    @BindView(R.id.ed_bankcard)
    MClearEditText mBankCard;
    //账户类型
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    String Account = "";

    //对公账户（弹框）
    BottomPopupOption bottomPopupOption;
    //开户银行
    private View DepositpopView;
    private PopupWindow DepositpopupWindow;
    RecyclerView ReDeposit;//商户类型列表
    private int screenWidth;//popupWindow宽度
    private int screenHeight;//popupWindow高度
    ImageView mBack;//商户类型返回
    TextView mTvTitle;//商户类型列表
    DepositAdapter adapter;
    List<DepositBean.DataBean> list;
    String aptitude = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initPresenter() {
//        activity = this;
//        Intent intent = getIntent();
//        aptitude = intent.getStringExtra("aptitude");
    }

    @Override
    public void initView() {
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("银行卡信息");
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
        bottomPopupOption = new BottomPopupOption(BankCardActivity.this);
        initScreenWidth();
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left, R.id.btn_bank, R.id.tv_account, R.id.tv_deposit_bank})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_account://银行账户类型
                bottomPopupOption.setItemText("对私账户", "对公账户");
                // bottomPopupOption.setColors();//设置颜色
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            Account = "2";
                            bottomPopupOption.dismiss();
                            mTvAccount.setText("对私账户");
                        } else if (position == 1) {
                            Account = "1";
                            bottomPopupOption.dismiss();
                            mTvAccount.setText("对公账户");
                        }
                    }
                });
                break;

            case R.id.tv_deposit_bank://开户银行
                getBankList();
                break;

            case R.id.btn_bank://下一步
                if (mTvAccount.getText().toString().trim().equals("")) {
                    ToastUitl.show("请选择账户类型", 0);
                } else if (mAccountHolder.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入开户人", 0);
                } else if (mDepositBank.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入开户银行", 0);
                } else if (mAccountBranch.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入开户支行", 0);
                } else if (mBankCard.getText().toString().trim().equals("")) {
                    ToastUitl.show("请输入银行卡号", 0);
                } else {
                    saveOrUpdateBank("", mDepositBank.getText().toString().trim(), mAccountBranch.getText().toString().trim(), mBankCard.getText().toString().trim(), Account);
                }
                break;
        }
    }

    //开户银行
    private void DepositPopWindow() {
        //  适配PopupWindow布局文件
        DepositpopView = View.inflate(this, R.layout.layout_merchant, null);
        backgroundAlpha(0.5f);
        //  创建PopupWindow
        DepositpopupWindow = new PopupWindow(DepositpopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给PopupWindow设置动画
        DepositpopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        DepositpopupWindow.setFocusable(false);
        DepositpopupWindow.setOutsideTouchable(false);
        DepositpopupWindow.setWidth(screenWidth);
        DepositpopupWindow.setHeight(screenHeight / 2 + 300);
        DepositpopupWindow.setBackgroundDrawable(new PaintDrawable());
        DepositpopupWindow.showAtLocation(findViewById(R.id.li_layout), Gravity.BOTTOM, 0, 0);//设置随意位置点击

        //  PopupWindow返回键
        mBack = (ImageView) DepositpopView.findViewById(R.id.iv_back);
        //  PupupWindow标题
        mTvTitle = (TextView) DepositpopView.findViewById(R.id.tv_title);
        mTvTitle.setText("开户银行");
        //  银行卡列表的ListView
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepositpopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });

        ReDeposit = (RecyclerView) DepositpopView.findViewById(R.id.re_merchant);
        ReDeposit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ReDeposit.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new DepositAdapter(list, BankCardActivity.this);
        adapter.setOnItemClickListener(new DepositAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mDepositBank.setText(list.get(position).getBankCategoryNumber());
                DepositpopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        ReDeposit.setAdapter(adapter);

    }

    /*
     * 查看自身的宽高
     */
    public void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = BaseApplication.getContext().getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    /*
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
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
                        backgroundAlpha(1f);
                        LogUtil.e("开户银行页面", "错误提示：" + e.getMessage());
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
                        DepositBean depositBean = gson.fromJson(json, DepositBean.class);
                        if (depositBean.getRetCode().equals("SUCCESS")) {
                            list = depositBean.getData();
                            DepositPopWindow();

                        } else {
                            ToastUitl.show(depositBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }


    //保存银行卡信息
    public void saveOrUpdateBank(String openingCode, String openingBank, String branchBank, String accountCode, String accountType) {
//        SuccinctProgress.showSuccinctProgress(activity,
//                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openingCode", openingCode);
            jsonObject.put("openingBank", openingBank);
            jsonObject.put("branchBank", branchBank);
            jsonObject.put("accountCode", accountCode);
            jsonObject.put("accountType", Integer.parseInt(accountType));
            jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("openingCode", openingCode);
            map.put("openingBank", openingBank);
            map.put("branchBank", branchBank);
            map.put("accountCode", accountCode);
            map.put("accountType", accountType);
            map.put("openingCode", openingCode);
            map.put("merchantCode", BaseApplication.getmerchantCode());
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
        HttpManager.getHttpManager().postMethod(ApiService.saveOrUpdateBank, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        backgroundAlpha(1f);
                        LogUtil.e("开户银行页面", "错误提示：" + e.getMessage());
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        CommonalityBean commonalityBean = gson.fromJson(json, CommonalityBean.class);
                        if (commonalityBean.getRetCode().equals("SUCCESS")) {
//                            if(aptitude.equals("0")){
//                                startActivity(SubmissionActivity.class);
//                            }else{
                                startActivity(StoresAccordingActivity.class);
//                            }
                        } else {
                            ToastUitl.show(commonalityBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
