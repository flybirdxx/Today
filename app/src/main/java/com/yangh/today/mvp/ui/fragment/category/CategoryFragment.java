package com.yangh.today.mvp.ui.fragment.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.R;
import com.yangh.today.app.LinearLayoutManagerWrapper;
import com.yangh.today.di.component.DaggerCategoryComponent;
import com.yangh.today.di.module.CategoryModule;
import com.yangh.today.mvp.contract.CategoryContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.presenter.CategoryPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.yangh.today.app.ARouterPaths.MAIN_DETAIL;
import static com.yangh.today.app.EventTag.EXTRA_DETAIL;

/**
 * Created by yangH on 2019/2/27.
 */
public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View, OnRefreshListener, OnLoadMoreListener, OnRefreshLoadMoreListener, DefaultAdapter.OnRecyclerViewItemClickListener {

    @Inject
    DefaultAdapter<GankEntity.ResultsBean> adapter;
    @Inject
    RxPermissions rxPermissions;
    @Inject
    List<GankEntity.ResultsBean> data;
    @BindView(R.id.recycle_2_news)
    RecyclerView recycle2News;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String type;


    public static CategoryFragment setArguments(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getArguments().getString("category");
        mPresenter.requestData(type, true);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setEnableLastTime(true));
        refreshLayout.setOnRefreshLoadMoreListener(this);
        ArmsUtils.configRecyclerView(recycle2News, new LinearLayoutManagerWrapper(getActivity()));
        recycle2News.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void startLoadMore() {

        onLoadMore(refreshLayout);

    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadMore(true);
    }

    @Override
    public RxPermissions getRxpermissions() {
        return rxPermissions;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> refreshLayout.setEnableRefresh(true));
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh(true);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestData(type, true);
        refreshLayout.finishLoadMore(1000);
    }


    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        GankEntity.ResultsBean resultsBean = (GankEntity.ResultsBean) data;
        ARouter.getInstance().build(MAIN_DETAIL)
                .withSerializable(EXTRA_DETAIL, resultsBean)
                .navigation();
    }
}
