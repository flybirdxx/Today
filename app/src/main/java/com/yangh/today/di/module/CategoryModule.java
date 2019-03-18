package com.yangh.today.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.mvp.adapter.CategoryAdapter;
import com.yangh.today.mvp.adapter.MyAdapter;
import com.yangh.today.mvp.contract.CategoryContract;
import com.yangh.today.mvp.model.CategoryModel;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangH on 2019/2/27.
 */
@Module
public class CategoryModule {

    private CategoryContract.View view;

    public CategoryModule(CategoryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CategoryContract.View provideCategoryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CategoryContract.Model provideCategoryModel(CategoryModel model) {
        return model;
    }

    //    @ActivityScope
    //    @Provides
    //    RecyclerView.LayoutManager provideLayoutManager() {
    //        return new LinearLayoutManager(view.getActivity());
    //    }

    @ActivityScope
    @Provides
    RxPermissions provideRxpermissions() {
        return new RxPermissions(view.getActivity());
    }

    @ActivityScope
    @Provides
    List<GankEntity.ResultsBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    DefaultAdapter<GankEntity.ResultsBean> provideGankAdapter(CategoryContract.View view, List<GankEntity.ResultsBean> list) {
        return new MyAdapter(list);
    }

}
