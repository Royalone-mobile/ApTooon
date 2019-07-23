package com.aptoon.controllers;





import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apis {
    //public static String BASE_URL = "http://45.9.236.187/";
    public static String BASE_URL ="http://orioninfosolutions.org";
    //public static String Photo_Base_URL="http://45.9.236.187/users/";
    public static String Photo_Base_URL="http://orioninfosolutions.org/ap-toon/public/users/";

    public static Service getAPIService() {
        /*------------Use For Retrofit----------------------*/


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        client.readTimeoutMillis();
        // Change base URL to your upload server URL.
        Service uploadService = new Retrofit.Builder()
                .baseUrl(Apis.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
        return uploadService;
    }
}
