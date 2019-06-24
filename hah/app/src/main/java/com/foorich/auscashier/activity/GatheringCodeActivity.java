package com.foorich.auscashier.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.GatheringCodeBean;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.QRCodeUtils;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-10 14:36
 * desc   : 收款码页面
 * version: 1.0
 */
public class GatheringCodeActivity extends BaseActivity {

    //上下文
    Activity activity;
    //标题
    @BindView(R.id.tv_pay_title)
    TextView Toolbar_title;
    //返回
    @BindView(R.id.imageView3)
    ImageView mCodeCreate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gathering_code;
    }

    @Override
    public void initPresenter() {
        activity = this;
    }

    @Override
    public void initView() {
        Toolbar_title.setText("商户收款码收款");

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .invasionStatusBar()
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.transparent))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));

        //如果oneYardPayLink属性为空，走网络请求获取商户静态码
        LogUtil.e("++++++++++", "" + BaseApplication.getoneYardPayLink());
        if (BaseApplication.getoneYardPayLink().equals("") || BaseApplication.getoneYardPayLink() == null) {
            QrcodeTokenUser();//走网络请求
        } else {
            twoCodeCreate(BaseApplication.getoneYardPayLink());//否则直接调取二维码
        }

    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.img_pay_back, R.id.tv_save})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.img_pay_back://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_save://保存
                //如果保存图片小于6.0权限，要求申请权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //没有权限则申请权限
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        //有权限直接执行,docode()不用做处理
                        setDecorViewImage(activity);
                    }
                } else {
                    //小于6.0，不用申请权限，直接执行
                    setDecorViewImage(activity);
                }
                break;
        }
    }


    //获取商户静态码
    public void QrcodeTokenUser() {
        {
            SuccinctProgress.showSuccinctProgress(activity,
                    getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
            //提交后台参数
            JSONObject json = new JSONObject();
            try {
                //data里面参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", BaseApplication.getuserId());
                jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
                jsonObject.put("storeCode", BaseApplication.getstoreCode());

                //拆分data里面key、value进行拼接cardnum
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", BaseApplication.getuserId());
                map.put("merchantCode", BaseApplication.getmerchantCode());
                map.put("storeCode", BaseApplication.getstoreCode());
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
            HttpManager.getHttpManager().postMethod(ApiService.QrcodeTokenUser, new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("收款页面", "错误提示：" + e.getMessage());
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
                            GatheringCodeBean gatheringCodeBean = gson.fromJson(json, GatheringCodeBean.class);
                            if (gatheringCodeBean.getRetCode().equals("SUCCESS")) {
                                twoCodeCreate(gatheringCodeBean.getData().getOneYardPayLink());//获取二维码地址
                                BaseApplication.saveoneYardPayLink(gatheringCodeBean.getData().getOneYardPayLink());
                            } else {
                                ToastUitl.show("商户码获取失败", 0);
                            }
                        }
                    }, body
            );
        }
    }

    //生成分享二维码
    private void twoCodeCreate(String url) {
        Bitmap bitmap = QRCodeUtils.createQRCodeWithLogo(url, 1000,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ls_bdqb));
        mCodeCreate.setImageBitmap(bitmap);
    }

    /**
     * 保存付款码
     */
    public void saveBitmap(Bitmap bm) {
        String fileName = System.currentTimeMillis() + ".jpg";//命名一样,防止多次保存出现多个一样的图片文件
        MediaStore.Images.Media.insertImage(getContentResolver(), bm, fileName, "");
        //发送系统通知消息
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//如果是4.4及以上版本
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File("/sdcard/XFB_C/phontos")); //输出目录
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);
        } else {
            //<=4.4版本使用,不然高版本直接权限拒绝崩溃
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
        ToastUitl.show("保存成功", 0);
    }

    //截图
    public boolean setDecorViewImage(Activity activity) {
        try {
            //整个手机屏幕的视图
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();

            Bitmap bitmap = view.getDrawingCache();

            // 获取状态栏高度
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            Log.i("TAG", "" + statusBarHeight);

            // 获取屏幕长和高
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            int height = activity.getWindowManager().getDefaultDisplay().getHeight();

            Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
//            savePic(b, activity);
            saveBitmap(b);
//            View view = activity.getWindow().getDecorView();
//            view.setDrawingCacheEnabled(true);
//            view.buildDrawingCache();
//            Bitmap bmp = view.getDrawingCache();
//            int width = getScreenWidth(activity);
//            int height = getScreenHeight(activity);
//            Bitmap bp = null;
//            bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
//            view.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    //    public void download() {
//        saveBitmap(convertViewToBitmap(mCodeCreate));
//    }
//
//    public Bitmap convertViewToBitmap(View view) {
//        int w = view.getWidth();
//        int h = view.getHeight();
//        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bmp);
//        c.drawColor(Color.WHITE);
//        /** 如果不设置canvas画布为白色，则生成透明 */
//        //view.layout(0, 0, w, h);
//        view.draw(c);
//        return bmp;
//    }

}
