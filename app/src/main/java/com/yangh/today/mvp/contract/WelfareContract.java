package com.yangh.today.mvp.contract;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/3/3.
 */
public interface WelfareContract {
    interface View extends IView {
        RxPermissions getPermission();

        void startLoadMore();

        void hideLoadMore();

        void setNewData(List<GankEntity.ResultsBean> data);

        void addNewData(List<GankEntity.ResultsBean> result);

        FragmentActivity getActivity();

    }

    interface Model extends IModel {
        Observable<GankEntity> getGirl(int pageSize,String page);
    }
}
