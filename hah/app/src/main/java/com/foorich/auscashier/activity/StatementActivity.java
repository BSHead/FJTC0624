package com.foorich.auscashier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.bean.TabEntity;
import com.foorich.auscashier.fragment.IncomeFragment;
import com.foorich.auscashier.fragment.IndentFragment;
import com.foorich.auscashier.fragment.PaymentFragment;
import com.foorich.auscashier.view.state.Sofia;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-10 14:36
 * desc   : 报表页面
 * version: 1.0
 */
public class StatementActivity extends BaseActivity {

    @BindView(R.id.tab_layout)

    CommonTabLayout tabLayout;

    private String[] mTitles = new String[]{"收入", "订单", "支付方式"};

    private int[] mIconUnselectIds = {R.mipmap.bb_sr, R.mipmap.bb_dd, R.mipmap.bb_zffs};//,R.mipmap.bb_yhq
    private int[] mIconSelectIds = {R.mipmap.bb_sr_select, R.mipmap.bb_dd_select, R.mipmap.bb_zffs_select};//,R.mipmap.bb_yhq_select

    public ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private IncomeFragment incomeFragment;
    private IndentFragment indentFragment;
    private PaymentFragment paymentFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_statement;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTab();
        //状态栏设置白色，字体设置黑色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragment(savedInstanceState);
    }

    public void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    //初始化碎片
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (null == savedInstanceState) {
            incomeFragment = new IncomeFragment();
            indentFragment = new IndentFragment();
            paymentFragment = new PaymentFragment();

            fragmentTransaction.add(R.id.fl_body, incomeFragment, "incomeFragment");
            fragmentTransaction.add(R.id.fl_body, indentFragment, "indentFragment");
            fragmentTransaction.add(R.id.fl_body, paymentFragment, "paymentFragment");

        } else {
            incomeFragment = (IncomeFragment) getSupportFragmentManager().findFragmentByTag("incomeFragment");
            indentFragment = (IndentFragment) getSupportFragmentManager().findFragmentByTag("indentFragment");
            paymentFragment = (PaymentFragment) getSupportFragmentManager().findFragmentByTag("paymentFragment");
        }
        fragmentTransaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                transaction.show(incomeFragment);
                transaction.hide(indentFragment);
                transaction.hide(paymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.show(indentFragment);
                transaction.hide(incomeFragment);
                transaction.hide(paymentFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.show(paymentFragment);
                transaction.hide(indentFragment);
                transaction.hide(incomeFragment);
                transaction.commitAllowingStateLoss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //1 显示tabLayout的角标 显示未读数
//        tabLayout.showMsg(1, 0);
        tabLayout.setMsgMargin(3, 0, 10);
        //隐藏消息
        //tabLayout.hideMsg(1);
    }
}