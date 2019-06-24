package com.foorich.auscashier.view.state;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-4 14:28
 * desc   :
 * version: 1.0
 */
public interface Bar {

    Bar statusBarDarkFont();

    Bar statusBarLightFont();

    Bar statusBarBackground(int statusBarColor);

    Bar statusBarBackground(Drawable drawable);

    Bar statusBarBackgroundAlpha(int alpha);

    Bar navigationBarBackground(int navigationBarColor);

    Bar navigationBarBackground(Drawable drawable);

    Bar navigationBarBackgroundAlpha(int alpha);

    Bar invasionStatusBar();

    Bar invasionNavigationBar();

    /**
     * @deprecated use {@link #fitsStatusBarView(int)} instead.
     */
    @Deprecated
    Bar fitsSystemWindowView(int viewId);

    /**
     * @deprecated use {@link #fitsStatusBarView(View)} instead.
     */
    @Deprecated
    Bar fitsSystemWindowView(View view);

    Bar fitsStatusBarView(int viewId);

    Bar fitsStatusBarView(View view);

    Bar fitsNavigationBarView(int viewId);

    Bar fitsNavigationBarView(View view);
}