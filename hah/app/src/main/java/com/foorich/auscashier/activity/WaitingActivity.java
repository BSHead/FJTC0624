package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-7 16:56
 * desc   : 申请提交等待
 * version: 1.0
 */
public class WaitingActivity extends BaseActivity {


    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    //商户名称
    @BindView(R.id.tv_name)
    TextView mName;
    //商户时间
    @BindView(R.id.tv_date)
    TextView mDate;


    @Override
    public int getLayoutId() {
        return R.layout.activity_waiting;
    }

    @Override
    public void initPresenter() {
        Intent intent = this.getIntent();
        mName .setText(intent.getStringExtra("merchantName"));
        mDate .setText(intent.getStringExtra("createTime"));
    }

    @Override
    public void initView() {
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("商户申请");

        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().returnToActivity(MainActivity.class);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            AppManager.getAppManager().returnToActivity(MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

}

