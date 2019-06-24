package com.foorich.auscashier.db;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.bean.SecondChannelTabBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 11:25
 * desc   :
 * version: 1.0
 */
public class SecondChannelManager {
    public static List<SecondChannelTabBean> loadTab(){
        List<String> name = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.second_fragment_name));
        List<String> id = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.second_fragment_id));
        ArrayList<SecondChannelTabBean> list = new ArrayList<>();
        for(int i=0;i<name.size();i++){
            SecondChannelTabBean secondChannelTabBean = new SecondChannelTabBean(name.get(i),id.get(i));
            list.add(secondChannelTabBean);
        }
        return list;
    }
}
