package com.yangh.today.mvp.presenter;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.yangh.today.mvp.contract.DetailContract;

import java.net.URI;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

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
    DetailPresenter(DetailContract.Model model, DetailContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.rxErrorHandler = handler;
        this.mApplication = application;
        this.appManager = appManager;
        this.imageLoader = imageLoader;
    }

    public void copy(String string) {
        ClipboardManager cm = (ClipboardManager) mApplication.getSystemService(Context.CLIPBOARD_SERVICE);
        //将文本赋值到剪切板
        ClipData data = ClipData.newPlainText("text", string);
        cm.setPrimaryClip(data);
        if (mRootView != null) {
            mRootView.showMessage("复制成功");
        }
    }

    public void openWithBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);
    }

    //    public void getGirl() {
    //        mModel.getGirl()
    //                .subscribeOn(Schedulers.io())
    //                .retryWhen(new RetryWithDelay(3, 2))
    //                .subscribeOn(AndroidSchedulers.mainThread())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .compose(RxUtils.applySchdulers(mRootView))
    //                .subscribe(new ErrorHandleSubscriber<GankEntity>(rxErrorHandler) {
    //                    @Override
    //                    public void onNext(GankEntity gankEntity) {
    //                        mRootView.setData(gankEntity.results.get(0).getUrl());
    //                    }
    //                });
    //    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rxErrorHandler = null;
        this.appManager = null;
        this.imageLoader = null;
        this.appManager = null;
    }
}
