package com.yangh.today.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yangh.today.mvp.contract.HeWeatherContract;
import com.yangh.today.mvp.model.HeWeatherModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/17/2019 10:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HeWeatherModule {

    @Binds
    abstract HeWeatherContract.Model bindHeWeatherModel(HeWeatherModel model);
}