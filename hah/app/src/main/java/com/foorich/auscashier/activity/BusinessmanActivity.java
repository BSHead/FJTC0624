package com.foorich.auscashier.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.adapter.AllMerchantAdapter;
import com.foorich.auscashier.adapter.CityAdapter;
import com.foorich.auscashier.adapter.CountyAdapter;
import com.foorich.auscashier.adapter.ProvinceAdapter;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.AddressBean;
import com.foorich.auscashier.bean.BusinessManBean;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.MerchantsTypeBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.MClearEditText;
import com.foorich.auscashier.view.state.Sofia;
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
 * date   : 2019-5-7 16:56
 * desc   : 个体工商户
 * version: 1.0
 */
public class BusinessmanActivity extends BaseActivity {


    Activity activity;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    //商家名称
    @BindView(R.id.ed_merchants_name)
    MClearEditText EdMerchantsName;
    //负责人
    @BindView(R.id.ed_principal_name)
    MClearEditText EdPrincipalName;
    //联系电话
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    //地址
    @BindView(R.id.ed_address)
    MClearEditText EdAddress;
    //商户类型
    @BindView(R.id.tv_merchants_type)
    TextView mMerchantsType;
    String service = "";
    //商户所在地区
    @BindView(R.id.tv_merchants_region)
    TextView mMerchantsRegion;
    String address = "";

    //商户所在地区
    private AllMerchantAdapter adapter;

    //商户类型
    private View AllMerchantpopView;
    private PopupWindow AllMerchantpopupWindow;
    //商户所在地区
    private View AllAreasApppopView;
    private PopupWindow AllAreasApppopupWindow;

    private int screenWidth;//popupWindow宽度
    private int screenHeight;//popupWindow高度
    ImageView mBack;//商户类型返回
    TextView mTvTitle;//商户类型列表

    RecyclerView ReMerchant;//商户类型列表
    List<MerchantsTypeBean.DataBean> list;//商户类型集合


    AddressBean resultData;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager_city;
    private LinearLayoutManager linearLayoutManager_county;
    RecyclerView province_rv;
    RecyclerView city_rv;
    RecyclerView county_rv;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountyAdapter countyAdapter;


    private TextView tVProvince;
    private TextView tVCity;
    private TextView tVCounty;

    private String StrProvince;
    private String StrCity;
    private String StrCounty;

    private View indicator;

    private static final int INDEX_TAB_PROVINCE = 0;
    private static final int INDEX_TAB_CITY = 1;
    private static final int INDEX_TAB_COUNTY = 2;


    private int tabIndex = INDEX_TAB_PROVINCE;
    String aptitude = "";//判断个人还是企业商户

    @Override
    public int getLayoutId() {
        return R.layout.activity_businessman;
    }

    @Override
    public void initPresenter() {
        activity = this;
        Intent intent = getIntent();
        aptitude = intent.getStringExtra("aptitude");
        mTvPhone.setText(BaseApplication.getloginName());
    }

