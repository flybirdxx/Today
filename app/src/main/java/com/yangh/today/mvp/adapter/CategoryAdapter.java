package com.yangh.today.mvp.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yangh.today.R;
import com.yangh.today.mvp.ui.holder.CategoryItemHolder;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.List;

/**
 * Created by yangH on 2019/2/27.
 */
public class CategoryAdapter extends DefaultAdapter<GankEntity.ResultsBean> {

    public CategoryAdapter(List<GankEntity.ResultsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GankEntity.ResultsBean> getHolder(View v, int viewType) {
        return new CategoryItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_category;
    }


}
