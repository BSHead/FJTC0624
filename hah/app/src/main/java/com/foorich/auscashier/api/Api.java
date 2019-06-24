package com.foorich.auscashier.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

import java.util.Map;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 8:41
 * desc   :
 * version: 1.0
 */
public interface Api {


    //返回值类型是被观察者
    @GET
    Observable<String> getMethod(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<String> postMethod(@Url String url, @FieldMap Map<String, String> map);

    @POST
    Observable<String> postMethod(@Url String url, @Body RequestBody body);

    @POST
    Observable<String> postMethod(@Url String url, @Body RequestBody body, @Query("token") String token);//资产是否能出库查询


    @FormUrlEncoded
    @POST
    Observable<String> postMethod(@Url String url, @FieldMap Map<String, String> map, @Field("carjb[]") String[] carjb, @Field("card[]") String[] card, @Field("carpaifang[]") String[] carpaifang, @Field("recommend[]") String[] recommend,
                                  @Field("create_addr[]") String[] create_addr, @Field("oil_standard[]") String[] oil_standard, @Field("enctype[]") String[] enctype, @Field("carsize[]") String[] carsize, @Field("car_color[]") String[] car_color, @Field("interiorConf[]") String[] interiorConf);

    @FormUrlEncoded
    @POST
    Observable<String> postTouchMethod(@Url String url, @Field("flag") boolean isTouch, @Field("userId") String userId);

    //上传多张图片
    @Multipart
    @POST
    Observable<ResponseBody> upLoadsImgs(@Url String url, @Part("accessToken") RequestBody accessToken, @Part("userId") RequestBody userId, @Part("deviceId") RequestBody deviceId, @Part("os") RequestBody os, @Part("id") RequestBody id, @Part("") MultipartBody.Part photo1, @Part("") MultipartBody.Part photo2, @Part("") MultipartBody.Part photo3, @Part("") MultipartBody.Part photo4, @Part("")  MultipartBody.Part photo5, @Part("")  MultipartBody.Part photo6, @Part("")  MultipartBody.Part photo7, @Part("") MultipartBody.Part photo8, @Part("") MultipartBody.Part photo9, @Part("") MultipartBody.Part photo10, @Part("") MultipartBody.Part photo11);

    // 带参数上传单张图片
    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Url String url, @Part("") MultipartBody.Part photo, @Part("accessToken") RequestBody accessToken, @Part("deviceId") RequestBody deviceId, @Part("os") RequestBody os, @Part("userId") RequestBody userId, @PartMap Map<String, RequestBody> params);

    // 上传单张图片
    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Url String url, @Part("") MultipartBody.Part photo, @Part("accessToken") RequestBody accessToken, @Part("deviceId") RequestBody deviceId, @Part("os") RequestBody os, @Part("userId") RequestBody userId);


    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Url String url, @Part("") MultipartBody.Part photo, @Part("merchantCode") RequestBody merchantCode);

   /* public Retrofit retrofit;
    public ApiService service;

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //增加头部信息
    Interceptor headerInterceptor =new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(build);
        }
    };

    //构造方法私有
    private Api() {
        //处理网络请求的日志拦截输出
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://192.168.5.242:9121")
//                .baseUrl("http://192.168.2.217:9121")
                .build();
        service = retrofit.create(ApiService.class);
    }

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }*/
}
