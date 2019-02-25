package com.yangh.today.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yangH on 2019/2/25.
 */
public class RxUtils {
    public RxUtils() {
    }

    public static <T> ObservableTransformer<T, T> applySchdulers(final IView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.hideLoading()).compose(RxLifecycleUtils.bindToLifecycle(view));
    }
}
