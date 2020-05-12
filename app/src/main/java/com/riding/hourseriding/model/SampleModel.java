package com.riding.hourseriding.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Raghvendra Sahu on 08-Apr-20.
 */
public class SampleModel {
    String news_title;
    String price;
    int image;

    public SampleModel(String product_name, String date, int image) {
        this.news_title = product_name;
        this.price = date;
        this.image = image;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @BindingAdapter("postImage")
    public static void loadImage(ImageView view, int imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                // .placeholder()
                .apply(new RequestOptions())
                .into(view);
    }

}
