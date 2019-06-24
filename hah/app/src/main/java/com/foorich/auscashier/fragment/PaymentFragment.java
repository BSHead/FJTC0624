package com.foorich.auscashier.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 16:45
 * desc   : 报表支付方式
 * version: 1.0
 */
public class PaymentFragment extends BaseFragment  {

    //顶部导航个数
    private Fragment[] mFragmentArrays = new Fragment[3];
    private String[] mTabTitles = new String[3];
    //上下文
    public Context mContext;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    @BindView(R.id.payment_viewpager)
    ViewPager viewPager;
    @BindView(R.id.payment_tablayout)
    TabLayout tabLayout;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_payment;
    }

    @Override
    public void initPresenter() {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("支付方式报表");
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
        mFragmentArrays[0] = SubPaymenSevenFragment.newInstance();
        mFragmentArrays[1] = SubPaymenThirtyFragment.newInstance();
        mFragmentArrays[2] = SubPaymenNinetyFragment.newInstance();
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
