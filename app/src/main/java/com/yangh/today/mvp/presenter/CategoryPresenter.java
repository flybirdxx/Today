package com.yangh.today.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.yangh.today.app.utils.RxUtils;
import com.yangh.today.mvp.contract.CategoryContract;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by yangH on 2019/2/28.
 */
@ActivityScope
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View> {
    @Inject
    DefaultAdapter<GankEntity.ResultsBean> mAdapter;
    @Inject
    RxErrorHandler rxErrorHandler;
    @Inject
    AppManager appManager;

    private int lastUserId = 1;
    @Inject
    List<GankEntity.ResultsBean> date;
    private boolean isFirst = true;
    private int index;

    @Inject
    CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rxErrorHandler = null;
        appManager = null;
    }


    public void requestData(String type, boolean pullToRefresh) {
        if (pullToRefresh && isFirst) {
            lastUserId = 1;
            isFirst = false;
        } else {
            lastUserId++;
        }
        mModel.getGank(type, String.valueOf(lastUserId))
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
                        mRootView.endLoadMore();
                    }
                })
                .compose(RxUtils.applySchdulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GankEntity>(rxErrorHandler) {
                    @Override
                    public void onNext(GankEntity gankEntity) {
                        //                        index = date.size();
                        index = date.size();
                        if (pullToRefresh) {
                            date.addAll(gankEntity.results);
                            mAdapter.notifyDataSetChanged();

                        }

                        // else {
                        //                            mAdapter.notifyItemRangeInserted(0,gankEntity.results.size());
                        //                        }

                        //                        if (pullToRefresh) {
                        //                            mAdapter.notifyDataSetChanged();
                        //                        } else {
                        //                            mAdapter.notifyItemRangeInserted(index, date.size()-index);
                        //                        }
                    }
                });
    }

}
