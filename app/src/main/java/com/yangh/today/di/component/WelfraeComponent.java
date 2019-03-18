package com.yangh.today.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.yangh.today.di.module.WelfareModule;
import com.yangh.today.mvp.ui.fragment.walfare.WelfareFragment;

import dagger.Component;

/**
 * Created by yangH on 2019/3/3.
 */
@ActivityScope
@Component(modules = WelfareModule.class, dependencies = AppComponent.class)
public interface WelfraeComponent {
    void inject(WelfareFragment fragment);
}
