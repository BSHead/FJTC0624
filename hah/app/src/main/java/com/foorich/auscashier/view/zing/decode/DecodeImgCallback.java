package com.foorich.auscashier.view.zing.decode;

import com.google.zxing.Result;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-21 17:29
 * desc   : 解析图片的回调
 * version: 1.0
 */
public interface DecodeImgCallback {
    public void onImageDecodeSuccess(Result result);

    public void onImageDecodeFailed();

}
