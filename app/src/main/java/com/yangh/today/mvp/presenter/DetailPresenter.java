package com.yangh.today.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.yangh.today.app.utils.RxUtils;
import com.yangh.today.mvp.contract.DetailContract;
import com.yangh.today.mvp.model.entity.GankEntity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by yangH on 2019/3/1.
 */
@ActivityScope
public class DetailPresenter extends BasePresenter<DetailContract.Model, DetailContract.View> {


    private RxErrorHandler rxErrorHandler;
    private Application mApplication;
    private AppManager appManager;
    private ImageLoader imageLoader;

    @Inject

    public DetailPresenter(DetailContract.Model model, DetailContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.rxErrorHandler = handler;
        this.mApplication = application;
        this.appManager = appManager;
        this.imageLoader = imageLoader;
    }

    public void getGirl() {
        mModel.getGirl()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.applySchdulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GankEntity>(rxErrorHandler) {
                    @Override
                    public void onNext(GankEntity gankEntity) {
                        mRootView.setData(gankEntity.results.get(0).getUrl());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rxErrorHandler = null;
        this.appManager = null;
        this.imageLoader = null;
        this.appManager = null;
    }
}
