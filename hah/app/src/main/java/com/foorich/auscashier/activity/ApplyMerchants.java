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
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-7 16:56
 * desc   : 申请商户
 * version: 1.0
 */
public class ApplyMerchants extends BaseActivity {


    Activity activity;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView Toolbar_left;
    //返回
    @BindView(R.id.tv_explain)
    TextView mExplain;


    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_merchants;
    }

    @Override
    public void initPresenter() {
        activity = this;
    }

    @Override
    public void initView() {
        Toolbar_left.setVisibility(View.VISIBLE);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

//        mExplain.setText("商户分为个体商户和企业商户，商户类别不同，需提交的资料不同，开通的功能额度以及贷款额度不同，请您如实填写！查看");
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left, R.id.tv_enterprise_business, R.id.tv_businessman})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_enterprise_business://企业商户（0-企业）
                Intent intent0 = new Intent(activity, BusinessmanActivity.class);
                intent0.putExtra("aptitude", "0");
                startActivity(intent0);
                //startActivity(QualificationActivity.class);
                //startActivity(BankCardActivity.class);
                break;

            case R.id.tv_businessman://个体商户（1-个人）
                Intent intent1 = new Intent(activity, BusinessmanActivity.class);
                intent1.putExtra("aptitude", "1");
                startActivity(intent1);
                break;

        }
    }
}
