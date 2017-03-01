package com.bosong.demolibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 饼状图控件，只支持两种颜色
 * Created by bosong on 2016/11/30.
 */

public class PieView extends View {
    private static final int DEFAULT_COLOR = 0xffff0000;
    private float mPercent1;
    private int mColor1;
    private int mColor2;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PieView);
        updateStyle(ta);

        init(context);
    }

    private void updateStyle(TypedArray styled){
        mColor1 = styled.getColor(R.styleable.PieView_color1, DEFAULT_COLOR);
        mColor2 = styled.getColor(R.styleable.PieView_color2, DEFAULT_COLOR);
        mPercent1 = styled.getFloat(R.styleable.PieView_percent1, 1.0f);
        styled.recycle();
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPie(canvas);
    }

    // 以12点方向为起点
    private void drawPie(Canvas canvas){
        float angle1 = 360.0f * mPercent1;
        float angle2 = 360.0f - angle1;
        mPaint.setColor(mColor1);
        // 默认以时钟3点的方向为0°，顺时针方向
        canvas.drawArc(new RectF(0, 0, mWidth, mHeight), 270, angle1, true, mPaint);
        mPaint.setColor(mColor2);
        canvas.drawArc(new RectF(0, 0, mWidth, mHeight), 270 + angle1, angle2, true, mPaint);
    }

    public void setPercent1(float percent1){
        this.mPercent1 = percent1;
    }
}