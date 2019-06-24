package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.utils.AmountUtil;
import com.foorich.auscashier.utils.DateUtil;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-10 14:36
 * desc   : 收款详情页面
 * version: 1.0
 */
public class GatheringActivity extends BaseActivity {

    //收款金额
    @BindView(R.id.tv_amt)
    TextView mAmt;
    //收款金额
    @BindView(R.id.tv_amt1)
    TextView mAmt1;
    //收款方式
    @BindView(R.id.tv_type)
    TextView mType;
    //交易单号
    @BindView(R.id.tv_orderNum)
    TextView mOrderNum;
    //创建时间
    @BindView(R.id.tv_createTime)
    TextView mCreateTime;
    //备注
    @BindView(R.id.tv_remark)
    TextView mRemark;


    String amt;
    String channelCode;
    String orderNum;
    String createTime;
    String remarks;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gathering;
    }

    @Override
    public void initPresenter() {
        Intent intent = this.getIntent();
        amt = AmountUtil.changeF2Y(this, intent.getStringExtra("amt"));//金额
        channelCode = intent.getStringExtra("channelCode");//支付方式
        orderNum = intent.getStringExtra("orderNum");//订单号
        createTime ="" + DateUtil.transform(intent.getStringExtra("createTime"));//交易时间
        remarks = intent.getStringExtra("remarks");//备注
        //金额
        if (amt.contains(".")) {
            mAmt1.setText("+"+amt + "元");
            mAmt.setText(amt + "元");
        }else{
            mAmt1.setText("+"+amt + ".00元");
            mAmt.setText(amt + ".00元");
        }
        mType.setText("账户余额");
        mOrderNum.setText(orderNum);
        mCreateTime.setText(createTime);
        mRemark.setText(remarks);
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
    @OnClick({ R.id.btn_gathering})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.btn_gathering://返回
                Intent intent = new Intent();
                intent.setAction("action.refresh");
                sendBroadcast(intent);
                AppManager.getAppManager().returnToActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            Intent intent = new Intent();
            intent.setAction("action.refresh");
            sendBroadcast(intent);
            AppManager.getAppManager().returnToActivity(MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

}
