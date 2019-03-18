package com.yangh.today.mvp.adapter;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.List;

/**
 * Created by yangH on 2019/3/3.
 */
public class MZAdapter extends BaseQuickAdapter<GankEntity.ResultsBean, BaseViewHolder> {

    private final ImageLoader imageLoader;
    private final Application context;

    public MZAdapter(@Nullable List<GankEntity.ResultsBean> data) {
        super(R.layout.item_welfare_fix, data);
        context = AppLifecyclesImpl.getInstance();
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();

    }

    @Override
    protected void convert(BaseViewHolder helper, GankEntity.ResultsBean item) {
//        PanoramaImageView imageView = (PanoramaImageView) helper.getView(R.id.img_mz);
//        imageView.setGyroscopeObserver(new GyroscopeObserver());
//        imageLoader.loadImage(context, ImageConfigImpl.builder()
//                .url(item.url)
//                .isCenterCrop(true)
//                .placeholder(R.drawable.pic_gray_bg)
//                .imageView(((ImageView) helper.getView(R.id.img_mz)))
//                .build());
        Glide.with(context)
                .asBitmap()
                .load(item.getUrl())
                .apply(new RequestOptions().centerCrop().placeholder(R.drawable.pic_gray_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .thumbnail(0.1f)
                .into(((ImageView) helper.getView(R.id.img_mz)));
    }


}
