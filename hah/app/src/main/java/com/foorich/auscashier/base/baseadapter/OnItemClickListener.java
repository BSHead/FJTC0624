package com.foorich.auscashier.base.baseadapter;

import android.view.View;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-16 10:55
 * desc   :
 * version: 1.0
 */
public abstract class OnItemClickListener extends SimpleClickListener {

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SimpleOnItemClick(adapter, view, position);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public abstract void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position);
}
