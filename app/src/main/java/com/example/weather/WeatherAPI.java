package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=bc8ab366f012505d580330ab91fd8314&units=metric")
    Call<OpenWeatherMap> getWeatherWithLocation(@Query("lat") double lat, @Query("lon") double lon);

    @GET("weather?appid=bc8ab366f012505d580330ab91fd8314&units=metric")
    Call<OpenWeatherMap> getWeatherWithCityName(@Query("q") String name);


}
