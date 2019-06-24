package com.foorich.auscashier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 16:43
 * desc   : 报表订单页面
 * version: 1.0
 */
public class IndentFragment extends BaseFragment {


    private Fragment[] mFragmentArrays = new Fragment[3];
    private String[] mTabTitles = new String[3];

    public Context mContext;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    @BindView(R.id.indent_viewpager)
    ViewPager viewPager;
    @BindView(R.id.indent_tablayout)
    TabLayout tabLayout;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_indent;
    }

    @Override
    public void initPresenter() {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("订单报表");
        mLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {
        mTabTitles[0] = "7天";
        mTabTitles[1] = "30天";
        mTabTitles[2] = "90天";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //  tabLayout.setPadding(50,50,50,50);
        mFragmentArrays[0] = SubIndentSevenFragment.newInstance();
        mFragmentArrays[1] = SubIndentThirtyFragment.newInstance();
        mFragmentArrays[2] = SubIndentNinetyFragment.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left:
                getActivity().finish();
                break;
        }
    }
}
