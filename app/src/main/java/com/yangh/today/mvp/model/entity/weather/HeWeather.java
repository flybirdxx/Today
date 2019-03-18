package com.yangh.today.mvp.model.entity.weather;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangH on 2019/3/17.
 */
public class HeWeather implements Serializable {

    /**
     * aqi : {"city":{"aqi":"73","qlty":"良","pm25":"51","pm10":"95","no2":"66","so2":"11","co":"1.0","o3":"23"}}
     * basic : {"city":"武汉","cnty":"中国","id":"CN101200101","lat":"30.5843544","lon":"114.29856873","update":{"loc":"2019-03-17 10:55","utc":"2019-03-17 02:55"}}
     * daily_forecast : [{"astro":{"mr":"14:17","ms":"03:33","sr":"06:29","ss":"18:32"},"cond":{"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"},"date":"2019-03-17","hum":"76","pcpn":"1.0","pop":"55","pres":"1015","tmp":{"max":"17","min":"9"},"uv":"0","vis":"25","wind":{"deg":"151","dir":"东南风","sc":"1-2","spd":"8"}},{"astro":{"mr":"15:26","ms":"04:26","sr":"06:28","ss":"18:33"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2019-03-18","hum":"72","pcpn":"0.0","pop":"2","pres":"1010","tmp":{"max":"21","min":"9"},"uv":"6","vis":"25","wind":{"deg":"242","dir":"西南风","sc":"1-2","spd":"9"}},{"astro":{"mr":"16:35","ms":"05:16","sr":"06:26","ss":"18:34"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2019-03-19","hum":"80","pcpn":"1.0","pop":"55","pres":"997","tmp":{"max":"22","min":"13"},"uv":"0","vis":"7","wind":{"deg":"145","dir":"东南风","sc":"4-5","spd":"26"}}]
     * now : {"cond":{"code":"104","txt":"阴"},"fl":"17","hum":"49","pcpn":"0.0","pres":"1022","tmp":"17","vis":"16","wind":{"deg":"175","dir":"南风","sc":"1","spd":"2"}}
     * status : ok
     * suggestion : {"air":{"brf":"较差","txt":"气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。"},"comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"较易发","txt":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"},"sport":{"brf":"较适宜","txt":"阴天，较适宜进行各种户内外运动。"},"trav":{"brf":"适宜","txt":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
     */

    private HeAqi aqi;
    private HeBasic basic;
    private HeNow now;
    private String status;
    private HeSuggesstion suggestion;
    private List<HeDailyForecast> daily_forecast;





}
