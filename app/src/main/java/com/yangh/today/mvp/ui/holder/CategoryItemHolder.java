package com.yangh.today.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.yangh.today.R;
import com.yangh.today.app.utils.TimeUtils;
import com.yangh.today.mvp.model.entity.GankEntity;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by yangH on 2019/2/27.
 */
public class CategoryItemHolder extends BaseHolder<GankEntity.ResultsBean> {
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_who)
    TextView tvWho;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    private static long l;

    public CategoryItemHolder(View view) {
        super(view);
    }

    @Override
    public void setData(GankEntity.ResultsBean data, int position) {

        //        String time = TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.string2Millis(data.getPublishedAt()) / TimeConstants.DAY);
        String date = TimeUtils.getNormalDate(data.getPublishedAt());

        tvPublish.setText(date);
        tvWho.setText(data.getWho());
        tvDesc.setText(data.getDesc());

    }


}


