package com.foorich.auscashier.api;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 8:49
 * desc   :
 * version: 1.0
 */
public class RetrofitUtil {

    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static RetrofitUtil instance;
    private static Retrofit mRetrofit;
    //解决优化查询超时问题 默认10s
    private final static Object mRetrofitLock = new Object();


    private static Retrofit getRetrofit() {

        if (sRetrofit == null) {
            synchronized (mRetrofitLock) {

                if (sRetrofit == null) {

                    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }};
                    try {
                        // Install the all-trusting trust manager
                        final SSLContext sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        clientBuilder.addInterceptor(httpLoggingInterceptor);

                        sOkHttpClient = clientBuilder
                                .sslSocketFactory(sslContext.getSocketFactory())
                                .hostnameVerifier(new HostnameVerifier() {
                                    @Override
                                    public boolean verify(String hostname, SSLSession session) {
                                        return true;
                                    }
                                }).connectTimeout(15, TimeUnit.SECONDS).build();
                        sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                                .baseUrl(ApiService.ServiceAddress)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return sRetrofit;
    }

    private static Retrofit getRetrofit2() {

        if (mRetrofit == null) {
            synchronized (mRetrofitLock) {

                if (mRetrofit == null) {
                    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }};
                    try {
                        // Install the all-trusting trust manager
                        final SSLContext sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        clientBuilder.addInterceptor(httpLoggingInterceptor);
                        sOkHttpClient = clientBuilder.
                                sslSocketFactory(sslContext.getSocketFactory())
                                .hostnameVerifier(new HostnameVerifier() {
                                    @Override
                                    public boolean verify(String hostname, SSLSession session) {
                                        return true;
                                    }
                                }).build();
                        mRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                                .baseUrl(ApiService.ServiceAddress)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();
                    } catch (Exception e) {

                    }
                }
            }
        }
        return mRetrofit;
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }

    public <T> T get(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    public <T> T get2(Class<T> tClass) {
        return getRetrofit2().create(tClass);
    }


}
