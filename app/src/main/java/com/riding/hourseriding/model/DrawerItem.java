package com.riding.hourseriding.model;

/**
 * Created by Raghvendra Sahu on 09-May-20.
 */
public class DrawerItem {
    String title;
    String NewsId;
    int imgResID;

    public DrawerItem(String title, int image,String newsId){
        this.title=title;
        imgResID =image;
        NewsId =newsId;
    }

    public String getTitle() {
        return title;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsId() {
        return NewsId;
    }

    public void setNewsId(String newsId) {
        NewsId = newsId;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
