package com.riding.hourseriding.model;

/**
 * Created by Raghvendra Sahu on 09-May-20.
 */
public class DrawerItem {
    String title;
    int imgResID;

    public DrawerItem(String title, int image){
        this.title=title;
        imgResID =image;
    }

    public String getTitle() {
        return title;
    }

    public int getImgResID() {
        return imgResID;
    }

}
