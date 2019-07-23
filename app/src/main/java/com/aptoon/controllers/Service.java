package com.aptoon.controllers;



import com.aptoon.entity.HomeModel;
import com.aptoon.entity.Login_Data;
import com.aptoon.entity.MyVideoListModel;
import com.aptoon.entity.Search_Data;
import com.aptoon.entity.Soon;
import com.aptoon.entity.Update_profile;
import com.aptoon.entity.User_Signup;
import com.aptoon.entity.VideoDetailsModel;
import com.aptoon.entity.VideoLikeModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface Service {

    @FormUrlEncoded
    @POST("/ap-toon/public/api/login")
    Call<Login_Data> Login_Data(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/register")
    Call<User_Signup> User_Signup(@FieldMap Map<String, String> params);

    @GET("/ap-toon/public/api/soon")
    Call<Soon> getSoon();

    @Multipart
    @POST("/ap-toon/public/api/update-profile")
    //Call<Update_profile> Update_profile(@Part List<MultipartBody.Part >list);
    Call <Update_profile> Update_profile (@Part MultipartBody.Part user_id,
                                          @Part MultipartBody.Part name,
                                          @Part MultipartBody.Part email,
                                          @Part MultipartBody.Part oldpicture,
                                          @Part MultipartBody.Part profile_pic);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/search")
    Call<Search_Data> Search_Data(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/home-page")
    Call<HomeModel> GetAllHomeData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/video-details")
    Call<VideoDetailsModel> Video_details(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/like-unlike")
    Call<VideoLikeModel> VideoLike(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ap-toon/public/api/add-to-list")
    Call<VideoLikeModel> VideoAddtoList(@FieldMap Map<String, String> params);

    @GET("/ap-toon/public/api/my-play-list/{video_id}")
    Call<MyVideoListModel> VideoPlaylist(@Path("video_id") String id);
}

