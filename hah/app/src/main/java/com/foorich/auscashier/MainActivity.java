package com.foorich.auscashier;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.bean.TabEntity;
import com.foorich.auscashier.fragment.HomeFragment;
import com.foorich.auscashier.fragment.PersonalFragment;
import com.foorich.auscashier.fragment.RunningFragment;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private String[] mTitles = new String[]{"首页", "流水", "我的"};
    private int[] mIconUnselectIds = {R.mipmap.tap_sy, R.mipmap.tap_ls, R.mipmap.tap_wd};
    private int[] mIconSelectIds = {R.mipmap.tap_sy_select, R.mipmap.tap_ls_select, R.mipmap.tap_wd_select};

    public ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HomeFragment homeFragment;
    private RunningFragment runningFragment;
    private PersonalFragment personalFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
            homeFragment = new HomeFragment();
            runningFragment = new RunningFragment();
            personalFragment = new PersonalFragment();

            fragmentTransaction.add(R.id.fl_body, homeFragment, "homeFragment");
            fragmentTransaction.add(R.id.fl_body, runningFragment, "runningFragment");
            fragmentTransaction.add(R.id.fl_body, personalFragment, "personalFragment");
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            runningFragment = (RunningFragment) getSupportFragmentManager().findFragmentByTag("runningFragment");
            personalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentByTag("personalFragment");
        }
        fragmentTransaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                transaction.show(homeFragment);
                transaction.hide(runningFragment);
                transaction.hide(personalFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.show(runningFragment);
                transaction.hide(homeFragment);
                transaction.hide(personalFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.show(personalFragment);
                transaction.hide(runningFragment);
                transaction.hide(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
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


    //退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;//退出标识

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUitl.show("再按一次退出程序",0);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}