package com.yangh.today.mvp.ui.fragment.walfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.app.base.SpacesItemDecoration;
import com.yangh.today.di.component.DaggerWelfraeComponent;
import com.yangh.today.di.module.WelfareModule;
import com.yangh.today.mvp.adapter.MZAdapter;
import com.yangh.today.mvp.contract.WelfareContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.presenter.WelfarePresenter;
import com.yangh.today.mvp.ui.activity.MainActivity;
import com.yangh.today.mvp.ui.activity.PicBrowserActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yangH on 2019/2/27.
 */
public class WelfareFragment extends BaseFragment<WelfarePresenter> implements WelfareContract.View, OnRefreshListener, OnRefreshLoadMoreListener {


    @Inject
    List<GankEntity.ResultsBean> data;
    @Inject
    GridLayoutManager layoutManager;
    @Inject
    GyroscopeObserver observer;

    @BindView(R.id.recycle_2_meizi)
    RecyclerView recycle2Meizi;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    Unbinder unbinder;
    private MZAdapter adapter;
    private ArrayList<String> imgUrlList = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWelfraeComponent.builder()
                .appComponent(appComponent)
                .welfareModule(new WelfareModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welfare, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).getTopBar().setTitle("妹子");
        }
        mPresenter.requestGirl(true);
        ArmsUtils.configRecyclerView(recycle2Meizi, layoutManager);
        recycle2Meizi.addItemDecoration(new SpacesItemDecoration(10));

        refresh.setOnRefreshLoadMoreListener(this);
        adapter = new MZAdapter(data);
        recycle2Meizi.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                imgUrlList.clear();
                for (int i = 0; i < data.size(); i++) {
                    imgUrlList.add(data.get(i).getUrl());
                }
                Intent intent = new Intent(getActivity(), PicBrowserActivity.class);
                intent.putStringArrayListExtra(PicBrowserActivity.ImgList_key, imgUrlList);
                intent.putExtra(PicBrowserActivity.CurrentPosition, position);
                startActivity(intent);
                ToastUtils.showShort(position);
            }
        });

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public RxPermissions getPermission() {
        return null;
    }

    @Override
    public void startLoadMore() {

    }


    @Override
    public void hideLoadMore() {

    }

    @Override
    public void setNewData(List<GankEntity.ResultsBean> data) {
        adapter.addData(data);
    }

    @Override
    public void addNewData(List<GankEntity.ResultsBean> result) {
        adapter.setNewData(result);
        adapter.notifyItemRangeInserted(data.size() - 1, result.size());

        //        adapter.addData(result);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(1000);
    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> refresh.setEnableRefresh(true));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestGirl(true);
        refreshLayout.finishLoadMore(1000);
    }
}
