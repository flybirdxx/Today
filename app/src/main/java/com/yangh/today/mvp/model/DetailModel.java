package com.yangh.today.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yangh.today.mvp.contract.DetailContract;

import javax.inject.Inject;

/**
 * Created by yangH on 2019/3/1.
 */
@ActivityScope
public class DetailModel extends BaseModel implements DetailContract.Model {
    @Inject
    public DetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

//    @Override
//    public Observable<GankEntity> getGirl() {
//        return mRepositoryManager.obtainRetrofitService(GankService.class).getGirl();
//    }
}
