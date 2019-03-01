package com.yangh.today.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yangh.today.di.module.DetailModule;
import com.yangh.today.mvp.ui.activity.DetailActivity;

import dagger.Component;

/**
 * Created by yangH on 2019/3/1.
 */
@ActivityScope
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent {
    void inject(DetailActivity activity);
}
