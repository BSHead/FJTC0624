package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.AppManager;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.view.state.Sofia;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-18 9:53
 * desc   : 加载webview
 * version: 1.0
 */

public class WebActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar_left)
    ImageView mToolbarLeft;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    String title;
    String url;
    String type;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarLeft.setVisibility(View.VISIBLE);
        Intent intent;
        intent = this.getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");

        type = intent.getStringExtra("type");//判断消息列表使用

        mToolbarTitle.setText(title);
        webView.loadUrl(url);

        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    public void initView() {
        init();
    }

    /*
     * WebView加载
     */
    private void init() {
        // TODO 自动生成的方法存根
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = webView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
    }


    @OnClick({R.id.toolbar_left})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left://返回

                if(type.equals("系统公告")){
                    // 广播通知
                    Intent in = new Intent();
                    in.setAction("action.refresh");
                    sendBroadcast(in);
                    AppManager.getAppManager().returnToActivity(MessagDetailseActivity.class);
                }else if(type.equals("交易推送")){
                    // 广播通知
                    Intent in = new Intent();
                    in.setAction("action.refresh");
                    sendBroadcast(in);
                    AppManager.getAppManager().returnToActivity(MessagDetailseActivity.class);
                }else{
                    AppManager.getAppManager().finishActivity();
                }
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            // 广播通知
            if(type.equals("系统公告")){
                // 广播通知
                Intent in = new Intent();
                in.setAction("action.refresh");
                sendBroadcast(in);
                AppManager.getAppManager().returnToActivity(MessagDetailseActivity.class);
            }else if(type.equals("交易推送")){
                // 广播通知
                Intent in = new Intent();
                in.setAction("action.refresh");
                sendBroadcast(in);
                AppManager.getAppManager().returnToActivity(MessagDetailseActivity.class);
            }else{
                AppManager.getAppManager().finishActivity();
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
