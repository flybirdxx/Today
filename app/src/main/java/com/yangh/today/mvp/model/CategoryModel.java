package com.yangh.today.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yangh.today.mvp.contract.CategoryContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.model.service.GankService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/2/27.
 */
@ActivityScope
public class CategoryModel extends BaseModel implements CategoryContract.Model {

    private final Gson gson;
    private final Application application;

    public static final int PAGESIZE = 10;

    @Inject
    public CategoryModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.gson = gson;
        this.application = application;
    }

    @Override
    public Observable<GankEntity> getGank(String type, String page) {
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getGank(type, PAGESIZE, page);
    }

}
