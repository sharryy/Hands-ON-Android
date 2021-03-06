package com.anonymous.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Posts>> getPosts(@Query("userId") int userId);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

}
