package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.Item;
import com.foorich.auscashier.bean.ShareBean;
import com.foorich.auscashier.utils.BottomDialog;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.OnItemClickListener;
import com.foorich.auscashier.utils.QRCodeUtils;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

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
 * date   : 2019-5-16 14:25
 * desc   : 分享页面
 * version: 1.0
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {


    //返回
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    //分享二维码
    @BindView(R.id.img_share)
    ImageView mImgShare;
    //分享按钮
    @BindView(R.id.btn_share)
    Button mBtnShare;

    String Details;//分享描述
    String Title;//分享标题
    String Logo;//分享图片
    String Url;//分享URL


    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setOnClickListener(this);
        mToolbarTitle.setText("分享给好友");
        mToolbarLeft.setVisibility(View.VISIBLE);
        mToolbarLeft.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);


        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        ShareCode();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.toolbar_left:
                finish();
                break;

            case R.id.btn_share:
                new BottomDialog(ShareActivity.this)
//                        .title(R.string.share_title)
                        .orientation(BottomDialog.HORIZONTAL)
                        .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                            @Override
                            public void click(Item item) {
                                final UMWeb web = new UMWeb("http:192.168.2.138/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null/aboutUs/aboutUsPage?null"); //切记切记 这里分享的链接必须是http开头
                                web.setTitle(Title);//标题
//                                  web.setThumb(image);  //缩略图
                                web.setDescription(Details);//描述

                                if (item.getTitle().equals("QQ好友")) {
                                    new ShareAction(ShareActivity.this)
                                            .setPlatform(SHARE_MEDIA.QQ)
                                            .withMedia(web)
                                            .setCallback(shareListener)
                                            .share();

                                } else if (item.getTitle().equals("微信好友")) {

                                    new ShareAction(ShareActivity.this)
                                            .setPlatform(SHARE_MEDIA.WEIXIN)
                                            .withMedia(web)
                                            .setCallback(shareListener)
                                            .share();

                                } else if (item.getTitle().equals("朋友圈")) {
                                    new ShareAction(ShareActivity.this)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .withMedia(web)
                                            .setCallback(shareListener)
                                            .share();

                                } else if (item.getTitle().equals("新浪微博")) {
                                    new ShareAction(ShareActivity.this)
                                            .setPlatform(SHARE_MEDIA.SINA)
                                            .withMedia(web)
                                            .setCallback(shareListener)
                                            .share();

                                }
                            }
                        })
                        .show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };


    //生成分享二维码
    private void twoCodeCreate(String url) {
        Bitmap bitmap = QRCodeUtils.createQRCode(url, 600
        );
        mImgShare.setImageBitmap(bitmap);
    }


    //分享好友
    public void ShareCode() {

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
        HttpManager.getHttpManager().postMethod(ApiService.ShareUrl, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("我的页面", "错误提示：" + e.getMessage());
                        if (e.getMessage().equals("connect timed out")) {
                            ToastUitl.show("网络连接超时", 0);
                        }else if(e.getMessage().contains("Failed to connect to")){
                            ToastUitl.show("服务器异常,请重试", 0);
                        }else{
                            ToastUitl.show("服务器异常,请重试", 0);
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        ShareBean shareBean = gson.fromJson(json, ShareBean.class);
                        if (shareBean.getRetCode().equals("SUCCESS")) {
                            twoCodeCreate(shareBean.getData().getGenerateUrl());//获取二维码地址
                            Details = shareBean.getData().getDETAILS();//分享描述
                            Title = shareBean.getData().getTITLE();//分享人手机号
                            Logo = shareBean.getData().getFX_logo();//分享图片
                            Url = shareBean.getData().getGenerateUrl();
                        } else {
                            ToastUitl.show("二维码获取失败", 0);
                        }
                    }
                }, body
        );
    }
}