    @Override
    public void initView() {
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("商户基本信息");

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
        initScreenWidth();
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left, R.id.tv_merchants_type, R.id.tv_merchants_region, R.id.btn_businessman})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_merchants_type://商户类型
                getAllMerchant();
                break;

            case R.id.tv_merchants_region://商户营业地区
                getAllAreasApp();
                break;

            case R.id.btn_businessman://下一步
                String merchantName = EdMerchantsName.getText().toString().trim();
                String legalPerson = EdPrincipalName.getText().toString().trim();
                String linkTel = mTvPhone.getText().toString().trim();

                String addressDetail = EdAddress.getText().toString().trim();
                int type = 1;

                if (merchantName.equals("")) {
                    ToastUitl.show("请输入商家名称", 0);
                } else if (legalPerson.equals("")) {
                    ToastUitl.show("请输入负责人", 0);
                } else if (linkTel.equals("")) {
                    ToastUitl.show("请输入手机号", 0);
                } else if (service.equals("")) {
                    ToastUitl.show("请选择商户类型", 0);
                } else if (address.equals("")) {
                    ToastUitl.show("请选择商户所在地区", 0);
                } else if (addressDetail.equals("")) {
                    ToastUitl.show("请输入详细地址", 0);
                } else {
                    saveOrUpdateMerchantBase(merchantName, legalPerson, linkTel, service, "", address, addressDetail, type);
                }
                break;
        }
    }

    //商户类型弹框
    private void AllMerchantPopWindow() {
        //  适配PopupWindow布局文件
        AllMerchantpopView = View.inflate(this, R.layout.layout_merchant, null);
        backgroundAlpha(0.5f);
        //  创建PopupWindow
        AllMerchantpopupWindow = new PopupWindow(AllMerchantpopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给PopupWindow设置动画
        AllMerchantpopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        AllMerchantpopupWindow.setFocusable(false);
        AllMerchantpopupWindow.setOutsideTouchable(false);
        AllMerchantpopupWindow.setWidth(screenWidth);
        AllMerchantpopupWindow.setHeight(screenHeight / 2 + 300);
        AllMerchantpopupWindow.setBackgroundDrawable(new PaintDrawable());
        AllMerchantpopupWindow.showAtLocation(findViewById(R.id.li_layout), Gravity.BOTTOM, 0, 0);//设置随意位置点击

        //  PopupWindow返回键
        mBack = (ImageView) AllMerchantpopView.findViewById(R.id.iv_back);
        //  PupupWindow标题
        mTvTitle = (TextView) AllMerchantpopView.findViewById(R.id.tv_title);
        mTvTitle.setText("选择商户类型");
        //  银行卡列表的ListView
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllMerchantpopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });

        ReMerchant = (RecyclerView) AllMerchantpopView.findViewById(R.id.re_merchant);
        ReMerchant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ReMerchant.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new AllMerchantAdapter(list, BusinessmanActivity.this);
        adapter.setOnItemClickListener(new AllMerchantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                service = list.get(position).getService();
                mMerchantsType.setText(service);
                AllMerchantpopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        ReMerchant.setAdapter(adapter);

    }

    //商户经营类型
    public void getAllMerchant() {
        SuccinctProgress.showSuccinctProgress(activity,
                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("key", BaseApplication.gettoken());

            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(CommonUtils.CreateLinkStringByGet(map)));
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
        HttpManager.getHttpManager().postMethod(ApiService.getAllMerchant, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        backgroundAlpha(1f);
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                        SuccinctProgress.dismiss();
                    }

                    @Override
                    public void onNext(String json) {
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        MerchantsTypeBean merchantsTypeBean = gson.fromJson(json, MerchantsTypeBean.class);
                        if (merchantsTypeBean.getRetCode().equals("SUCCESS")) {
                            list = merchantsTypeBean.getData();
                            AllMerchantPopWindow();

                        } else {
                            ToastUitl.show("获取商户类型失败", 0);
                        }
                    }
                }, body
        );
    }

    //商户所在地区弹框
    private void AllAreasAppPopWindow() {
        //  适配PopupWindow布局文件
        AllAreasApppopView = View.inflate(this, R.layout.address_selector, null);
        backgroundAlpha(0.5f);
        //  创建PopupWindow
        AllAreasApppopupWindow = new PopupWindow(AllAreasApppopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给PopupWindow设置动画
        AllAreasApppopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        AllAreasApppopupWindow.setFocusable(false);
        AllAreasApppopupWindow.setOutsideTouchable(false);
        AllAreasApppopupWindow.setWidth(screenWidth);
        AllAreasApppopupWindow.setHeight(screenHeight / 2 + 300);
        AllAreasApppopupWindow.setBackgroundDrawable(new PaintDrawable());
        AllAreasApppopupWindow.showAtLocation(findViewById(R.id.li_layout), Gravity.BOTTOM, 0, 0);//设置随意位置点击

        //  PopupWindow返回键
        mBack = (ImageView) AllAreasApppopView.findViewById(R.id.iv_back);
        //  PupupWindow标题
        mTvTitle = (TextView) AllAreasApppopView.findViewById(R.id.tv_title);
        mTvTitle.setText("选择营业地区");
        //  银行卡列表的ListView
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllAreasApppopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });


        indicator = AllAreasApppopView.findViewById(R.id.indicator);

        tVProvince = (TextView) AllAreasApppopView.findViewById(R.id.textViewProvince);
        tVProvince.setVisibility(View.VISIBLE);
        tVCity = (TextView) AllAreasApppopView.findViewById(R.id.textViewCity);
        tVCity.setVisibility(View.GONE);
        tVCounty = (TextView) AllAreasApppopView.findViewById(R.id.textViewCounty);
        tVCounty.setVisibility(View.GONE);

        provinceAdapter = new ProvinceAdapter(mContext);
        cityAdapter = new CityAdapter(mContext);
        countyAdapter = new CountyAdapter(mContext);


        province_rv = (RecyclerView) AllAreasApppopView.findViewById(R.id.province_rv);
        city_rv = (RecyclerView) AllAreasApppopView.findViewById(R.id.city_rv);
        county_rv = (RecyclerView) AllAreasApppopView.findViewById(R.id.county_rv);
        province_rv.setVisibility(View.VISIBLE);
        city_rv.setVisibility(View.GONE);
        county_rv.setVisibility(View.GONE);


        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        province_rv.setLayoutManager(linearLayoutManager);

        linearLayoutManager_city = new LinearLayoutManager(mContext);
        linearLayoutManager_city.setOrientation(LinearLayoutManager.VERTICAL);
        city_rv.setLayoutManager(linearLayoutManager_city);

        linearLayoutManager_county = new LinearLayoutManager(mContext);
        linearLayoutManager_county.setOrientation(LinearLayoutManager.VERTICAL);
        county_rv.setLayoutManager(linearLayoutManager_county);


        province_rv.setAdapter(provinceAdapter);
        provinceAdapter.setData(resultData.getData());

        provinceAdapter.setOnItemClickListener(new ProvinceAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, final int pos, String regionName) {
                //data为内容，position为点击的位置
                Log.i("", "data: " + data + ",position: " + pos + "regionName： " + regionName + "regionId: ");

                StrProvince = regionName;
                tVProvince.setText(regionName);
                tVProvince.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                tVCity.setTextColor(mContext.getResources().getColor(R.color.black));

                tabIndex = INDEX_TAB_PROVINCE;
                updateIndicator();

                province_rv.setVisibility(View.GONE);
                city_rv.setVisibility(View.VISIBLE);
                tVCity.setVisibility(View.VISIBLE);
                tVCity.setText("请选择");

                city_rv.setAdapter(cityAdapter);
                cityAdapter.setData(resultData.getData().get(pos).getCity());
                cityAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data, int position, String regionName) {

                        StrCity = regionName;

                        tVCity.setText(regionName);

                        tabIndex = INDEX_TAB_CITY;
                        updateIndicator();

                        tVProvince.setTextColor(mContext.getResources().getColor(R.color.black));
                        tVCity.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        tVCounty.setTextColor(mContext.getResources().getColor(R.color.black));


                        province_rv.setVisibility(View.GONE);
                        city_rv.setVisibility(View.GONE);
                        county_rv.setVisibility(View.VISIBLE);
                        tVCounty.setVisibility(View.VISIBLE);
                        tVCounty.setText("请选择");

                        county_rv.setAdapter(countyAdapter);
                        countyAdapter.setData(resultData.getData().get(pos).getCity());
                        countyAdapter.setOnItemClickListener(new CountyAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, String data, int position, String regionName) {

                                StrCounty = regionName;

                                tVCounty.setText(regionName);

                                tabIndex = INDEX_TAB_COUNTY;
                                updateIndicator();

                                tVProvince.setTextColor(mContext.getResources().getColor(R.color.black));
                                tVCity.setTextColor(mContext.getResources().getColor(R.color.black));
                                tVCounty.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                                address = StrProvince + StrCity + StrCounty;
                                mMerchantsRegion.setText(address);
                                AllAreasApppopupWindow.dismiss();
                                backgroundAlpha(1f);
                                LogUtil.e("+++", "省： " + StrProvince + "市：   " + StrCity + "区：   " + StrCounty);
                            }
                        });
                    }
                });
            }
        });

        initListener();

    }

    //商户所在地区监听
    private void initListener() {

        tVProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_rv.setVisibility(View.GONE);
                county_rv.setVisibility(View.GONE);
                tVProvince.setText("请选择");
                tVCity.setText("");
                tVCounty.setText("");
                province_rv.setVisibility(View.VISIBLE);
                tVCity.setVisibility(View.GONE);
                tVCounty.setVisibility(View.GONE);
                tVProvince.setTextColor(mContext.getResources().getColor(R.color.black));
                tVCity.setTextColor(mContext.getResources().getColor(R.color.black));
                tVCounty.setTextColor(mContext.getResources().getColor(R.color.black));


                tabIndex = INDEX_TAB_PROVINCE;
                updateIndicator();


            }
        });

        tVCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                province_rv.setVisibility(View.GONE);
                city_rv.setVisibility(View.VISIBLE);
                county_rv.setVisibility(View.GONE);
                tVCity.setText("请选择");
                tVCounty.setText("");
                tVCity.setVisibility(View.VISIBLE);
                tVCounty.setVisibility(View.VISIBLE);

                tVProvince.setTextColor(mContext.getResources().getColor(R.color.black));
                tVCity.setTextColor(mContext.getResources().getColor(R.color.black));
                tVCounty.setTextColor(mContext.getResources().getColor(R.color.black));

                tabIndex = INDEX_TAB_CITY;
                updateIndicator();

            }
        });


        tVCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabIndex = INDEX_TAB_COUNTY;
                updateIndicator();


            }
        });
    }

    //商户所在地区更新
    private void updateIndicator() {
        AllAreasApppopView.post(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case INDEX_TAB_PROVINCE:
                        buildIndicatorAnimatorTowards(tVProvince).start();
                        break;
                    case INDEX_TAB_CITY:
                        buildIndicatorAnimatorTowards(tVCity).start();
                        break;
                    case INDEX_TAB_COUNTY:
                        buildIndicatorAnimatorTowards(tVCounty).start();
                        break;
                }
            }
        });
    }

    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }

    //商户所在地区
    public void getAllAreasApp() {
        SuccinctProgress.showSuccinctProgress(activity,
                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("key", BaseApplication.gettoken());

            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(CommonUtils.CreateLinkStringByGet(map)));
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
        HttpManager.getHttpManager().postMethod(ApiService.getAllAreasApp, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        backgroundAlpha(1f);
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        } else if (e.getMessage().contains("Failed to connect to")) {
                            ToastUitl.show("服务器异常,请重试", 0);
                        } else {
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                        SuccinctProgress.dismiss();
                    }

                    @Override
                    public void onNext(String json) {
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();

                        resultData = gson.fromJson(json, AddressBean.class);
                        if (resultData.getRetCode().equals("SUCCESS")) {
                            AllAreasAppPopWindow();
                        } else {
                            ToastUitl.show("获取所在地区失败:" + resultData.getMessage(), 0);
                        }

                    }
                }, body
        );
    }

    /*
     * 查看自身的宽高
     */
    public void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = BaseApplication.getContext().getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    /*
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    //保存商户资料
    public void saveOrUpdateMerchantBase(String merchantName, String legalPerson, String linkTel, String service, String mccCode, String address, String addressDetail, int type) {
        SuccinctProgress.showSuccinctProgress(activity,
                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("loginName", BaseApplication.getloginName());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("merchantName", merchantName);
            jsonObject.put("legalPerson", legalPerson);
            jsonObject.put("linkTel", linkTel);
            jsonObject.put("service", service);
            jsonObject.put("mccCode", mccCode);
            jsonObject.put("address", address);
            jsonObject.put("addressDetail", addressDetail);
            jsonObject.put("type", type);

            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("merchantName", merchantName);
            map.put("legalPerson", legalPerson);
            map.put("linkTel", linkTel);
            map.put("service", service);
            map.put("mccCode", mccCode);
            map.put("address", address);
            map.put("addressDetail", addressDetail);
            map.put("type", String.valueOf(type));
            map.put("key", BaseApplication.gettoken());

            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(CommonUtils.CreateLinkStringByGet(map)));
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
        HttpManager.getHttpManager().postMethod(ApiService.saveOrUpdateMerchantBase, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("保存商户信息页面", "错误提示：" + e.getMessage());
                        SuccinctProgress.dismiss();
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
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        BusinessManBean businessManBean = gson.fromJson(json, BusinessManBean.class);
                        if (businessManBean.getRetCode().equals("SUCCESS")) {

                            if(aptitude.equals("0")){//企业
                                BaseApplication.savemerchantCode(businessManBean.getData().getMerchantCode());
                                startActivity(EnterpriseActivity.class);
                            }else{//个人
                                BaseApplication.savemerchantCode(businessManBean.getData().getMerchantCode());
                                startActivity(QualificationActivity.class);
                            }

                        } else {
                            ToastUitl.show(businessManBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
