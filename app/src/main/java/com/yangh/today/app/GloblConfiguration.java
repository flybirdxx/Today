package com.yangh.today.app;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.BaseUrl;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;
import com.jess.arms.http.log.FormatPrinter;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.RefWatcher;
import com.yangh.today.BuildConfig;
import com.yangh.today.mvp.model.APi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by yangH on 2019/2/25.
 */
public class GloblConfiguration implements ConfigModule {
    public static String sDomain = APi.APP_DOMAIN;
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) {
            //relesse时，让框架不要再打印Http请求和响应信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }
        builder.baseurl(APi.APP_DOMAIN)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                //想支持多 BaseUrl, 以及运行时动态切换任意一个 BaseUrl, 请使用 https://github.com/JessYanCoding/RetrofitUrlManager
                //如果 BaseUrl 在 App 启动时不能确定, 需要请求服务器接口动态获取, 请使用以下代码
                //以下方式是 Arms 框架自带的切换 BaseUrl 的方式, 在整个 App 生命周期内只能切换一次, 若需要无限次的切换 BaseUrl, 以及各种复杂的应用场景还是需要使用 RetrofitUrlManager 框架
                //以下代码只是配置, 还要使用 Okhttp (AppComponent中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
                //切记整个过程必须在第一次调用 Retrofit 接口之前完成, 如果已经调用过 Retrofit 接口, 此种方式将不能切换 BaseUrl
                .baseurl(new BaseUrl() {
                    @Override
                    public HttpUrl url() {
                        return HttpUrl.parse(sDomain);
                    }
                })

                //可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性
                //                .cacheFactory(new Cache.Factory() {
                //                    @NonNull
                //                    @Override
                //                    public Cache build(CacheType type) {
                //                        switch (type.getCacheTypeId()){
                //                            case CacheType.EXTRAS_TYPE_ID:
                //                                return new IntelligentCache(500);
                //                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
                //                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
                //                            default:
                //                                return new LruCache(200);
                //                        }
                //                    }
                //                })
                .formatPrinter(new FormatPrinter() {
                    @Override
                    public void printJsonRequest(Request request, String bodyString) {
                        Timber.i("printJsonRequest:" + bodyString);
                    }

                    @Override
                    public void printFileRequest(Request request) {
                        Timber.i("printFileRequest:" + request.url().toString());
                    }

                    @Override
                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code, String headers, MediaType contentType, String bodyString, List<String> segments, String message, String responseUrl) {
                        Timber.i("printJsonRequest:" + bodyString);

                    }

                    @Override
                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers, List<String> segments, String message, String responseUrl) {
                        Timber.i("printFileRequest" + responseUrl);
                    }
                })
                // 这里提供一个全局处理 Http 请求和响应结果的处理类,可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                // 用来处理 rxjava 中发生的所有错误,rxjava 中发生的每个错误都会回调此接口
                // rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {
                    //这里可以自己自定义配置Gson的参数
                    gsonBuilder.serializeNulls() //支持序列化null参数
                            //支持将序列化key为object的map,默认只能序列化key为string的map
                            .enableComplexMapKeySerialization();
                })
                .retrofitConfiguration((context1, retrofirBuilder) -> {
                    //这里可以自己自定义配置Retrofit的参数, 甚至您可以替换框架配置好的
                    // OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)

                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {
                    //                    okhttpBuilder.sslSocketFactory(); //支持 Https,详情请百度
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听
                    ProgressManager.getInstance().with(okhttpBuilder);
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl.
                    RetrofitUrlManager.getInstance().with(okhttpBuilder);
                })
                .rxCacheConfiguration((context1, cacheBuilder) -> {
                    cacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    //想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 fastjson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
                    return null;
                });


    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向 Activity 的生命周期中注入一些自定义逻辑
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
            }

            @Override
            public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                ((RefWatcher) ArmsUtils
                        .obtainAppComponentFromContext(f.getActivity())
                        .extras()
                        .get(IntelligentCache.KEY_KEEP + RefWatcher.class.getName()))
                        .watch(f);
            }
        });
    }


}
