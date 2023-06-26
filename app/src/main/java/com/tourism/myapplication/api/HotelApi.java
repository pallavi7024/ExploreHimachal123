package com.tourism.myapplication.api;

import androidx.annotation.Keep;

import com.tourism.myapplication.api.model.GetModel;
import com.tourism.myapplication.api.model.HotelModel;
import com.tourism.myapplication.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Keep
public interface HotelApi {
    String BASE_URL = "https://pallavi7024.github.io/Explore-Himachal-Data/data/";

    @GET("things_to_do/camping/camping.json")
    Call<List<GetModel>> getCampingDataTTD();

    @GET("things_to_do/paragliding/paragliding.json")
    Call<List<GetModel>> getParaglidingDataTTD();

    @GET("things_to_do/trekking/trekking.json")
    Call<List<GetModel>> getTrekkingDataTTD();

    @GET("events/fair_and_festival/fair_and_festival.json")
    Call<List<GetModel>> getFairAndFestivalData();

    @GET("events/holy_places/holy_places.json")
    Call<List<GetModel>> getHolyPlacesData();

    @GET("events/himachal_food/himachal_food.json")
    Call<List<GetModel>> getHimachalFoodData();

    @GET("things_to_do/{type}/{location}/{location}.json")
    Call<List<HotelModel>> getHotelsData(
            @Path("type")
            String type,
            @Path("location")
            String location
    );


    @GET("top_destination/top_destination.json")
    Call<List<GetModel>> getTopDestination();

    @GET("top_destination/{location}/{location}.json")
    Call<List<HotelModel>> getTopDestinationHotels(
            @Path("location")
            String location
    );

    default Call<List<GetModel>> getThingsToDo(Constants.HotelType type) {
        switch (type) {
            case CAMPING:
                return getCampingDataTTD();

            case PARAGLIDING:
                return getParaglidingDataTTD();

            case TREKKING:
                return getTrekkingDataTTD();

            case FAIRS_AND_FESTIVALS:
                return getFairAndFestivalData();

            case HOLY_PLACES:
                return getHolyPlacesData();

            case HIMACHAL_FOOD:
                return getHimachalFoodData();

            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
