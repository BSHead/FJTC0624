package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CaptureBean;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;
import com.foorich.auscashier.view.zing.android.CaptureActivityHandler;
import com.foorich.auscashier.view.zing.android.FinishListener;
import com.foorich.auscashier.view.zing.android.InactivityTimer;
import com.foorich.auscashier.view.zing.bean.ZxingConfig;
import com.foorich.auscashier.view.zing.camera.CameraManager;
import com.foorich.auscashier.view.zing.common.Constant;
import com.foorich.auscashier.view.zing.decode.DecodeImgCallback;
import com.foorich.auscashier.view.zing.decode.DecodeImgThread;
import com.foorich.auscashier.view.zing.decode.ImageUtil;
import com.foorich.auscashier.view.zing.view.ViewfinderView;
import com.google.gson.Gson;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-13 14:19
 * desc   : 扫一扫
 * version: 1.0
 */

public class CaptureActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {


    private static final String TAG = CaptureActivity.class.getSimpleName();
    private ZxingConfig config;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private ImageView flashLightIv;
    private TextView flashLightTv;
    private LinearLayout flashLightLayout;
    private LinearLayout albumLayout;
    private LinearLayout bottomLayout;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    //    private BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private ImageView ivBack;
    private AlertDialog myDialog;
    String orderNum = "";
    private MyThreadPay threadpay;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myDialog = new AlertDialog(this).builder();
        Log.i("onCreate", "setContentView");
        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {

            Log.i("config", e.toString());
        }

        if (config == null) {
            config = new ZxingConfig();
        }


