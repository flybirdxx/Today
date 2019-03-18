package com.yangh.today.mvp.presenter;

import android.app.Application;
import android.view.View;


import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.yangh.today.app.utils.IntentUtils;
import com.yangh.today.app.utils.RxUtils;
import com.yangh.today.mvp.contract.WelfareContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.ui.activity.PicBrowserActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * Created by yangH on 2019/3/3.
 */
@ActivityScope
public class WelfarePresenter extends BasePresenter<WelfareContract.Model, WelfareContract.View> {

    private Application application;

    @Inject
    RxErrorHandler rxErrorHandler;
    @Inject
    List<GankEntity.ResultsBean> data;
    private boolean isFirst = true;
    private int lastUserId = 1;
    private ArrayList<String> imgList = new ArrayList<>();

    @Inject
    public WelfarePresenter(WelfareContract.Model model, WelfareContract.View rootView, Application application) {
        super(model, rootView);
        this.application = application;
    }

    public void requestGirl(boolean pullToRefresh) {
        if (pullToRefresh && isFirst) {
            lastUserId = 1;
            isFirst = false;
        } else {
            lastUserId++;
        }

        mModel.getGirl(10, String.valueOf(lastUserId))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh) {
                        mRootView.showLoading();
                    } else {
                        mRootView.startLoadMore();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh) {
                        mRootView.hideLoading();
                    } else {
                        mRootView.hideLoadMore();
                    }
                })
                .compose(RxUtils.applySchdulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GankEntity>(rxErrorHandler) {
                    @Override
                    public void onNext(GankEntity gankEntity) {
                        if (pullToRefresh) {
                            mRootView.setNewData(gankEntity.results);
                        } else {
                            mRootView.addNewData(gankEntity.results);
                        }
                    }
                });
    }

    public void showImage(View view, int pos) {
        imgList.clear();
        for (int i = 0; i < data.size(); i++) {
            imgList.add(data.get(i).getUrl());
        }


        IntentUtils.start2ShowImg(application,imgList,pos,view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        application = null;
    }
}

