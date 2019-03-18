package com.yangh.today.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yangh.today.mvp.contract.WelfareContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.model.service.GankService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/3/3.
 */
@ActivityScope
public class WelfareModel extends BaseModel implements WelfareContract.Model {

    public static final int PAGESIZE = 10;

    @Inject
    public WelfareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GankEntity> getGirl(int pageSize,String page) {
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getGirl(pageSize, page);
    }


}
