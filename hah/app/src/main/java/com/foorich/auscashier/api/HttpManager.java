package com.foorich.auscashier.api;

import android.text.TextUtils;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 8:48
 * desc   :
 * version: 1.0
 */
public class HttpManager {

    public int currentRetryCount=0;
    public int maxConnectCount=2;
    public int waitRetryTime=0;


    public static HttpManager httpManager = new HttpManager();


    private HttpManager() {

    }

    public static HttpManager getHttpManager() {
        return httpManager;
    }

    /**
     * get方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void getMethod(String url, Observer<String> observer) {
        //获取被观察者
        Observable<String> observable = RetrofitUtil.getInstance().get2(Api.class).getMethod(url);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * Post方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     * @param map
     */
    public void postMethod(String url, Observer<String> observer, Map map) {

        Observable<String> observable = RetrofitUtil.getInstance().get(Api.class).postMethod(url, map);

        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).

                observeOn(AndroidSchedulers.mainThread()).

                subscribe(observer);

    }

    public void postMethod(String url, Observer<String> observer, RequestBody body) {

        Observable<String> observable = RetrofitUtil.getInstance().get(Api.class).postMethod(url, body);

        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }


    public void postMethodasset(String url, Observer<String> observer, RequestBody body) {

        Observable<String> observable = RetrofitUtil.getInstance().get(Api.class).postMethod(url, body);

        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);

    }


    public void postMethod(String url, Observer<String> observer, Map map, String[] carjb, String[] card, String[] c, String[] d, String[] e, String[] f, String[] g, String[] h, String[] i, String[] j) {

        Observable<String> observable = RetrofitUtil.getInstance().get(Api.class).postMethod(url, map, carjb, card, c, d, e, f, g, h, i, j);

        //获取被观察者

        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * Post方式请求 指纹登录
     * 封装时，传递observer
     *
     * @param baseUrl
     * @param url
     * @param observer
     */
    public void postTochMethod(String baseUrl, String url, Observer<String> observer, boolean isTouch, String userId) {

        //获取被观察者
        Observable<String> observable = RetrofitUtil.getInstance().get(Api.class).postTouchMethod(url, isTouch, userId);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * 上传单张图片
     */
    public void upLoadIcon(String url, String path,  String picname , String merchantCode, Observer<ResponseBody> observer) {
        File file = new File(path.toString());
        RequestBody requestbody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody Code = RequestBody.create(MediaType.parse("text/plain"), merchantCode);
        MultipartBody.Part body = MultipartBody.Part.createFormData(picname, file.getName(), requestbody);
        Observable<ResponseBody> responseBodyObservable;
        responseBodyObservable = RetrofitUtil.getInstance().get(Api.class).upLoadImg(url, body, Code);
        responseBodyObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    /**
     * 上传多张图片以及文件
     */

    public void upLoadImgs(String url, String[] file, Observer<ResponseBody> observer) {
        RequestBody assboy = RequestBody.create(MediaType.parse("text/plain"), "MyApplication.getToken()");
        RequestBody userboy = RequestBody.create(MediaType.parse("text/plain"), "MyApplication.getUserId()");
        RequestBody dvice_id = RequestBody.create(MediaType.parse("text/plain"), "MyApplication.getAndroidId()");
        RequestBody os = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "MyApplication.getUpCarId()");
        MultipartBody.Part pic1body = null;
        if (!TextUtils.isEmpty(file[0])) {
            RequestBody requestbody = RequestBody.create(MediaType.parse("image/jpg"), new File(file[0]));
            pic1body = MultipartBody.Part.createFormData("pic1", file[0], requestbody);
        }
        MultipartBody.Part pic2body = null;
        if (!TextUtils.isEmpty(file[1])) {
            RequestBody requestbody2 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[1]));
            pic2body = MultipartBody.Part.createFormData("pic2", file[1], requestbody2);
        }
        MultipartBody.Part pic3body = null;
        if (!TextUtils.isEmpty(file[2])) {
            RequestBody requestbody3 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[2]));
            pic3body = MultipartBody.Part.createFormData("pic3", file[2], requestbody3);
        }
        MultipartBody.Part pic4body = null;
        if (!TextUtils.isEmpty(file[3])) {
            RequestBody requestbody4 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[3]));
            pic4body = MultipartBody.Part.createFormData("pic4", file[3], requestbody4);
        }
        MultipartBody.Part pic5body = null;
        if (!TextUtils.isEmpty(file[4])) {
            RequestBody requestbody5 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[4]));
            pic5body = MultipartBody.Part.createFormData("pic5", file[4], requestbody5);
        }
        MultipartBody.Part pic6body = null;
        if (!TextUtils.isEmpty(file[5])) {
            RequestBody requestbody6 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[5]));
            pic6body = MultipartBody.Part.createFormData("pic6", file[5], requestbody6);
        }
        MultipartBody.Part pic7body = null;
        if (!TextUtils.isEmpty(file[6])) {
            RequestBody requestbody7 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[6]));
            pic7body = MultipartBody.Part.createFormData("pic7", file[6], requestbody7);
        }

        MultipartBody.Part pic8body = null;
        if (!TextUtils.isEmpty(file[7])) {
            RequestBody requestbody8 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[7]));
            pic8body = MultipartBody.Part.createFormData("pic8", file[7], requestbody8);
        }
        MultipartBody.Part pic9body = null;
        if (!TextUtils.isEmpty(file[8])) {
            RequestBody requestbody9 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[8]));
            pic9body = MultipartBody.Part.createFormData("pic9", file[8], requestbody9);
        }


        MultipartBody.Part pic10body = null;
        if (!TextUtils.isEmpty(file[9])) {
            RequestBody requestbody8 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[9]));
            pic10body = MultipartBody.Part.createFormData("pic10", file[9], requestbody8);
        }
        MultipartBody.Part pic11body = null;
        if (!TextUtils.isEmpty(file[10])) {
            RequestBody requestbody9 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[10]));
            pic11body = MultipartBody.Part.createFormData("pic11", file[10], requestbody9);
        }
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().get(Api.class).upLoadsImgs(url, assboy, userboy, dvice_id, os, id, pic1body, pic2body, pic3body, pic4body, pic5body, pic6body, pic7body, pic8body, pic9body, pic10body, pic11body);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
