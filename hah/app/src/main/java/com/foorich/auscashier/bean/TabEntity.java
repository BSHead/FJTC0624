package com.foorich.auscashier.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-10 15:00
 * desc   :
 * version: 1.0
 */
public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
