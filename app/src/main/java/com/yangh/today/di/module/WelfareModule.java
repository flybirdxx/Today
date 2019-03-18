package com.yangh.today.di.module;

import android.support.annotation.PluralsRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.mvp.adapter.MZAdapter;
import com.yangh.today.mvp.contract.WelfareContract;
import com.yangh.today.mvp.model.WelfareModel;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangH on 2019/3/3.
 */
@Module
public class WelfareModule {

    private WelfareContract.View view;

    public WelfareModule(WelfareContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WelfareContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GridLayoutManager provideGridLayoutManager() {
        return new GridLayoutManager(view.getActivity(),2);
    }

    @ActivityScope
    @Provides
    WelfareContract.Model provideModel(WelfareModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    GyroscopeObserver provideObserver(){
        return new GyroscopeObserver();
    }

    @ActivityScope
    @Provides
    List<GankEntity.ResultsBean> provideListData() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RxPermissions providePermission() {
        return new RxPermissions(view.getActivity());
    }

//    @ActivityScope
//    @Provides
//    RecyclerView.Adapter provideAdapter(List<GankEntity.ResultsBean> list) {
//        return new MZAdapter(list);
//    }
}
