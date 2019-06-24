package com.foorich.auscashier.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.activity.DealflowBillActivity;
import com.foorich.auscashier.activity.SelectionTimeActivity;
import com.foorich.auscashier.adapter.DealflowAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.base.BaseFragment;
import com.foorich.auscashier.bean.DealflowBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.MyScrollView;
import com.google.gson.Gson;

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
 * date   : 2019-3-11 14:54
 * desc   : 流水
 * version: 1.0
 */
public class RunningFragment extends BaseFragment {

    Activity activity;
    //标题
    @BindView(R.id.toolbar_title)
    TextView TvTitle;
    @BindView(R.id.toolbar_right)
    ImageView ImgRight;
    //滑动监听
    @BindView(R.id.scrollview)
    MyScrollView scrollView;
    //列表
    @BindView(R.id.rv_dealflow)
    RecyclerView rvDealflow;
    //适配器
    DealflowAdapter adapter;
    //订单集合
    List<DealflowBean.DataBean.ItemsBean.Item0Bean.OrderListBean> OrderList;
//    int pageNum = 1;//初始页数设置
//    @BindView(R.id.refresh_layout)
//    PullRefreshLayout mRefreshLayout;//刷新自定义


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_runnimg;
    }


    @Override
    public void initPresenter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        //滑动监听
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int i = CommonUtils.dip2px(activity, scrollY);
                int dp = CommonUtils.px2dp(activity, i);
                if (dp > 50) {
                    TvTitle.setVisibility(View.VISIBLE);
                    TvTitle.setText("流水");
                } else {
                    TvTitle.setVisibility(View.INVISIBLE);
                    TvTitle.setText("");
                }
            }
        });
    }


    @Override
    protected void initView() {
        activity = getActivity();
        rvDealflow.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        //解决数据加载不完的问题
        rvDealflow.setNestedScrollingEnabled(false);
        rvDealflow.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        rvDealflow.setFocusable(false);
        //显示图标
        ImgRight.setVisibility(View.VISIBLE);
        ImgRight.setImageResource(R.mipmap.ls_sx);
        Dealflow();

//        Refresh();
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_right})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_right:
                //跳转到时间筛选
                startActivity(SelectionTimeActivity.class);
                break;

        }

    }


    /*
     * 刷新接口
     */
/*
    private void Refresh() {
        */
/*
         *  下拉刷新
         *//*

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onPullDownRefresh() {
                mRefreshLayout.onRefreshComplete();
                pageNum = 1;
//                List.clear();
//                getData(pageNum);
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.onRefreshComplete();
                    }
                }, 1000);
//                Searchadapter.notifyDataSetChanged();
            }

            */
/*
             *  上拉加载
             *//*

            @Override
            public void onPullUpRefresh() {
//                if (List.size() % 10 == 0) {
//                    pageNum = (int) (List.size() / 10) + 1;
//                    getData(pageNum);
//                } else {
//                    pageNum = (int) (List.size() / 10) + 2;
//                    getData(pageNum);
//                }
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.onRefreshComplete();
                    }
                }, 1500);
//                Searchadapter.notifyDataSetChanged();
            }
        });
//        ShelvesCameraView.setAdapter(Searchadapter);
    }
*/


    //流水查询
    public void Dealflow() {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("page", 1);
            jsonObject.put("rows", 20);
            jsonObject.put("startDate", "");
            jsonObject.put("endDate", "");
            jsonObject.put("dateMonth", "");

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();

            map.put("merchantCode", BaseApplication.getmerchantCode());
            map.put("userId", BaseApplication.getuserId());
            map.put("page", "1");
            map.put("rows", "20");
            map.put("startDate", "");
            map.put("endDate", "");
            map.put("dateMonth", "");
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
        HttpManager.getHttpManager().postMethod(ApiService.dealflow, new Observer<String>() {
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
                        DealflowBean Bean = gson.fromJson(json, DealflowBean.class);
                        if (Bean.getRetCode().equals("SUCCESS")) {
                            OrderList = Bean.getData().getItems().getItem0().getOrderList();
                            adapter = new DealflowAdapter(OrderList, getActivity());
                            rvDealflow.setAdapter(adapter);
                            adapter.setOnItemClickListener(new DealflowAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(activity, DealflowBillActivity.class);
                                    intent.putExtra("picture", OrderList.get(position).getPicture());//头像
                                    intent.putExtra("payType", OrderList.get(position).getPayType());//收款方式
                                    intent.putExtra("tradeState", OrderList.get(position).getTradeState());//支付状态
                                    intent.putExtra("orderNum", OrderList.get(position).getOrderNum());//交易单号
                                    intent.putExtra("createTime", OrderList.get(position).getCreateTime());//交易时间
                                    intent.putExtra("leaveMessage", OrderList.get(position).getLeaveMessage());//订单来源
                                    intent.putExtra("amt", OrderList.get(position).getAmt());//金额
                                    startActivity(intent);
                                }
                            });

                        } else {

                        }
                    }
                }, body
        );
    }


    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                Dealflow();
            }
        }
    };
}
