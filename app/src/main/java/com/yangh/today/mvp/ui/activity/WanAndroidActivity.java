package com.yangh.today.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.di.component.AppComponent;
import com.yangh.today.R;
import com.yangh.today.app.ARouterPaths;
import com.yangh.today.app.base.BaseActivity;
import com.yangh.today.app.utils.RxLocation;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yangH on 2019/3/15.
 */
@Route(path = ARouterPaths.WAN_ANDROID)
public class WanAndroidActivity extends BaseActivity {
    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StringBuffer sb = new StringBuffer();
        RxLocation.getInstance(this)
                .mode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
                .cache(true)
                .onceLocationLatest(true)
                .needAddress(false)
                .mockEnable(true)
                .wifiScan(false)
                .timeout(5000)
                .build().locate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AMapLocation>() {
                    @Override
                    public void accept(AMapLocation aMapLocation) throws Exception {
                        sb.append("纬度" + aMapLocation.getLatitude());
                        sb.append("经度" + aMapLocation.getLongitude());
                        sb.append("国家" + aMapLocation.getCountry());
                        sb.append("省" + aMapLocation.getProvince());
                        sb.append("城市" + aMapLocation.getCity());
                        sb.append("城区" + aMapLocation.getDistrict());
                        sb.append("街道" + aMapLocation.getStreet());

                        tvText.setText(sb.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        LogUtils.d("WandroidActivity" + message);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
