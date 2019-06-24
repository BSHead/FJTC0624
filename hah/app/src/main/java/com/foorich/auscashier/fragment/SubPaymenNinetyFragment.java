package com.foorich.auscashier.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.PaymentAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.base.BaseFragment;
import com.foorich.auscashier.bean.PaymentBean;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.chart.PieEntry;
import com.foorich.auscashier.view.chart.PieView;
import com.foorich.auscashier.view.chart.StatisticalItem;
import com.foorich.auscashier.view.refresh.header.progresslayout.ProgressLayout;
import com.foorich.auscashier.view.refresh.library.RefreshListenerAdapter;
import com.foorich.auscashier.view.refresh.library.TwinklingRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 16:10
 * desc   : 支付方式报表90天
 * version: 1.0
 */
public class SubPaymenNinetyFragment extends BaseFragment {

    //筛选时间
    @BindView(R.id.tv_payment_ninety_time)
    TextView mTime;
    //报表
    @BindView(R.id.pv_payment_ninety)
    PieView mPieView;
    //自定义刷新
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    //支付方式列表
    @BindView(R.id.re_payment_ninety)
    RecyclerView mRecycleView;
    //支付方式适配器
    PaymentAdapter adapter;
    //传入集合
    List<PaymentBean.DataBean> payList;
    List<PieEntry> list;

    public static Fragment newInstance() {
        SubPaymenNinetyFragment fragment = new SubPaymenNinetyFragment();
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_payment_ninety;
    }


    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        incomeStatement();
        Refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
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
                        incomeStatement();
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


    public void incomeStatement() {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("startDay", "2019-03-08");
            jsonObject.put("endDay", "2019-06-08");
            jsonObject.put("dateType", "90");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("merchantCode", BaseApplication.getmerchantCode());
            map.put("userId", BaseApplication.getuserId());
            map.put("startDay", "2019-03-08");
            map.put("endDay", "2019-06-08");
            map.put("dateType", "90");
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
        HttpManager.getHttpManager().postMethod(ApiService.payMent, new Observer<String>() {
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
                        PaymentBean commonalityBean = gson.fromJson(json, PaymentBean.class);
                        if (commonalityBean.getRetCode().equals("SUCCESS")) {
                            payList = commonalityBean.getData();

                            list = new ArrayList<>();
                            for (int i = 0; i < payList.size(); i++) {
                                PieEntry item = new PieEntry();
                                item.setColor(Color.parseColor(payList.get(i).getColor()));
                                item.setData(Integer.parseInt(payList.get(i).getCount()));
                                item.setMsg(payList.get(i).getRatio());
                                list.add(item);
                            }
                            //开启动画,default = false
                            mPieView.setShowAnimator(true);
                            //设置中间透明圈的透明度,default = 0.4f
                            mPieView.setAlpha(0.5f);
                            //设置初始绘制角度,default = 0
                            mPieView.setStartDegree(0);
                            //设置板块凸出距离，default = 30f
                            mPieView.setSpace(30f);
                            //是否显示百分比,default = true
                            mPieView.setDisPlayPercent(false);
                            //设置板块字体大小,default = 30
                            mPieView.setBlockTextSize(30);
                            //设置板块字体颜色,default = Color.WHITE
                            mPieView.setBlockTextColor(Color.WHITE);
                            //是否显示中心文字,default = false
                            mPieView.setShowCenterText(false);
                            //设置中心文字，default = "PieView"
                            mPieView.setCenterText("丰瑞祥");
                            //设置中心文字大小,default = 50
                            mPieView.setCenterTextSize(60);
                            //设置中心文字颜色，default = Color.GRAY
                            mPieView.setCenterTextColor(Color.GRAY);
                            //设置白色内孔占外圆比例，default = 0.5f,设置值应在（0,1）之间
                            // 并不是很建议设置，设置不合理可能引起图形错乱
                            mPieView.setHoleRadiusPercent(0.5f);
                            //设置透明圆占外圆比例，default = 0.6f,设置值应在（0,1）之间，并大于等于白色内孔
                            // 并不是很建议设置，设置不合理可能引起图形错乱
                            mPieView.setAlphaRadiusPercent(0.5f);
                            //设置扇形报表数据
                            mPieView.setData(list);
                            //支付方式报表
                            adapter = new PaymentAdapter(payList, getActivity());
                            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            mRecycleView.setAdapter(adapter);

                        } else {
                            ToastUitl.show(commonalityBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
