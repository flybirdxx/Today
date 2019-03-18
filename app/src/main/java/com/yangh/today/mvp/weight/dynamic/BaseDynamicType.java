package com.yangh.today.mvp.weight.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import java.util.Random;

/**
 * Created by yangH on 2019/3/15.
 */
public abstract class BaseDynamicType {


    static final String TAG = BaseDynamicType.class.getSimpleName();

    private final Context mContext;
    private final float density;
    private final boolean isNight;
    private GradientDrawable skybackground;
    protected int width;
    protected int height;

    public static final class skyBackground {
        public static final int[] BLACK = new int[]{0xff000000, 0xff000000};
        public static final int[] CLEAR_D = new int[]{0xff3d99c2, 0xff4f9ec5};
        public static final int[] CLEAR_N = new int[]{0xff0b0f25, 0xff252b42};


        public static final int[] OVERCAST_D = new int[]{0xff33425f, 0xff617688};//0xff748798, 0xff617688
        public static final int[] OVERCAST_N = new int[]{0xff262921, 0xff23293e};//0xff1b2229, 0xff262921

        public static final int[] RAIN_D = new int[]{0xff4f80a0, 0xff4d748e};
        public static final int[] RAIN_N = new int[]{0xff0d0d15, 0xff22242f};

        public static final int[] FOG_D = new int[]{0xff688597, 0xff44515b};
        public static final int[] FOG_N = new int[]{0xff2f3c47, 0xff24313b};

        public static final int[] SNOW_D = new int[]{0xff4f80a0, 0xff4d748e};//临时用RAIN_D凑数的
        public static final int[] SNOW_N = new int[]{0xff1e2029, 0xff212630};

        public static final int[] CLOUDY_D = new int[]{0xff4f80a0, 0xff4d748e};//临时用RAIN_D凑数的
        public static final int[] CLOUDY_N = new int[]{0xff071527, 0xff252b42};// 0xff193353 };//{ 0xff0e1623, 0xff222830 }

        public static final int[] HAZE_D = new int[]{0xff616e70, 0xff474644};// 0xff999b95, 0xff818e90
        public static final int[] HAZE_N = new int[]{0xff373634, 0xff25221d};

        public static final int[] SAND_D = new int[]{0xffb5a066, 0xffd5c086};//0xffa59056
        public static final int[] SAND_N = new int[]{0xff312820, 0xff514840};

    }


    public static BaseDynamicType setDynamicByType(Context context, WeatherType type) {
        switch (type) {
            case CLEAR_D:
                return new SunnyType(context);
                default:

                    return new DefaultType(context);
        }
    }

    public BaseDynamicType(Context context, boolean isNight) {
        super();
        mContext = context;
        this.density = context.getResources().getDisplayMetrics().density;
        this.isNight = isNight;
    }


    /**
     * @return 设置天空的背景颜色
     */
    public GradientDrawable setSkybackground() {
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getSkyBackgroundGradient());
    }

    protected void drawSkyBackground(Canvas canvas, float alpha) {
        if (skybackground == null) {
            skybackground = setSkybackground();
            skybackground.setBounds(0, 0, width, height);
        }
        skybackground.setAlpha(Math.round(alpha * 255f));
        skybackground.draw(canvas);
    }

    /**
     * 回执天空背景
     *
     * @param canvas
     * @param alpha
     * @return
     */
    public boolean draw(Canvas canvas, float alpha) {
        drawSkyBackground(canvas, alpha);
        boolean needDrawNextFrame = drawWeather(canvas, alpha);
        return needDrawNextFrame;
    }


    /**
     * @return 天空背景的渐变色
     */
    protected int[] getSkyBackgroundGradient() {
        return isNight ? skyBackground.CLEAR_N : skyBackground.CLEAR_D;
    }


    protected void setSize(int w, int h) {
        if (this.width != w && this.height != h) {
            this.width = w;
            this.height = h;
            Log.d(TAG, "setSize");
            if (this.skybackground != null) {
                skybackground.setBounds(0, 0, w, h);
            }
        }
    }

    protected float getFrameOffsetPercent() {
        return 1f / 40f;
    }

    public static int convertAlphaColor(float percent, final int originalColor) {
        int newAlpha = (int) (percent * 255) & 0xff;
        return (newAlpha << 24) | (originalColor & 0xffffff);
    }

    public float dp2px(float dp) {
        return dp * density;
    }

    // 获得0--n之内的不等概率随机整数，0概率最大，1次之，以此递减，n最小
    public static int getRandowInt(int n) {
        int max = n + 1;
        int bigend = ((1 + max) * max) / 2;
        Random rd = new Random();
        int x = Math.abs(rd.nextInt() % bigend);
        int sum = 0;
        for (int i = 0; i < max; i++) {
            sum += (max - i);
            if (sum > x) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取[min, max)内的随机数，越大的数概率越小
     * 参考http://blog.csdn.net/loomman/article/details/3861240
     *
     * @param min
     * @param max
     * @return
     */
    public static float getDownRandFloat(float min, float max) {
        float bigend = ((min + max) * max) / 2f;
        // Random rd = new Random();
        float x = getRandom(min, bigend);// Math.abs(rd.nextInt() % bigend);
        int sum = 0;
        for (int i = 0; i < max; i++) {
            sum += (max - i);
            if (sum > x) {
                return i;
            }
        }
        return min;
    }

    public static float getRandom(float min, float max) {
        if (max < min) {
            throw new IllegalArgumentException("max should bigger than min!!!!");
            // return 0f;
        }
        return (float) (min + Math.random() * (max - min));
    }

    /**
     * 必须取[0,1]之间的float
     *
     * @param alpha
     * @return
     */
    public static float fixAlpha(float alpha) {
        if (alpha > 1f) {
            return 1f;
        }
        if (alpha < 0f) {
            return 0f;
        }
        return alpha;
    }

    public abstract boolean drawWeather(Canvas canvas, float alpha);
}
