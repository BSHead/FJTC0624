package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.Function;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.GlideCacheUtil;
import com.foorich.auscashier.view.SetListViewHeight;
import com.foorich.auscashier.view.state.Sofia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 19:18
 * desc   : 账户安全
 * version: 1.0
 */
public class AccountAecurityActivity extends BaseActivity {

    Activity activity;

    // 图片的文字标题 
    private String[] titles = new String[]{"忘记登录密码", "修改登录密码", "支付密码"};

    private String[] title = new String[]{"忘记登录密码", "修改登录密码", "忘记支付密码", "修改支付密码"};

    private String[] v = new String[]{"", "", "未设置"};

    @BindView(R.id.lv_setting)
    ListView listview;
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;


    private AlertDialog myDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_aecurity;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        activity = this;
        mToolbarLeft.setVisibility(View.VISIBLE);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
        myDialog = new AlertDialog(this).builder();
        if (BaseApplication.getexistpaypwd().equals("true")) {
            Jurisdiction2();
        } else {
            Jurisdiction();
        }

    }

    //权限管理
    private void Jurisdiction() {

        FunctionAdapter adapter = new FunctionAdapter(titles, v,
                this);
        listview.setAdapter(adapter);
        SetListViewHeight.setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://忘记登录密码
                        startActivity(ForgetActivity.class);
                        break;

                    case 1://修改登录密码
                        startActivity(ChangeLoginPdActivity.class);
                        break;

                    case 2://设置支付密码
                        startActivity(SetPaymentPdActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    //功能内部类
    class FunctionAdapter extends BaseAdapter {

        private List<Function> functions;
        private LayoutInflater inflater;

        public FunctionAdapter(String[] titles, String[] v, Context context) {
            super();
            functions = new ArrayList<Function>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < titles.length; i++) {
                Function function = new Function(titles[i], v[i]);
                functions.add(function);
            }


        }

        @Override
        public int getCount() {
            if (null != functions) {
                return functions.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return functions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_commonality, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.tv_commonality);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.title
                    .setText(functions.get(position).getTitle());

            return convertView;
        }

        class ViewHolder {
            public TextView title;

        }
    }


    //权限管理
    private void Jurisdiction2() {

        FunctionAdapter2 adapter = new FunctionAdapter2(title,
                this);
        listview.setAdapter(adapter);
        SetListViewHeight.setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://忘记登录密码
                        startActivity(ForgetActivity.class);
                        break;

                    case 1://修改登录密码
                        startActivity(ChangeLoginPdActivity.class);
                        break;

                    case 2://忘记支付密码
                        Intent intent = new Intent(AccountAecurityActivity.this, TextMessageActivity.class);
                        intent.putExtra("type", "3");
                        intent.putExtra("phone", BaseApplication.getloginName());
                        startActivity(intent);
                        break;

                    case 3://修改支付密码
                        startActivity(ChangePayPDActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
    }


    //功能内部类
    class FunctionAdapter2 extends BaseAdapter {

        private List<Function> functions;
        private LayoutInflater inflater;

        public FunctionAdapter2(String[] titles, Context context) {
            super();
            functions = new ArrayList<Function>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < title.length; i++) {
                Function function = new Function(titles[i]);
                functions.add(function);
            }


        }

        @Override
        public int getCount() {
            if (null != functions) {
                return functions.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return functions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_commonality, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.tv_commonality);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.title
                    .setText(functions.get(position).getTitle());
            return convertView;
        }

        class ViewHolder {
            public TextView title;

        }
    }


    //多个控件具有相同的点击事件
    @OnClick({ R.id.btn_exit_login, R.id.toolbar_left})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.toolbar_left:
                AppManager.getAppManager().finishActivity();


            case R.id.btn_exit_login://退出登录
                myDialog.setGone().setMsg("退出登录").setNegativeButton("取消", R.color.black, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).setPositiveButton("确定", R.color.orange, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        BaseApplication.saveoneYardPayLink("");//保存商户静态码
                        BaseApplication.saveloginName("");//保存用户名
                        BaseApplication.saveloginPass("");//保存用户密码
                        BaseApplication.saveheadImgUrl("");//保存用户头像
                        BaseApplication.savetoken("");//保存token
                        BaseApplication.savemerchantCode("");//保存商户号
                        BaseApplication.savemerchantName("");//保存商户名称
                        BaseApplication.savelicense("");//保存营业执照号
                        BaseApplication.saveprovinceCode("");//保存营业地址
                        BaseApplication.saveuserId("");//保存id
                        BaseApplication.saveostype("");//保存id
                        BaseApplication.saveexistpaypwd("");//保存支付密码
                        BaseApplication.savestatus("");//保存商户状态
                        BaseApplication.savestoreCode("");//保存商户状态
                        BaseApplication.saveAccountCode("");//保存提现银行卡号
                        BaseApplication.saveNickName("");//保存用户昵称

                        SharedPreferences sharedPreferences = activity.getSharedPreferences("share", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isFirstRun", true);
                        editor.commit();
                        startActivity(LoginActivity.class);
                        GlideCacheUtil.getInstance().clearImageAllCache(activity);
                        AppManager.getAppManager().finishAllActivity();
                    }
                }).show();
                break;
        }
    }
}
