package com.foorich.auscashier.bean;

import android.graphics.drawable.Drawable;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-4-4 15:45
 * desc   :
 * version: 1.0
 */
public class Item {
    private int id;
    private String title;
    private Drawable icon;

    public Item() {
    }

    public Item(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
