package com.foorich.auscashier.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.activity.ApplyMerchants;
import com.foorich.auscashier.activity.CaptureActivity;
import com.foorich.auscashier.activity.FailApplyActivity;
import com.foorich.auscashier.activity.GatheringCodeActivity;
import com.foorich.auscashier.activity.MessageActivity;
import com.foorich.auscashier.activity.StatementActivity;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.base.BaseFragment;
import com.foorich.auscashier.bean.InitializationBean;
import com.foorich.auscashier.bean.MessageList;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.MyScrollView;
import com.foorich.auscashier.view.refresh.header.progresslayout.ProgressLayout;
import com.foorich.auscashier.view.refresh.library.RefreshListenerAdapter;
import com.foorich.auscashier.view.refresh.library.TwinklingRefreshLayout;
import com.foorich.auscashier.view.zing.bean.ZxingConfig;
import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 14:28
 * desc   : 首页
 * version: 1.0
 */
public class HomeFragment extends BaseFragment {

    //上下文
    public Context mContext;
    //消息
    @BindView(R.id.toolbar_right)
    ImageView mRight;
    //弹框
    private AlertDialog myDialog;
    //收款金额
    @BindView(R.id.tv_receiptsAmt)
    TextView mTvReceiptsAmt;
    //交易笔数
    @BindView(R.id.tv_penReceipts)
    TextView mTvPenReceipts;
    //用户充值金额
    @BindView(R.id.tv_rechargeAmt)
    TextView mTvRechargeAmt;
    //用户充值笔数
    @BindView(R.id.tv_penRecharge)
    TextView mTvPenRecharge;
    //滑动监听
    @BindView(R.id.scrollview)
    MyScrollView scrollView;
    @BindView(R.id.toolbar_title)
    TextView TvTitle;

    private int REQUEST_CODE_SCAN = 111;
    //自定义刷新
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        //接收广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        //滑动监听
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int i = CommonUtils.dip2px(getActivity(), scrollY);
                int dp = CommonUtils.px2dp(getActivity(), i);
                if (dp > 50) {
                    TvTitle.setVisibility(View.VISIBLE);
                    TvTitle.setText("首页");
                } else {
                    TvTitle.setVisibility(View.INVISIBLE);
                    TvTitle.setText("");
                }
            }
        });
    }

    @Override
    protected void initView() {
        mContext = getActivity();
        myDialog = new AlertDialog(mContext).builder();
        mRight.setVisibility(View.VISIBLE);


        if (BaseApplication.getstatus().equals("0")) {//审核成功
        } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
            myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("申请", R.color.orange, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();
        } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
            myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(WaitingActivity.class);
                }
            }).show();
        } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
            startActivity(FailApplyActivity.class);
        } else {//申请商户
            SetMerchant();
        }

        initialization();
        Refresh();
    }


    /*
     * 刷新接口
     */
    private void Refresh() {
        ProgressLayout header = new ProgressLayout(getActivity());//这三行是刷新谷歌默认
        refreshLayout.setHeaderView(header);//这三行是刷新谷歌默认
        refreshLayout.setFloatRefresh(true);//这三行是刷新谷歌默认
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initialization();
                        initUpdateFlag();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_right, R.id.li_gathering, R.id.li_gathering_code, R.id.li_statement})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right://消息
                if (BaseApplication.getstatus().equals("0")) {//审核成功
                    startActivity(MessageActivity.class);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }
                break;

            case R.id.li_gathering://收款
                if (BaseApplication.getstatus().equals("0")) {//审核成功

//                    startActivity(GatheringActivity.class);
                    AndPermission.with(this).permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                            Intent intent = new Intent(getActivity(), CaptureActivity.class);
                            //ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                            //也可以不传这个参数
                            //不传的话  默认都为默认不震动  其他都为true
                            ZxingConfig config = new ZxingConfig();
                            config.setPlayBeep(true);
                            config.setShake(true);
                            intent.putExtra(com.foorich.auscashier.view.zing.common.Constant.INTENT_ZXING_CONFIG, config);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            ToastUitl.show("没有权限无法扫描呦", 0);
                        }
                    }).start();

                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }
                break;

            case R.id.li_gathering_code://收款码
                if (BaseApplication.getstatus().equals("0")) {//审核成功
                    startActivity(GatheringCodeActivity.class);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }
                break;

            case R.id.li_statement://报表

