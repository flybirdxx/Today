package com.yangh.today.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yangh.today.di.module.HeWeatherModule;
import com.yangh.today.mvp.contract.HeWeatherContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yangh.today.mvp.ui.activity.HeWeatherActivity;
import com.yangh.today.mvp.ui.fragment.HeWeatherFragment;


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
@ActivityScope
@Component(modules = HeWeatherModule.class, dependencies = AppComponent.class)
public interface HeWeatherComponent {
    void inject(HeWeatherActivity activity);

    void inject(HeWeatherFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HeWeatherComponent.Builder view(HeWeatherContract.View view);

        HeWeatherComponent.Builder appComponent(AppComponent appComponent);

        HeWeatherComponent build();
    }
}