        setContentView(R.layout.activity_capture);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initView();

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
//        beepManager = new BeepManager(this);
//        beepManager.setPlayBeep(config.isPlayBeep());
//        beepManager.setVibrate(config.isShake());

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .invasionStatusBar()
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.viewfinder_mask))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.white));

    }


    private void initView() {

        previewView = (SurfaceView) findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setOnClickListener(this);


        //相机、相册
//        flashLightIv = (ImageView) findViewById(R.id.flashLightIv);
//        flashLightTv = (TextView) findViewById(R.id.flashLightTv);
//        flashLightLayout = (LinearLayout) findViewById(R.id.flashLightLayout);
//        flashLightLayout.setOnClickListener(this);
//        albumLayout = (LinearLayout) findViewById(R.id.albumLayout);
//        albumLayout.setOnClickListener(this);
//        bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);


//        switchVisibility(bottomLayout, config.isShowbottomLayout());
//        switchVisibility(flashLightLayout, config.isShowFlashLight());
//        switchVisibility(albumLayout, config.isShowAlbum());
//
//
//        /*有闪光灯就显示手电筒按钮  否则不显示*/
//        if (isSupportCameraLedFlash(getPackageManager())) {
//            flashLightLayout.setVisibility(View.VISIBLE);
//        } else {
//            flashLightLayout.setVisibility(View.GONE);
//        }
    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

//        if (flashState == Constant.FLASH_OPEN) {
//            flashLightIv.setImageResource(R.drawable.ic_open);
//            flashLightTv.setText("关闭闪光灯");
//        } else {
//            flashLightIv.setImageResource(R.drawable.ic_close);
//            flashLightTv.setText("打开闪光灯");
//        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(final Result rawResult) {
        inactivityTimer.onActivity();
        SuccinctProgress.showSuccinctProgress(CaptureActivity.this,
                "加载中···", SuccinctProgress.THEME_ARC, false, true);

        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
            jsonObject.put("amt", "0.01");
            jsonObject.put("auth_code", rawResult.getText());
            jsonObject.put("storeCode", BaseApplication.getstoreCode());
//            jsonObject.put("apptype", "Cambodia");

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("merchantCode", BaseApplication.getmerchantCode());
            map.put("amt", "0.01");
            map.put("auth_code", rawResult.getText());
            map.put("storeCode", BaseApplication.getstoreCode());
//            map.put("apptype", "Cambodia");
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
        HttpManager.getHttpManager().postMethod(ApiService.doPay, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SuccinctProgress.dismiss();
                        myDialog.setGone().setMsg("请扫描正确的二维码").setCancelable(false).setNegativeButton("请重试", R.color.orange, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                continuePreview();
                            }
                        }).show();
                    }

                    @Override
                    public void onNext(String json) {
//                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        CaptureBean captureBean = gson.fromJson(json, CaptureBean.class);
                        //扫描成功
                        if (captureBean.getRetCode().equals("SUCCESS")) {
                            orderNum = captureBean.getData().getOrderNum();
                            start();
//                            Intent intent = getIntent();
//                            intent.putExtra(Constant.CODED_CONTENT, rawResult.getText());//商户码
//                            intent.putExtra("Logo", merchantBean.getData().getDoorphoto());//商户头像
//                            intent.putExtra("Name", merchantBean.getData().getMerchantName());//商户名
//                            setResult(RESULT_OK, intent);
//                            finish();
                        } else {
                            SuccinctProgress.dismiss();
                            myDialog.setGone().setMsg(captureBean.getMessage()).setCancelable(false).setNegativeButton("请重试", R.color.orange, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    continuePreview();
                                }
                            }).show();
                        }
                    }
                }, body
        );


    }


    private void switchVisibility(View view, boolean b) {
        if (b) {
//            view.setVisibility(View.VISIBLE);
        } else {
//            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication());

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

//        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("收款");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
//        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void onClick(View view) {
/*
        int id = view.getId();
        if (id == R.id.flashLightLayout) {
            *//*切换闪光灯*//*
            cameraManager.switchFlashLight(handler);
        } else if (id == R.id.albumLayout) {
            *//*打开相册*//*
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, Constant.REQUEST_IMAGE);
        } else if (id == R.id.iv_back) {
            finish();
        }*/

        switch (view.getId()) {
            case R.id.iv_back:
                stop();
                finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_IMAGE && resultCode == RESULT_OK) {
            String path = ImageUtil.getImageAbsolutePath(this, data.getData());

            new DecodeImgThread(path, new DecodeImgCallback() {
                @Override
                public void onImageDecodeSuccess(Result result) {
                    handleDecode(result);
                }

                @Override
                public void onImageDecodeFailed() {
                    Toast.makeText(CaptureActivity.this, "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                }
            }).run();

        }
    }

    /*
     * 继续扫描
     */
    private void continuePreview() {
        SurfaceHolder surfaceHolder = previewView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null) {
            handler.restartPreviewAndDecode();
        }
    }


    //查询订单号
    public void OrderInquiry(String orderNum) {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("orderNum", orderNum);
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("orderNum", orderNum);
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
        HttpManager.getHttpManager().postMethod(ApiService.getOrder, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SuccinctProgress.dismiss();
                        continuePreview();
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
                        CaptureBean captureBean = gson.fromJson(json, CaptureBean.class);
                        if (captureBean.getRetCode().equals("SUCCESS")) {
                            String tradeState = String.valueOf(captureBean.getData().getTradeState());
                            if (tradeState.equals("1")) {
                                Intent intent = new Intent(CaptureActivity.this, GatheringActivity.class);
                                intent.putExtra("amt", "" + captureBean.getData().getAmt());//收款金额
                                intent.putExtra("channelCode", captureBean.getData().getChannelPayTypeId());//收款方式
                                intent.putExtra("orderNum", "" + captureBean.getData().getOrderNum());//订单号
                                intent.putExtra("createTime", "" + captureBean.getData().getCreateTime());//交易时间
                                intent.putExtra("remarks", captureBean.getData().getRemarks());//备注
                                startActivity(intent);
                                stop();
                                finish();
                            } else {
//                                SuccinctProgress.dismiss();
//                                myDialog.setGone().setMsg("该订单已失效").setCancelable(false).setNegativeButton("请重新扫描", R.color.orange, new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        continuePreview();
//                                    }
//                                }).show();
                            }
                        } else {
                            myDialog.setGone().setMsg("网络连接超时").setCancelable(false).setNegativeButton("请重新扫描", R.color.orange, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    continuePreview();
                                }
                            }).show();
                        }
                    }
                }, body
        );
    }

    /**
     * 查询订单2秒更新一次
     */
    private class MyThreadPay extends Thread {

        public boolean stop;

        public void run() {
            while (!stop) {
                // 处理功能
                LogUtil.e("---", "2秒到了");
                OrderInquiry(orderNum);
                // 通过睡眠线程来设置定时时间
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 启动线程
     */
    private void start() {
        if (threadpay == null) {
            threadpay = new MyThreadPay();
            threadpay.start();
        }
    }

    /**
     * 停止线程
     */
    private void stop() {
        if (threadpay != null) {
            threadpay.stop = true;
            threadpay = null;
        }
    }

    //退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stop();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
