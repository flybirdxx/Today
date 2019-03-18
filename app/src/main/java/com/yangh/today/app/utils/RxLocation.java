package com.yangh.today.app.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;

import io.reactivex.Observable;

/**
 * Created by yangH on 2019/3/16.
 */
public class RxLocation {

    private AMapLocationClient client;

    public RxLocation(Context context, Builder builder) {
        client = new AMapLocationClient(context.getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(builder.mode)
                .setOnceLocation(false)
                .setInterval(2000)
                .setNeedAddress(builder.needAddress);
    }

    public static Builder getInstance(Context context) {
        return new Builder(context);
    }

    public Observable<AMapLocation> locate(){
        return new LocationObservable(client);
    }

    public static class Builder {
        private AMapLocationClientOption.AMapLocationMode mode;
        private boolean onceLocationLatest;
        private boolean needAddress;
        private boolean wifiScan;
        private boolean mockEnable;
        private long timeout;
        private boolean cache;
        private Context context;

        public Builder(Context context) {
            this.context = context;
            mode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
            this.needAddress = true;
            this.wifiScan = true;
            this.onceLocationLatest = false;
            this.timeout = 20000;
            this.mockEnable = false;
            this.cache = false;
        }

        public Builder mode(AMapLocationClientOption.AMapLocationMode mode) {
            this.mode = mode;
            return this;
        }


        public Builder onceLocationLatest(boolean onceLocationLatest) {
            this.onceLocationLatest = onceLocationLatest;
            return this;
        }

        public Builder needAddress(boolean needAddress) {
            this.needAddress = needAddress;
            return this;
        }

        public Builder wifiScan(boolean wifiScan) {
            this.wifiScan = wifiScan;
            return this;
        }

        public Builder mockEnable(boolean mockEnable) {
            this.mockEnable = mockEnable;
            return this;
        }

        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder cache(boolean cache) {
            this.cache = cache;
            return this;
        }

        public RxLocation build() {
            return new RxLocation(context, this);
        }
    }
}
