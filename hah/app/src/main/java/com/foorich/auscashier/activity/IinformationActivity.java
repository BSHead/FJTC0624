package com.foorich.auscashier.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foorich.auscashier.MainActivity;
import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.UploadPhotoBean;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.GlideCacheUtil;
import com.foorich.auscashier.utils.GlideRoundTransformUtil;
import com.foorich.auscashier.utils.LogUtil;
import com.foorich.auscashier.utils.SuccinctProgress;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.BottomPopupOption;
import com.foorich.auscashier.view.state.Sofia;
import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-07 14:14
 * desc   : 个人信息页面
 * version: 1.0
 */
public class IinformationActivity extends BaseActivity {


    private AlertDialog myDialog;
    Activity activity;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mBack;
    //头像
    @BindView(R.id.img_head_formation)
    ImageView mImgHead;
    //商户昵称
    @BindView(R.id.tv_name_formation)
    TextView mFormation;
    //商户名称
    @BindView(R.id.tv_merchantName)
    TextView mMerchantName;
    //商户地址
    @BindView(R.id.tv_provinceCode)
    TextView mProvinceCode;
    //商户营业执照号
    @BindView(R.id.tv_license)
    TextView mLicense;
    //商户营业执照号
    @BindView(R.id.tv_phone)
    TextView mPhone;
    //商户营业执照号
    @BindView(R.id.tv_accountCode)
    TextView mAccountCode;


    //商户昵称
//    @BindView(R.id.tv_name)
//    TextView mName;

    BottomPopupOption bottomPopupOption;
    private final int IMAGE_RESULT_CODE = 2;//选择图库
    private final int PICK = 1;//选择拍照
    static File photoFile; //在指定路径下创建文件
    private String Hrad;//临时头像
    public String data = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_iinformation;
    }

    @Override
    public void initPresenter() {
        Glide.with(IinformationActivity.this).load(BaseApplication.getheadImgUrl()).transform(new GlideRoundTransformUtil(IinformationActivity.this)).into(mImgHead);
        //接收广播消息
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void initView() {
        activity = this;
        myDialog = new AlertDialog(this).builder();
        mBack.setVisibility(View.VISIBLE);
        bottomPopupOption = new BottomPopupOption(IinformationActivity.this);
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
        mFormation.setText(BaseApplication.getnickName());
        mMerchantName.setText(BaseApplication.getmerchantName());
        mProvinceCode.setText(BaseApplication.getprovinceCode());
        mLicense.setText(BaseApplication.getlicense());
        mPhone.setText(BaseApplication.getloginName());
        mAccountCode.setText(BaseApplication.getAccountCode());
    }


    //多个控件具有相同的点击事件
    @OnClick({ R.id.li_about_head, R.id.toolbar_left, R.id.li_phone, R.id.li_license, R.id.tv_provinceCode,R.id.li_nickname})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                Intent intent = new Intent();
                intent.setAction("action.refresh");
                sendBroadcast(intent);
                AppManager.getAppManager().returnToActivity(MainActivity.class);
                break;

            case R.id.li_about_head://头像
                bottomPopupOption.setItemText("拍照", "从手机相册选择");
                // bottomPopupOption.setColors();//设置颜色
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            takePhoto();//拍照
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            if (ContextCompat.checkSelfPermission(IinformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                                ActivityCompat.requestPermissions(IinformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_RESULT_CODE);
                            } else {
                                //已授权，获取照片
                                choosePhoto();//选择相册
                            }
                            bottomPopupOption.dismiss();
                        }
                    }
                });
                break;

            case R.id.li_nickname://商户昵称
                startActivity(ChangeNicknameActivity.class);
                break;


//            case R.id.li_phone://注册账户
//                ToastUitl.show("注册账户", 0);
//                break;
//
//            case R.id.li_license://营业执照号
//                ToastUitl.show("营业执照号", 0);
//                break;
//
//            case R.id.tv_provinceCode://营业地址
//                ToastUitl.show("营业地址", 0);
//                break;



        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //拍照
            case PICK:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    savaPhotoToSDCard("/sdcard/XFB_C/phontos", createFileName(), bitmap);
                    uploadFront(photoFile.getPath());
                } else {
                    ToastUitl.show("获取资源失败", 0);
                }
                break;

            //选取照片
            case IMAGE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    uploadFront(getRealPathFromURI(uri));
                }
                break;
        }
    }

    //上传图片
    public void uploadFront(String url) {
        SuccinctProgress.showSuccinctProgress(IinformationActivity.this,
                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //请求接口
        HttpManager.getHttpManager().upLoadIcon(ApiService.upload, url, "file", "", new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
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
            public void onNext(ResponseBody responseBody) {
                SuccinctProgress.dismiss();
                try {
                    Gson gson = new Gson();
                    UploadPhotoBean uploadPhotoBean = gson.fromJson(responseBody.string(), UploadPhotoBean.class);

                    if (uploadPhotoBean.getRetCode().equals("SUCCESS")) {
                        doorPhoto(uploadPhotoBean.getData().getUrl());
                        Hrad = uploadPhotoBean.getData().getUrl();
                    } else {
                        ToastUitl.show(uploadPhotoBean.getMessage(), 0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    //用户修改头像
    public void doorPhoto(String url) {
        {
            SuccinctProgress.showSuccinctProgress(IinformationActivity.this,
                    "上传中···", SuccinctProgress.THEME_ARC, false, true);
            //提交后台参数
            JSONObject json = new JSONObject();
            try {
                //data里面参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", BaseApplication.getuserId());
                jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
                jsonObject.put("photodoor", url);
                jsonObject.put("doorPhotoType", "0");

                //拆分data里面key、value进行拼接cardnum
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", BaseApplication.getuserId());
                map.put("merchantCode", BaseApplication.getmerchantCode());
                map.put("photodoor", url);
                map.put("doorPhotoType", "0");
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
            HttpManager.getHttpManager().postMethod(ApiService.doorPhoto, new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("个人页面", "错误提示：" + e.getMessage());
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
                            CommonalityBean commonalityBean = gson.fromJson(json, CommonalityBean.class);
                            if (commonalityBean.getRetCode().equals("SUCCESS")) {
                                BaseApplication.saveheadImgUrl(Hrad);
                                Glide.with(IinformationActivity.this).load(Hrad).transform(new GlideRoundTransformUtil(IinformationActivity.this)).into(mImgHead);

                            } else {
                                ToastUitl.show(commonalityBean.getMessage(), 0);
                            }
                        }
                    }, body
            );
        }
    }


    //选择图片
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_RESULT_CODE);
    }


    //拍照调取系统相机
    private void takePhoto() {
        //获取动态相机权限
        AndPermission.with(this).permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK);
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ToastUitl.show("没有权限无法扫描呦", 0);
            }
        }).start();
    }


    //获取本地图片路径
    private String getRealPathFromURI(Uri contentUri) { //传入图片uri地址
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    ///以系统时间命名图片
    public static String createFileName() {
        String fileName = "";
        //系统当前时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }


    //把图片保存到存储卡
    public static void savaPhotoToSDCard(String path, String photoName, Bitmap photoBitmp) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //创建路径，这个path保存路径
            //privite static final String PATH = "/sdcard/XFBQB/phontos";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            photoFile = new File(path, photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmp != null) {
                    if (photoBitmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                mFormation.setText(BaseApplication.getnickName());
            }
        }
    };

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

}
