package com.foorich.auscashier.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.foorich.auscashier.base.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 11:41
 * desc   : 公用类
 * version: 1.0
 */
public class CommonUtils {

    /**
     * 自动调起软键盘
     *
     * @param view
     * @return void
     */
    public static void showSoftKeyboard(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().getSystemService(INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }

                       },
                700);
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager)BaseApplication.getContext(). getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     * @return void
     */
    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    //LUHN算法，主要用来计算信用卡等证件号码的合法性。
    public static boolean isBankNumber(String bankNumber) {
        char[] cc = bankNumber.toCharArray();
        int[] n = new int[cc.length + 1];
        int j = 1;
        for (int i = cc.length - 1; i >= 0; i--) {
            n[j++] = cc[i] - '0';
        }
        int even = 0;
        int odd = 0;
        for (int i = 1; i < n.length; i++) {
            if (i % 2 == 0) {
                int temp = n[i] * 2;
                if (temp < 10) {
                    even += temp;
                } else {
                    temp = temp - 9;
                    even += temp;
                }
            } else {
                odd += n[i];
            }
        }

        int total = even + odd;
        if (total % 10 == 0)
            return true;
        return false;

    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 正则表达式匹配判断
     * 校验手机号
     * 1、手机号开头集合
     * 166，
     * 176，177，178
     * 180，181，182，183，184，185，186，187，188，189 
     * 145，147  
     * 130，131，132，133，134，135，136，137，138，139 
     * 150，151，152，153，155，156，157，158，159
     * 198，1992、匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     *
     * @return true if matched, else false
     */

    public static boolean isMobile(String mobile) {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    //拼接验签数据
    public static String CreateLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }



    //将像素转换为px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将px转换为dp
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }




}

