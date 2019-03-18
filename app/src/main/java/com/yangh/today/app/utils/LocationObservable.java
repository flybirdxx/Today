package com.yangh.today.app.utils;

import android.location.LocationListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * Created by yangH on 2019/3/16.
 */
class LocationObservable extends Observable<AMapLocation> {

    private final AMapLocationClient client;

    public LocationObservable(AMapLocationClient client) {
        this.client = client;
    }

    @Override
    protected void subscribeActual(Observer<? super AMapLocation> observer) {
        LogUtils.d("subscribeActual--" + Thread.currentThread().getName());
        MapLocationListener listener = new MapLocationListener(client, observer);
        observer.onSubscribe(listener);
        client.setLocationListener(listener);
        client.startLocation();
    }

    static class MapLocationListener extends MainThreadDisposable implements AMapLocationListener {

        private final AMapLocationClient client;
        private final Observer<? super AMapLocation> observer;

        public MapLocationListener(AMapLocationClient client, Observer<? super AMapLocation> observer) {
            this.client = client;
            this.observer = observer;
        }

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (isDisposed()) {
                return;
            }
            if (aMapLocation.getErrorCode() == 0) {
                observer.onNext(aMapLocation);
            } else {
                observer.onError(new Throwable(aMapLocation.getErrorInfo() + aMapLocation.getErrorCode()));
            }
        }

        @Override
        protected void onDispose() {
            client.stopLocation();
            client.onDestroy();
            client.setLocationListener(null);
        }
    }
}
