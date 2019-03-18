package com.yangh.today.mvp.weight.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangH on 2019/3/16.
 */
public class SunnyType extends BaseDynamicType {


    private final GradientDrawable drawable;
    private static final int SUNNY_COUNT = 3;
    private final static float centerOfWidth = 0.02f;

    private List<sunnyHolder> holders = new ArrayList<>();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SunnyType(Context context) {
        super(context, false);
        drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{0x20ffffff, 0x10ffffff});
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        mPaint.setColor(0x3ffffff);
    }

    @Override
    public boolean drawWeather(Canvas canvas, float alpha) {
        float size = width * centerOfWidth;
        for (sunnyHolder holder : holders) {
            holder.updateRandom(drawable, alpha);
            drawable.draw(canvas);
        }
        mPaint.setColor(Color.argb((int) (alpha * 0.18f * 255f), 0xff, 0xff, 0xff));
        canvas.drawCircle(size, size, width * 0.12f, mPaint);
        return false;
    }

    @Override
    protected void setSize(int w, int h) {
        super.setSize(w, h);
        if (this.holders.size() >= 0) {
            float minSize = width * 0.16f;
            float maxSize = width * 0.15f;
            float center = width * centerOfWidth;
            float deltaSize = (maxSize - minSize) / SUNNY_COUNT;
            for (int i = 0; i < SUNNY_COUNT; i++) {
                float curSize = maxSize - 1 * deltaSize * getRandom(0.9f, 1.1f);
                sunnyHolder holder = new sunnyHolder(center, center, curSize, curSize);
                holders.add(holder);
            }
        }
    }


    public static class sunnyHolder {
        public float x;
        public float y;
        public float w;
        public float h;
        public final float maxAlpha = 1f;
        public float curAlpha;// [0,1]
        public boolean alphaIsGrowing = true;
        private final float minAlpha = 0.5f;

        public sunnyHolder(float x, float y, float w, float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.curAlpha = getRandom(minAlpha, maxAlpha);
        }

        public void updateRandom(GradientDrawable drawable, float alpha) {
            float delta = getRandom(0.002f * maxAlpha, 0.005f * maxAlpha);
            if (alphaIsGrowing) {
                curAlpha += delta;
                if (curAlpha > maxAlpha) {
                    curAlpha = maxAlpha;
                    alphaIsGrowing = false;
                }
            } else {
                curAlpha -= delta;
                if (curAlpha < minAlpha) {
                    curAlpha = minAlpha;
                    alphaIsGrowing = true;
                }
            }

            final int left = Math.round(x - w / 2f);
            final int right = Math.round(x + w / 2f);
            final int top = Math.round(y - h / 2f);
            final int bottom = Math.round(y + h / 2f);
            drawable.setBounds(left, top, right, bottom);
            drawable.setGradientRadius(w / 2.2f);
            drawable.setAlpha(((int) (255 * curAlpha * alpha)));
        }
    }
}
