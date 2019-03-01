package com.yangh.today.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.yangh.today.mvp.contract.DetailContract;
import com.yangh.today.mvp.presenter.DetailPresenter;

/**
 * Created by yangH on 2019/3/1.
 */
public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.View {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(String url) {

    }

    @Override
    public void onFavoriteChange(boolean isFavorite) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
