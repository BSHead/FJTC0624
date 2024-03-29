package com.foorich.auscashier.view.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-4 14:28
 * desc   :
 * version: 1.0
 */
public class FitWindowLayout extends ViewGroup {

    public FitWindowLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int contentViewHeight = 0;
        int childCount = getChildCount();
        int menuWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(widthMeasureSpec));
        int menuHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec));
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.measure(menuWidthSpec, menuHeightSpec);
            contentViewHeight += view.getMeasuredHeight();
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), contentViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int allHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int viewHeight = view.getMeasuredHeight();
            view.layout(l, allHeight, r, allHeight + viewHeight);
            allHeight += viewHeight;
        }
    }
}