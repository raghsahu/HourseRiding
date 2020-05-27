package com.riding.hourseriding.api_call;

import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Raghvendra Sahu on 20-Apr-20.
 */
public interface Api_Call {

    @GET("wp-json/wp/v2/posts?per_page=5")
    Observable<List<NewsPostModel>> GetTodayNews();

    @GET("wp-json/wp/v2/posts?per_page=5")
    Observable<List<NewsPostModel>> GetLatestPostNews(
            @Query("page") String page,
            @Query("categories") String categ);


//    @FormUrlEncoded
//    @POST("index.php?route=restapi/customer/edit_account")
//    Observable<SimpleResultModel> UpdateUser(
//            @Field("firstname") String first_name,
//            @Field("lastname") String lastname,
//            @Field("telephone") String mobile,
//            @Field("email") String email,
//            @Field("customer_id") String customer_id);
//
//    @GET("index.php?route=restapi/information")
//    Observable<Company_infoModel> InfoCompany();


}
