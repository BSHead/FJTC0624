package com.foorich.auscashier.view.state;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-4 14:30
 * desc   :
 * version: 1.0
 */
public class StatusView extends View {


    private int mBarSize;

    public StatusView(Context context) {
        this(context, null, 0);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        mBarSize = resources.getDimensionPixelSize(resourceId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mBarSize);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    /**
     * Get status bar height.
     */
    public int getBarSize() {
        return mBarSize;
    }

}
