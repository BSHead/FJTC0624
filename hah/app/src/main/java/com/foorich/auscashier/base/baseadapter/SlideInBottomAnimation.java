package com.foorich.auscashier.base.baseadapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:59
 * desc   :
 * version: 1.0
 */
public class SlideInBottomAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }

}
