package com.foorich.auscashier.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-20 14:05
 * desc   :
 * version: 1.0
 */
public class LinkStringByGet {

    //拼接data里面数据
    public static String createLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
//            value = URLEncoder.encode(value, "UTF-8");
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
