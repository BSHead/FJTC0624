package com.foorich.auscashier.view.state;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-4 14:30
 * desc   :
 * version: 1.0
 */
public class Sofia {
    private Sofia() {
    }

    public static Bar with(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentLayout = (ViewGroup) window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        if (contentLayout.getChildCount() > 0) {
            View contentView = contentLayout.getChildAt(0);
            if (contentView instanceof Bar) {
                return (Bar) contentView;
            }
        }
        return new HostLayout(activity);
    }

}
