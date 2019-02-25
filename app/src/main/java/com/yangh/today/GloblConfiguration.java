package com.yangh.today;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by yangH on 2019/2/25.
 */
public class GloblConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //使用 builder 可以为框架配置一些配置信息
        builder.baseurl("www.baidu.com")
                .gsonConfiguration((context1, gsonBuilder) -> {
                    //这里可以自己自定义配置Gson的参数
                    gsonBuilder.serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {
                    //这里可以自己自定义配置Retrofit的参数,甚至您可以替换系统配置好的okhttp对象
                    //比如使用fastjson替代gson
                    //retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create())
                }).rxCacheConfiguration(new ClientModule.RxCacheConfiguration() {
            @Override
            public RxCache configRxCache(Context context, RxCache.Builder rxCacheBuilder) {
                rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                return null;
            }
        })
                .okhttpConfiguration((context1, okhttpBuilder) -> {
                    //这里可以自己自定义配置Okhttp的参数
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                })
                .globalHttpHandler(new GlobalHttpHandler() {
                    /**
                     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
                     * 重新请求 token, 并重新执行请求
                     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串
                     * @param chain
                     * @param response
                     * @return
                     */
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
                            //                            try {
                            //                                List<User> list = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<List<User>>() {
                            //                                }.getType());
                            //                                User user = list.get(0);
                            //                                Timber.w("Result ------> " + user.getLogin() + "    ||   Avatar_url------> " + user.getAvatarUrl());
                            //                            } catch (Exception e) {
                            //                                e.printStackTrace();
                            //                                return response;
                            //                            }
                        }
                        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
                        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
                        create a new request and modify it accordingly using the new token
                        Request newRequest = chain.request().newBuilder().header("token", newToken)
                                             .build();

                        retry the request

                        response.body().close();
                        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
                        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
                        return response;
                    }

                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
                        return chain.request().newBuilder().header("token", tokenId)
                                              .build(); */
                        return request;
                    }
                })
                .responseErrorListener((context1, t) -> {
                    /* 用来提供处理所有错误的监听, RxJava 订阅时必须传入 ErrorHandleSubscriber 实例, 此监听才生效 */
                    Timber.w("---------" + t.getMessage());
                    ArmsUtils.snackbarText("net error");
                })
                /**
                 * ArmsUtils.obtainAppComponentFromContext(context)
                 * 	.imageLoader()
                 * 	.loadImage(mApplication, ImageConfigImpl
                 *                 .builder()
                 *                 .url(data.getAvatarUrl())
                 *                 .imagerView(mAvater)
                 *                 .build());
                 */
                .imageLoaderStrategy(new GlideImageLoaderStrategy());
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //向 Application的 生命周期中注入一些自定义逻辑
        lifecycles.add(new AppLifecycles() {
            private RefWatcher mRefWatcher;//leakCanary观察器

            @Override
            public void attachBaseContext(@NonNull Context base) {

            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.DEBUG) {
                    //timber 日志打印
                    Timber.plant(new Timber.DebugTree());
                }
                if (BuildConfig.USE_CANARY) {
                    //leakCanary 内存泄漏检测
                    this.mRefWatcher = BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED;
                    //                    LeakCanary.install(application);
                }
            }

            @Override
            public void onTerminate(@NonNull Application application) {
                this.mRefWatcher = null;
            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向 Activity 的生命周期中注入一些自定义逻辑
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //向 Fragment 的生命周期中注入一些自定义逻辑
    }
}
