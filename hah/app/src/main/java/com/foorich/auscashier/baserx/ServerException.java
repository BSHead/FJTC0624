package com.foorich.auscashier.baserx;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 11:05
 * desc   : 服务器请求异常
 * version: 1.0
 */
public class ServerException extends Exception {

    public ServerException(String msg) {
        super(msg);
    }
}
