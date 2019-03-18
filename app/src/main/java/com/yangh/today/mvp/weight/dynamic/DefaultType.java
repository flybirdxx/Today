package com.yangh.today.mvp.weight.dynamic;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by yangH on 2019/3/16.
 */
class DefaultType extends BaseDynamicType {
    public DefaultType(Context context) {
        super(context, true);
    }

    @Override
    public boolean drawWeather(Canvas canvas, float alpha) {
        return false;
    }


    @Override
    protected int[] getSkyBackgroundGradient() {
        return skyBackground.BLACK;
    }
}
