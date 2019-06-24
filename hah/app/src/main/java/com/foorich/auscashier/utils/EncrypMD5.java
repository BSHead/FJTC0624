package com.foorich.auscashier.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-13 9:59
 * desc   : MD5加密
 * version: 1.0
 */
public class EncrypMD5 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "加密MD5";
        EncrypMD5 md5 = new EncrypMD5();
        byte[] resultBytes = md5.eccrypt(msg);
        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + EncrypMD5.hexString(resultBytes));
    }

    //byte字节转换成16进制的字符串MD5Utils.hexString
    public static String hexString(byte[] bytes) {
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }
}
