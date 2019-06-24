package com.foorich.auscashier.view.keyboard;

import android.app.Activity;

import com.foorich.auscashier.R;


/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-18 20:38
 * desc   : 自定义键盘调用键盘xml文件
 * version: 1.0
 */
public class KeyboardAdd extends BaseKeyboard{

    public KeyboardAdd(Activity activity) {
        init(activity, R.xml.keyboard_ip_address);
    }

}
