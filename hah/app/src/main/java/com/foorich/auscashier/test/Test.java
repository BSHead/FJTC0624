package com.foorich.auscashier.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-12 16:25
 * desc   : 测试
 * version: 1.0
 */
public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        JSONObject json = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("loginName", "18201097621");
                jsonObject.put("osType", "1");
                jsonObject.put("osType", "123456");
                jsonArray.put(jsonObject);

            json.put("appVersion", 1.0);
            json.put("data", jsonArray);
            json.put("language", "zh");
            json.put("sign", "187852ca054d36fcbe29f29b791bc838");
            json.put("tenant", "platform");
            json.put("terminal", "Android");
            json.put("token", "7620190410174856");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());



        System.out.println("密文是：" + body);
    }
}
