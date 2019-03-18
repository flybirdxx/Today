package com.yangh.today.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.ui.holder.CategoryHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yangH on 2019/3/1.
 */
public class MyAdapter extends DefaultAdapter<GankEntity.ResultsBean> {

    private  Context context;
    private  LayoutInflater inflater;


    public MyAdapter(List<GankEntity.ResultsBean> infos) {
        super(infos);
        inflater = LayoutInflater.from(AppLifecyclesImpl.getInstance());
    }

    @Override
    public BaseHolder<GankEntity.ResultsBean> getHolder(View v, int viewType) {
        return new CategoryHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_category_fix;
    }
}
