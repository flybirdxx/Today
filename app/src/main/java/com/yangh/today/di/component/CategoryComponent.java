package com.yangh.today.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yangh.today.di.module.CategoryModule;
import com.yangh.today.mvp.ui.fragment.category.CategoryFragment;

import dagger.Component;

/**
 * Created by yangH on 2019/2/27.
 */
@ActivityScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment fragment);
}
