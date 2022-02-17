package com.cz.jcamera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.cz.jcamera.util.ScreenUtils;


/**
 * 自定义聚焦位置的矩形框
 */
public class FoucsView extends View {
    private int size;
    private int center_x;
    private int center_y;
    private int length;
    private Paint mPaint;

    public FoucsView(Context context) {
        this(context, null);
    }

    public FoucsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoucsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.size = ScreenUtils.getScreenWidth(context) / 3;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//设置防抖动
        mPaint.setColor(0xEE16AE16);
        mPaint.setStrokeWidth(4);//设置线宽
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为空心
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        center_x = (int) (size / 2.0);
        center_y = (int) (size / 2.0);
        length = (int) (size / 2.0) - 2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制矩形
        canvas.drawRect(center_x - length, center_y - length, center_x + length, center_y + length, mPaint);
        canvas.drawLine(2, getHeight() / 2, size / 10, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() - 2, getHeight() / 2, getWidth() - size / 10, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, 2, getWidth() / 2, size / 10, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() - 2, getWidth() / 2, getHeight() - size / 10, mPaint);
    }
}
