package com.yangh.today.mvp.model.service;

import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.model.entity.weather.HeWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.yangh.today.mvp.model.APi.GANK_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * Created by yangH on 2019/2/27.
 */
public interface GankService {
    //    http://gank.io/api/data/Android/10/1
    @Headers({DOMAIN_NAME_HEADER + GANK_DOMAIN_NAME})
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> getGank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") String page);

    //    http://gank.io/api/random/data/Android/20
    @Headers({DOMAIN_NAME_HEADER+GANK_DOMAIN_NAME})
    @GET("api/data/福利/{pageSize}/{page}")
    Observable<GankEntity> getGirl(@Path("pageSize") int pageSize, @Path("page") String page);


}
