package com.yangh.today.mvp.contract;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.mvp.model.entity.GankEntity;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/2/27.
 */
public interface CategoryContract {
    interface View extends IView {
        void startLoadMore();

        void endLoadMore();

        FragmentActivity getActivity();

        RxPermissions getRxpermissions();
    }

    interface Model extends IModel {
        Observable<GankEntity> getGank(String type, String page);

        //        Observable<GankEntity> getGankMore(String type, String page);
    }
}
