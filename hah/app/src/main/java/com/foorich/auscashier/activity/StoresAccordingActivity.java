package com.foorich.auscashier.activity;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foorich.auscashier.R;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.CommonalityBean;
import com.foorich.auscashier.bean.DepositBean;
import com.foorich.auscashier.bean.StoresBean;
import com.foorich.auscashier.bean.UploadPhotoBean;
import com.foorich.auscashier.utils.CommonUtils;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
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
 * date   : 2019-5-7 16:56
 * desc   : 上传门店照
 * version: 1.0
 */
public class StoresAccordingActivity extends BaseActivity {


    Activity activity;
    //标题
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    //返回
    @BindView(R.id.toolbar_left)
    ImageView mLeft;
    //营业执照
    @BindView(R.id.img_license_view)
    ImageView mLicense;
    //营业执照小图标
    @BindView(R.id.img_license_img)
    ImageView mLicenseImg;
    //店内执照
    @BindView(R.id.img_shop_view)
    ImageView mShop;
    //店内执照小图标
    @BindView(R.id.img_shop_img)
    ImageView mShopImg;


    BottomPopupOption bottomPopupOption;
    private final int IMAGE_RESULT_CODE = 2;//选择图库
    private final int PICK = 1;//选择拍照
    private int TYPE;//选择拍照类型（1、营业照、2店内照）
    static File photoFile; //在指定路径下创建文件

    String photoAccording = "";//营业照
    String photoHouse = "";//店内照

    @Override
    public int getLayoutId() {
        return R.layout.activity_stores_according;
    }

    @Override
    public void initPresenter() {
        activity = this;
    }

    @Override
    public void initView() {
        mLeft.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("上传门店照");
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
        bottomPopupOption = new BottomPopupOption(activity);
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_left, R.id.btn_stores, R.id.re_license_img, R.id.re_shop_price})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.btn_stores://下一步

                if (photoAccording.equals("")) {
                    ToastUitl.show("要上传门店照哟！", 0);
                } else if (photoHouse.equals("")) {
                    ToastUitl.show("要上传店内照哟！", 0);
                } else {
                    saveOrUpdatePhoto(photoAccording, photoHouse + "," + photoHouse);
                }
                break;

            case R.id.re_license_img://营业执照
                TYPE = 1;
                if (TYPE == 1) {
                    bottomPopupOption.setItemText("拍照", "从手机相册选择");
                    // bottomPopupOption.setColors();//设置颜色
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                takePhoto();
                                bottomPopupOption.dismiss();
                            } else if (position == 1) {
                                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_RESULT_CODE);
                                } else {
                                    //已授权，获取照片
                                    choosePhoto();
                                }
                                bottomPopupOption.dismiss();
                            }
                        }
                    });
                }

                break;

            case R.id.re_shop_price://店内照
                TYPE = 2;
                if (TYPE == 2) {
                    bottomPopupOption.setItemText("拍照", "从手机相册选择");
                    // bottomPopupOption.setColors();//设置颜色
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                takePhoto();
                                bottomPopupOption.dismiss();
                            } else if (position == 1) {
                                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_RESULT_CODE);
                                } else {
                                    //已授权，获取照片
                                    choosePhoto();
                                }
                                bottomPopupOption.dismiss();
                            }
                        }
                    });
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //拍照
            case PICK:
                if (resultCode == RESULT_OK) {
                    if (TYPE == 1) {
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        savaPhotoToSDCard("/sdcard/XFB/phontos", createFileName(), bitmap);
                        uploadphotoAccording(photoFile.getPath());
                    } else {
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        savaPhotoToSDCard("/sdcard/XFB/phontos", createFileName(), bitmap);
                        uploadHouse(photoFile.getPath());
                    }
                } else {
                    ToastUitl.show("获取资源失败", 0);
                }
                break;

            //选取照片
            case IMAGE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    if (TYPE == 1) {
                        Uri uri = data.getData();
                        uploadphotoAccording(getRealPathFromURI(uri));
                    } else {
                        Uri uri = data.getData();
                        uploadHouse(getRealPathFromURI(uri));
                    }
                }
                break;
        }
    }


    //上传营业执照
    public void uploadphotoAccording(String url) {
        SuccinctProgress.showSuccinctProgress(activity,
                "加载中···", SuccinctProgress.THEME_ARC, false, true);
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
                        Glide.with(activity).load(uploadPhotoBean.getData().getUrl()).into(mLicense);
                        mLicenseImg.setVisibility(View.GONE);
                        photoAccording = "" + uploadPhotoBean.getData().getUrl();
                    } else {
                        ToastUitl.show(uploadPhotoBean.getMessage(), 0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    //上传店内照片
    public void uploadHouse(String url) {
        SuccinctProgress.showSuccinctProgress(activity,
                "加载中···", SuccinctProgress.THEME_ARC, false, true);
        //请求接口
        HttpManager.getHttpManager().upLoadIcon(ApiService.upload, url, "file", "", new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                SuccinctProgress.dismiss();
                LogUtil.e("实名认证页面", "错误提示：" + e.getMessage());
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
                        Glide.with(activity).load(uploadPhotoBean.getData().getUrl()).into(mShop);
                        mShopImg.setVisibility(View.GONE);
                        photoHouse = "" + uploadPhotoBean.getData().getUrl();
                    } else {
                        ToastUitl.show(uploadPhotoBean.getMessage(), 0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 权限申请结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK:   //拍照权限申请返回
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
                break;
            case IMAGE_RESULT_CODE:   //相册选择照片权限申请返回
                choosePhoto();
                break;
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


    //保存门店照片
    public void saveOrUpdatePhoto(String storePhotoP, String shopPhotoP) {
//        SuccinctProgress.showSuccinctProgress(activity,
//                getString(R.string.lode), SuccinctProgress.THEME_ARC, false, true);
        //提交后台参数
        JSONObject json = new JSONObject();
        try {
            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("storePhotoP", storePhotoP);
            jsonObject.put("shopPhotoP", shopPhotoP);
            jsonObject.put("merchantCode", BaseApplication.getmerchantCode());
            jsonObject.put("userId", BaseApplication.getuserId());
            jsonObject.put("key", BaseApplication.gettoken());
            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("storePhotoP", storePhotoP);
            map.put("shopPhotoP", shopPhotoP);
            map.put("merchantCode", BaseApplication.getmerchantCode());
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
        HttpManager.getHttpManager().postMethod(ApiService.saveOrUpdatePhoto, new Observer<String>() {
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
                        SuccinctProgress.dismiss();
                        Gson gson = new Gson();
                        StoresBean storesBean = gson.fromJson(json, StoresBean.class);
                        if (storesBean.getRetCode().equals("SUCCESS")) {
                            BaseApplication.savestatus(storesBean.getData().getStatus());
                            Intent intent = new Intent(activity, SubmissionActivity.class);
                            intent.putExtra("merchantName", storesBean.getData().getMerchantName());
                            intent.putExtra("createTime", storesBean.getData().getCreateTime());
                            startActivity(intent);
                        } else {
                            ToastUitl.show(storesBean.getMessage(), 0);
                        }
                    }
                }, body
        );
    }
}
