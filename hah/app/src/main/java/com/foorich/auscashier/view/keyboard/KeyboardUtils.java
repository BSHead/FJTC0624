package com.foorich.auscashier.view.keyboard;

import android.view.View;
import android.widget.EditText;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-18 20:35
 * desc   : 自定义键盘工具类
 * version: 1.0
 */
public class KeyboardUtils {

    public static void bindEditTextEvent(final BaseKeyboard customKeyboard, final EditText editText){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    customKeyboard.attachTo(editText);

                }
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customKeyboard.isAttached) customKeyboard.showKeyboard();
                else customKeyboard.attachTo(editText);
            }
        });
    }
}
