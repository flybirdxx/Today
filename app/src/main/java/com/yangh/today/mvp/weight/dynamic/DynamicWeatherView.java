package com.yangh.today.mvp.weight.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;


/**
 * Created by yangH on 2019/3/16.
 */
public class DynamicWeatherView extends SurfaceView implements SurfaceHolder.Callback {

    private float curTypeAlpha;
    private BaseDynamicType curDynamic;
    private BaseDynamicType preType;
    private WeatherType curType = WeatherType.DEFAULT;
    private int mWidth;
    private int mHeight;
    private DrawThread drawThread;

    public DynamicWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        curTypeAlpha = 0f;
        drawThread = new DrawThread();
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        drawThread.start();
    }

    /**
     * 添加动态背景
     *
     * @param type
     */
    private void setDynamic(BaseDynamicType type) {
        if (type == null) {
            return;
        }
        curTypeAlpha = 0f;
        if (this.curDynamic != null) {
            this.preType = curDynamic;
        }
        this.curDynamic = type;
    }

    /**
     * @param type 设置动态背景类型
     */
    public void setType(WeatherType type) {
        if (type == null) {
            return;
        }
        if (type != curType) {
            curType = type;
            setDynamic(BaseDynamicType.setDynamicByType(getContext(), curType));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private boolean drawSurface(Canvas canvas) {
        final int w = mWidth;
        final int h = mHeight;
        if (w == 0 || h == 0) {
            return true;
        }
        boolean needDrawNextFrame = false;
        if (curDynamic != null) {
            curDynamic.setSize(w, h);
            needDrawNextFrame = curDynamic.draw(canvas, curTypeAlpha);
        }
        if (preType != null && curTypeAlpha < 1f) {
            needDrawNextFrame = true;
            preType.setSize(w, h);
            preType.draw(canvas, 1f - curTypeAlpha);
        }
        if (curTypeAlpha < 1f) {
            curTypeAlpha += 0.04f;
            if (curTypeAlpha > 1) {
                curTypeAlpha = 1f;
                preType = null;
            }
        }
        return needDrawNextFrame;
    }

    public void onResume(){
        synchronized (drawThread){
            drawThread.isRunning=true;
            drawThread.notify();
        }
        Log.i(TAG, "onResume: ");
    }
    public void onPause(){
        synchronized (drawThread) {
            drawThread.isRunning = false;
            drawThread.notify();
        }
        Log.i(TAG, "onPause");
    }

    public void onDestory(){
        synchronized (drawThread) {
           drawThread.mQuit = true;
           drawThread.notify();
        }
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (drawThread) {
            drawThread.mSurface = holder;
            drawThread.notify();
        }
        Log.i(TAG, "surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (drawThread) {
            drawThread.mSurface = holder;
            drawThread.notify();
            while (drawThread.mActive){
                try {
                    drawThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.removeCallback(this);
        Log.i(TAG, "surfaceDestroyed: ");
    }


    private class DrawThread extends Thread {

        SurfaceHolder mSurface;
        boolean isRunning;
        boolean mActive;
        boolean mQuit;

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (mSurface == null || !isRunning) {
                        if (mActive) {
                            mActive = false;
                            notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!mActive) {
                        mActive = true;
                        notify();
                    }
                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    Canvas canvas = mSurface.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        drawSurface(canvas);
                    }

                }
            }
        }
    }


}
