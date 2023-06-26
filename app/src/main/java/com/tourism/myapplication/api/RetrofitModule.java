package com.tourism.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitModule {

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(HotelApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    public static HotelApi getHotelApi() {
        return retrofit.create(HotelApi.class);
    }
}
