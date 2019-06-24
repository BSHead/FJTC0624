package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.DealflowAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.DealflowBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.GlideRoundTransformUtil;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.SeparatedEditText;
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
 * date   : 2019-6-4 16:54
 * desc   : 流水详情
 * version: 1.0
 */
public class DealflowBillActivity extends BaseActivity implements View.OnClickListener, PopupWindow.OnDismissListener {

    Activity activity;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //付款头像
    @BindView(R.id.img_bill)
    ImageView mPicture;
    //收款方式
    @BindView(R.id.tv_bill_payType)
    TextView mTvBillPayType;
    //收款金额
    @BindView(R.id.tv_bill_Amt)
    TextView mTvBillAmt;
    //交易状态
    @BindView(R.id.tv_bill_tradeState)
    TextView mTvBillTradeState;
    //交易单号
    @BindView(R.id.tv_orderNum)
    TextView mTvOrderNum;
    //支付方式
    @BindView(R.id.tv_payType)
    TextView mTvPayType;
    //支付时间
    @BindView(R.id.tv_time)
    TextView mTvTime;
    //订单来源
    @BindView(R.id.tv_leaveMessage)
    TextView mTvLeaveMessage;
    //退款
    @BindView(R.id.btn_refund)
    TextView mRefund;
    PopupWindow popupWindow;//PopupWindow
    private int screenWidth;//popupWindow宽度
    private int screenHeight;//popupWindow高度
    SeparatedEditText separatedEditText;
    View contentView;
    ImageView imgDelete;
    TextView tvPayFroget;
    boolean showContent;
    @BindView(R.id.li_layout)
    LinearLayout mliLayout;
    View view;

    String orderNum;//接收传值交易单号
    String amt;//接收传值交易金额

    @Override
    public int getLayoutId() {
        return R.layout.activity_dealflowbill;
    }

    @Override
    public void initPresenter() {
        initScreenWidth();
        activity = this;
        Intent intent = this.getIntent();
        Glide.with(this).load(intent.getStringExtra("picture")).transform(new GlideRoundTransformUtil(this)).into(mPicture);//图片
        mTvBillPayType.setText(intent.getStringExtra("payType"));//收款方式
        mTvPayType.setText(intent.getStringExtra("payType"));//收款方式
        mTvBillTradeState.setText(intent.getStringExtra("tradeState"));//交易状态
        orderNum = intent.getStringExtra("orderNum");//交易单号
        mTvOrderNum.setText(intent.getStringExtra("orderNum"));
        mTvTime.setText(intent.getStringExtra("createTime"));//创建时间
        mTvLeaveMessage.setText(intent.getStringExtra("leaveMessage"));//订单来源
        amt = intent.getStringExtra("amt");//收款金额
        mTvBillAmt.setText(amt);
        //判断交易状态
        if(intent.getStringExtra("tradeState").equals("退款申请中")){
            mRefund.setVisibility(View.INVISIBLE);
        }else{
            mRefund.setVisibility(View.VISIBLE);
        }
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
    @OnClick({R.id.toolbar_left, R.id.btn_refund})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.btn_refund://退款
                showPopupWindow(findViewById(R.id.li_layout));
                break;
        }
    }


    //退款
    public void Refund(String refundPass) {
        SuccinctProgress.showSuccinctProgress(DealflowBillActivity.this,
                "退款中···", SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            EncrypMD5 m = new EncrypMD5();
            byte[] result = m.eccrypt(refundPass);
            String payPD = EncrypMD5.hexString(result);

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("merchantNo", BaseApplication.getmerchantCode());
            jsonObject.put("refundPass", payPD);
            jsonObject.put("orderNum", orderNum);
            jsonObject.put("amt", amt);

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("merchantNo", BaseApplication.getmerchantCode());
            map.put("refundPass", payPD);
            map.put("orderNum", orderNum);
            map.put("amt", amt);
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
        HttpManager.getHttpManager().postMethod(ApiService.refund, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SuccinctProgress.dismiss();
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                        separatedEditText.setText("");
                    }

                    @Override
                    public void onNext(String json) {
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        CommonalityBean Bean = gson.fromJson(json, CommonalityBean.class);
                        if (Bean.getRetCode().equals("SUCCESS")) {
                            ToastUitl.show(Bean.getMessage(),0);
                            popupWindow.dismiss();
                            Intent intent = new Intent();
                            intent.setAction("action.refresh");
                            sendBroadcast(intent);
                            AppManager.getAppManager().returnToActivity(MainActivity.class);

                        } else {
                            ToastUitl.show(Bean.getMessage(),0);
                            separatedEditText.setText("");
                        }
                    }
                }, body
        );
    }


    /*
     * 支付密码弹框
     */
    public void showPopupWindow(final View anchor) {
        popupWindow = new PopupWindow(this);
        //popupWindow画布
        contentView = LayoutInflater.from(this).inflate(
                R.layout.layout_paypd, null);
        //设置背景半透明
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(this);
        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(screenHeight / 2 + 200);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setAnimationStyle(R.style.DialogAnimation);
        popupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);//设置随意位置点击
        separatedEditText = (SeparatedEditText) contentView.findViewById(R.id.ed_pay_underline);
        imgDelete = (ImageView) contentView.findViewById(R.id.img_pay_delete);
        imgDelete.setOnClickListener(this);
        tvPayFroget = (TextView) contentView.findViewById(R.id.tv_pay_forget);
        tvPayFroget.setOnClickListener(this);
        //设置密码隐藏
        separatedEditText.setPassword(!showContent);
        //自动调起键盘
        CommonUtils.showSoftKeyboard(separatedEditText);
        //监听输入框数字
        separatedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
                if (separatedEditText.getText().toString().trim().length() >= 6) {
                    //大于六位密码直接支付
                    Refund(separatedEditText.getText().toString().trim());
                }
            }
        });
    }


    /*
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_pay_delete:
                popupWindow.dismiss();
                break;
        }
    }

}
