package com.foorich.auscashier.view.refresh.processor;

import com.foorich.auscashier.view.refresh.library.TwinklingRefreshLayout;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-11 13:43
 * desc   :
 * version: 1.0
 */
public abstract class Decorator implements IDecorator {

    protected IDecorator decorator;
    protected TwinklingRefreshLayout.CoContext cp;

    public Decorator(TwinklingRefreshLayout.CoContext processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }

}
