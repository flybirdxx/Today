package com.yangh.today.mvp.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yangH on 2019/3/11.
 */
public class CircleImageView extends AppCompatImageView {

    //圆形图片的半径
    private int mRadius;
    //画笔
    private Paint paint;
    //图片缩放比例
    private float mScale;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //因为是圆形图片，应该让狂傲保持一致
        int size = Math.min(getMaxWidth(), getMaxHeight());
        mRadius = size / 2;

        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        Bitmap bitmap = drawableToBitmap(getDrawable());

        //初始化bitmapshader，传入bitmap对象
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //计算缩放比例
        mScale = (mRadius * 0.2f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
        Matrix matrix = new Matrix();
        matrix.setScale(mScale,mScale);
        bitmapShader.setLocalMatrix(matrix);


        paint.setShader(bitmapShader);

        //话圆形，指定好中心坐标，半径，画笔
        canvas.drawCircle(mRadius,mRadius,mRadius,paint);

    }
//写一个drawable转bitmap的方法
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable){
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;

    }
}