//                private void notify_normal_singLine() {
                //设置想要展示的数据内容
//                Intent intent = new Intent(mContext, AboutActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                PendingIntent pIntent = PendingIntent.getActivity(mContext,
//                        requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                int smallIcon = R.mipmap.bga_refresh_loading03;
//                String ticker = "您有一条新通知";
//                String title = "你哟一笔花呗收款";
//                String content = "花呗收款0.01元";
//
//                //实例化工具类，并且调用接口
//                NotifyUtil notify1 = new NotifyUtil(mContext, 1);
//                notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
//                currentNotify = notify1;
//            }

                if (BaseApplication.getstatus().equals("0")) {//审核成功
                    startActivity(StatementActivity.class);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }

                break;
        }
    }


    //申请成为商户
    public void SetMerchant() {
        myDialog.setGone().setCancelable(false).setMsg("您要先开通商户，才能使用该公能哦，快去开通吧！").setNegativeButton("谢谢，不了", R.color.black, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }

        }).setPositiveButton("申请", R.color.orange, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ApplyMerchants.class);
            }
        }).show();
    }


    //首页初始化
    public void initialization() {

        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("key", BaseApplication.gettoken());
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(LinkStringByGet.createLinkStringByGet(map)));
            //MD5在次加密SHA1
            EncrypMD5 md5 = new EncrypMD5();
            byte[] resultBytes = md5.eccrypt(sha1);
            String md52 = EncrypMD5.hexString(resultBytes);
            //最终提交参数
            json.put("appVersion", "1.0");
            json.put("data", jsonObject);
            json.put("language", "zh");
            json.put("sign", md52);
            json.put("tenant", "platform");
            json.put("terminal", "Android");
            json.put("token", BaseApplication.gettoken());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.homepage, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        InitializationBean Bean = gson.fromJson(json, InitializationBean.class);
                        if (Bean.getRetCode().equals("SUCCESS")) {
                            mTvReceiptsAmt.setText(String.valueOf(Bean.getData().getReceiptsAmt()));
                            mTvPenReceipts.setText(String.valueOf(Bean.getData().getPenReceipts()));
                            mTvRechargeAmt.setText(String.valueOf(Bean.getData().getRechargeAmt()));
                            mTvPenRecharge.setText(String.valueOf(Bean.getData().getPenRecharge()));
                            initUpdateFlag();
                        } else {
                            ToastUitl.show(Bean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }


    // 接收广播消息
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                initialization();
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
    }


    public void initUpdateFlag() {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Integer.parseInt(BaseApplication.getuserId()));
            jsonObject.put("usertype", "0");

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("usertype", "0");
            map.put("key", BaseApplication.gettoken());
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(LinkStringByGet.createLinkStringByGet(map)));
            //MD5在次加密SHA1
            EncrypMD5 md5 = new EncrypMD5();
            byte[] resultBytes = md5.eccrypt(sha1);
            String md52 = EncrypMD5.hexString(resultBytes);
            //最终提交参数
            json.put("appVersion", "1.0");
            json.put("data", jsonObject);
            json.put("language", "zh");
            json.put("sign", md52);
            json.put("tenant", "platform");
            json.put("terminal", "Android");
            json.put("token", BaseApplication.gettoken());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.getMessageNum, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        if (e.getMessage().equals("connect timed out")) {
//                            ToastUitl.show("网络连接超时", 0);
//                        } else if (e.getMessage().contains("Failed to connect to")) {
//                            ToastUitl.show("服务器异常,请重试", 0);
//                        } else {
//                            ToastUitl.show("服务器异常,请重试", 0);
//                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        MessageList messageList = gson.fromJson(json, MessageList.class);
                        if (messageList.getRetCode().equals("SUCCESS")) {
                            if (messageList.getData().getJiaoYiIsRead().equals("false")&&messageList.getData().getXiTongIsRead().equals("false")&&messageList.getData().getJiaoYiIsRead().equals("false")) {
                                mRight.setImageResource(R.mipmap.sy_xx);
                            }else{
                                mRight.setImageResource(R.mipmap.sy_xx);
                            }

                        } else {

                        }
                    }
                }, body
        );
    }

}
