package com.yangh.today.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yangh.today.mvp.model.entity.GankEntity;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/3/1.
 */
public interface DetailContract {
    interface View extends IView {
        void setData(String url);

        void onFavoriteChange(boolean isFavorite);
    }

    interface Model extends IModel {
        Observable<GankEntity> getGirl();
    }
}
