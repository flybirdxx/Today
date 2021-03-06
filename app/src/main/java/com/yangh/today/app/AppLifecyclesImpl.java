package com.yangh.today.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yangh.today.BuildConfig;

import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import timber.log.Timber;

import static com.yangh.today.mvp.model.APi.APP_GANK_DOMAIN;
import static com.yangh.today.mvp.model.APi.APP_HE_NOMAIN;
import static com.yangh.today.mvp.model.APi.GANK_DOMAIN_NAME;
import static com.yangh.today.mvp.model.APi.WEATHER_DOMAIN_NAME;

/**
 * Created by yangH on 2019/2/27.
 */
public class AppLifecyclesImpl implements AppLifecycles {

    public static Application application;
    private static Handler handler;

    public static Application getInstance() {
        return application;
    }

    public static Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }


    @Override
    public void attachBaseContext(@NonNull Context base) {
        //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        Utils.init(application);
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        RetrofitUrlManager.getInstance().putDomain(GANK_DOMAIN_NAME, APP_GANK_DOMAIN);
        RetrofitUrlManager.getInstance().putDomain(WEATHER_DOMAIN_NAME, APP_HE_NOMAIN);
        if (BuildConfig.LOG_DEBUG) {

            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
            //timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            //            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展

            //            Logger.addLogAdapter(new AndroidLogAdapter());
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    //                    Logger.log(priority, tag, message, t);
                    LogUtils.log(priority, tag, message, t);
                }
            });
            this.application = application;
            ButterKnife.setDebug(true);


            ARouter.init(application);
            QMUISwipeBackActivityManager.init(application);

            //LeakCanary 内存泄露检查
            //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
            //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
            ArmsUtils.obtainAppComponentFromContext(application).extras().put(IntelligentCache.KEY_KEEP +
                    RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) :
                    RefWatcher.DISABLED);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
