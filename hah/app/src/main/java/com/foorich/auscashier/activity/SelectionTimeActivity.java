package com.foorich.auscashier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-5 16:02
 * desc   : 选择时间
 * version: 1.0
 */
public class SelectionTimeActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_selectiontime;
    }


    @Override
    public void initPresenter() {


    }

    @Override
    public void initView() {

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.tv_back,R.id.tv_complete,R.id.li_select})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.tv_back:
                //返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_complete:
                //完成回调
                ToastUitl.show("完成",0);
                break;

            case R.id.li_select:
                //按日筛选
                ToastUitl.show("按日切换",0);
                break;


        }
    }
}
