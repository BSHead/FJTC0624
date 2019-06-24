package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.view.SeparatedEditText;
import com.foorich.auscashier.view.keyboard.KeyboardAdd;
import com.foorich.auscashier.view.keyboard.KeyboardUtils;
import com.foorich.auscashier.view.keyboard.MyKeyBoardView;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-19 14:26
 * desc   : 设置支付密码
 * version: 1.0
 */
public class SetPaymentPdActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_left)
    ImageView Toolbar_left_img;

    @BindView(R.id.ed_setpay_underline)
    SeparatedEditText mEditText;

    //自定义键盘
    @BindView(R.id.keyboard_view)
    MyKeyBoardView mKeyboardView;

    boolean showContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpaymentpd;
    }

    @Override
    public void initPresenter() {
        //接收广播消息
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                mEditText.setText("");
            }
        }
    };


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
                    Intent intent = new Intent(SetPaymentPdActivity.this, AffirmPaymentPdActivity.class);
                    intent.putExtra("password", mEditText.getText().toString());
                    startActivity(intent);
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
}
