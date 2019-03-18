package com.yangh.today.mvp.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.app.utils.TimeUtils;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yangH on 2019/3/1.
 */
public class CategoryHolder extends BaseHolder<GankEntity.ResultsBean> {



    private final ImageLoader imageLoader;
    @BindView(R.id.img_item)
    ImageView imgItem;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_acthor)
    TextView tvActhor;
    @BindView(R.id.tv_publish)
    TextView tvPublish;

    public CategoryHolder(View view) {
        super(view);
        imageLoader = ArmsUtils.obtainAppComponentFromContext(AppLifecyclesImpl.getInstance()).imageLoader();

    }

    @Override
    public void setData(GankEntity.ResultsBean data, int position) {
        tvDesc.setText(data.getDesc());
        tvActhor.setText(data.getWho());

        tvPublish.setText(TimeUtils.getNormalDate(data.getPublishedAt()));

        String imgUrl = "";
        List<String> images = data.getImages();
        if (images != null && images.size() > 0) {
            imgUrl = images.get(0);
        }
        if (TextUtils.isEmpty(imgUrl)) {
            imgItem.setVisibility(View.GONE);
        } else {
            imgItem.setVisibility(View.VISIBLE);
//            imageLoader.loadImage(AppLifecyclesImpl.getInstance(),
//                    ImageConfigImpl.builder()
//                            .url(imgUrl)
//                            .placeholder(R.drawable.pic_gray_bg)
//                            .isCenterCrop(true)
//                            .imageView(imgItem)
//                            .build());
            Glide.with(AppLifecyclesImpl.getInstance())
                    .asBitmap()
                    .load(imgUrl)
                    .apply(new RequestOptions().centerCrop().placeholder(R.drawable.pic_gray_bg).override(240,240))
                    .into(imgItem);

        }

    }

}
