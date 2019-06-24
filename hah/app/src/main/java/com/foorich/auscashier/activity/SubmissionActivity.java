package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;
import butterknife.OnClick;

public class SubmissionActivity  extends BaseActivity {


    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;

    String merchantName;
    String createTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_submission;
    }

    @Override
    public void initPresenter() {
        Intent intent = this.getIntent();
        merchantName = intent.getStringExtra("merchantName");
        createTime = intent.getStringExtra("createTime");
    }

    @Override
    public void initView() {
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("申请已提交");

        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left,R.id.btn_submission})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;


            case R.id.btn_submission://完成
                Intent intent = new Intent(SubmissionActivity.this, WaitingActivity.class);
                intent.putExtra("merchantName", merchantName);
                intent.putExtra("createTime", createTime);
                startActivity(intent);

                break;

        }
    }
}
