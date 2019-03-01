package com.yangh.today.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yangh.today.mvp.contract.DetailContract;
import com.yangh.today.mvp.model.DetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangH on 2019/3/1.
 */
@Module
public class DetailModule {

    private DetailContract.View view;

    public DetailModule(DetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DetailContract.View provideDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DetailContract.Model provideDetailModel(DetailModel model) {
        return model;
    }
}
