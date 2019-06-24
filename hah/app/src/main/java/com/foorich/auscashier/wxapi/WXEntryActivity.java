package com.foorich.auscashier.wxapi;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.umeng.socialize.weixin.view.WXCallbackActivity;


/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 10:07
 * desc   :
 * version: 1.0
 */
public class WXEntryActivity extends WXCallbackActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
//
//    @Override
//    public void onReq(BaseReq req) {
//        super.onReq(req);
//    }
//
//    //微信回调
//    @Override
//    public void onResp(BaseResp resp) {   //分享之后的回调
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK: //正确返回
//                //Toast.makeText(this, "微信分享成功回调了111", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        super.onResp(resp);
//    }
}
