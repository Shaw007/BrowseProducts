package com.srmstudios.browseproducts.util;

public class HomeItem {
    private int imageDrawable;
    private String name;

    public HomeItem() {

    }

    public HomeItem(int imageDrawable, String name) {
        this.imageDrawable = imageDrawable;
        this.name = name;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
