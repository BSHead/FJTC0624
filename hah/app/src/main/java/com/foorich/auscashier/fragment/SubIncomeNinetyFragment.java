package com.foorich.auscashier.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.base.BaseFragment;
import com.foorich.auscashier.bean.IncomeBean;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.refresh.header.progresslayout.ProgressLayout;
import com.foorich.auscashier.view.refresh.library.RefreshListenerAdapter;
import com.foorich.auscashier.view.refresh.library.TwinklingRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 16:10
 * desc   : 收入报表90天
 * version: 1.0
 */
public class SubIncomeNinetyFragment extends BaseFragment {


    //筛选时间
    @BindView(R.id.tv_income_ninety_time)
    TextView mTime;
    //90天收入
    @BindView(R.id.tv_income_ninety_earning)
    TextView mEarning;
    //单日最高
    @BindView(R.id.tv_income_ninety_biggest)
    TextView mBiggest;
    //日均
    @BindView(R.id.tv_income_ninety_daily)
    TextView mDaily;
    //较之前90天
    @BindView(R.id.tv_income_ninety_before)
    TextView mCompare;
    //自定义刷新
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    public static Fragment newInstance() {
        SubIncomeNinetyFragment fragment = new SubIncomeNinetyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_income_ninety;
    }


    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        incomeStatement();
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
            jsonObject.put("startDay", "2019-04-12");
            jsonObject.put("endDay", "2019-06-12");
            jsonObject.put("dateType", "90");
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("merchantCode", BaseApplication.getmerchantCode());
            map.put("userId", BaseApplication.getuserId());
            map.put("startDay", "2019-04-12");
            map.put("endDay", "2019-06-12");
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
        HttpManager.getHttpManager().postMethod(ApiService.incomeStatement, new Observer<String>() {
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
                        IncomeBean commonalityBean = gson.fromJson(json, IncomeBean.class);
                        if (commonalityBean.getRetCode().equals("SUCCESS")) {
                            mEarning.setText(commonalityBean.getData().getTotalAmt() + "");
                            mBiggest.setText(commonalityBean.getData().getMaxAmt() + "");
                            mDaily.setText(commonalityBean.getData().getAvgAmt() + "");
                            mCompare.setText(commonalityBean.getData().getRatio());
                            mCompare.setTextColor(getActivity().getResources().getColor(R.color.orangered));
                            if (commonalityBean.getData().getRatio().contains("+")) {
                                mCompare.setTextColor(getActivity().getResources().getColor(R.color.lime));
                                mCompare.setText( commonalityBean.getData().getRatio().subSequence(0,1));
                            } else if (commonalityBean.getData().getRatio().contains("-")) {
                                mCompare.setTextColor(getActivity().getResources().getColor(R.color.orangered));
                                mCompare.setText(commonalityBean.getData().getRatio().subSequence(0,1));
                            } else {
                                mCompare.setText(commonalityBean.getData().getRatio());
                            }
                        } else {
                            ToastUitl.show(commonalityBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
