package com.foorich.auscashier.view.zing.android;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-21 17:38
 * desc   : 在相机会手电筒可能被占用的情况下退出
 * version: 1.0
 */
public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        run();
    }

    private void run() {
        activityToFinish.finish();
    }


}
