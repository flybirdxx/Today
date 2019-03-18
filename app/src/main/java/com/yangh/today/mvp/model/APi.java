package com.yangh.today.mvp.model;

/**
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 *
 * @author yangH
 * @date 2019/2/27
 */
public interface APi {
    String APP_GANK_DOMAIN = "http://gank.io/";
    String APP_HE_NOMAIN = "https://free-api.heweather.net/";
    String APP_DOMAIN = "https://api.github.com";

    String RequestSuccess = "0";
    String GANK_DOMAIN_NAME = "gank";
    String WEATHER_DOMAIN_NAME = "heweather";


}
