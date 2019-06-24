package com.foorich.auscashier.utils;

import java.util.Collection;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 11:22
 * desc   : 集合操作工具类
 * version: 1.0
 */
public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }

}
