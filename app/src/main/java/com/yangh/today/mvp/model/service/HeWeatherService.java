package com.yangh.today.mvp.model.service;

import com.yangh.today.mvp.model.entity.weather.HeWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.yangh.today.mvp.model.APi.WEATHER_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * Created by yangH on 2019/3/17.
 */
public interface HeWeatherService {
    @Headers({DOMAIN_NAME_HEADER + WEATHER_DOMAIN_NAME})
    @GET("s6/weather")
    Observable<HeWeather> getWeather(@Query("city") String city, @Query("key") String key);
}
