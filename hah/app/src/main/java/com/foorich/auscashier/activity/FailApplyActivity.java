package com.foorich.auscashier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
 * date   : 2019-3-11 14:28
 * desc   : 申请失败
 * version: 1.0
 */
public class FailApplyActivity extends BaseActivity  {

    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;



    @Override
    public int getLayoutId() {
        return R.layout.activity_fail_apply;
    }

    @Override
    public void initPresenter() {

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
    @OnClick({R.id.toolbar_left,R.id.btn_apply})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().returnToActivity(MainActivity.class);
                break;

            case R.id.btn_apply://重新申请
               startActivity(ApplyMerchants.class);
                break;
        }
    }
}
