package com.yangh.today.mvp.model.service;

import com.yangh.today.mvp.model.entity.GankEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yangH on 2019/2/27.
 */
public interface CommonService {
    //    http://gank.io/api/data/Android/10/1
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> getGank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") String page);

    //    http://gank.io/api/random/data/Android/20
    @GET("api/random/data/福利/1")
    Observable<GankEntity> getGirl();
}
