package com.agadimi.loadingdotbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

public class LoadingDotBarView extends View
{
    private final String LOG_TAG = "LoadingDotBarView";

    private int dotColor, dotRadius, barHeight, gapSize;
    private Paint circlePaint;

    private int halfHeight, dotOneX, dotTwoX, dotThreeX;

    public LoadingDotBarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingDotBar,
                0, 0);

        try
        {
            dotColor = a.getColor(R.styleable.LoadingDotBar_dot_color, getResources().getColor(R.color.dot_color_default));
            dotRadius = a.getDimensionPixelSize(R.styleable.LoadingDotBar_dot_radius, getResources().getDimensionPixelSize(R.dimen.dot_radius_default));
            barHeight = a.getDimensionPixelSize(R.styleable.LoadingDotBar_bar_height, getResources().getDimensionPixelSize(R.dimen.bar_height_default));
            gapSize = a.getDimensionPixelSize(R.styleable.LoadingDotBar_gap_size, getResources().getDimensionPixelSize(R.dimen.gap_size_default));
        } finally
        {
            a.recycle();
        }

        init();
    }

    public int getDotColor()
    {
        return dotColor;
    }

    public void setDotColor(int dotColor)
    {
        this.dotColor = dotColor;
        invalidate();
        requestLayout();
    }

    public int getDotRadius()
    {
        return dotRadius;
    }

    public void setDotRadius(int dotRadius)
    {
        this.dotRadius = dotRadius;
        invalidate();
        requestLayout();
    }

    public int getBarHeight()
    {
        return barHeight;
    }

    public void setBarHeight(int barHeight)
    {
        this.barHeight = barHeight;
        invalidate();
        requestLayout();
    }

    public int getGapSize()
    {
        return gapSize;
    }

    public void setGapSize(int gapSize)
    {
        this.gapSize = gapSize;
        invalidate();
        requestLayout();
    }


    private void init()
    {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(dotColor);
    }

    @Override
    public int getPaddingLeft()
    {
        int superPad = super.getPaddingLeft();
        return  superPad> 0 ? superPad : getResources().getDimensionPixelSize(R.dimen.x_padding_default);
    }

    @Override
    public int getPaddingTop()
    {
        int superPad = super.getPaddingTop();
        return superPad > 0 ? superPad : getResources().getDimensionPixelSize(R.dimen.y_padding_default);
    }

    @Override
    public int getPaddingRight()
    {
        int superPad = super.getPaddingRight();
        return superPad > 0 ? superPad : getResources().getDimensionPixelSize(R.dimen.x_padding_default);
    }

    @Override
    public int getPaddingBottom()
    {
        int superPad = super.getPaddingBottom();
        return superPad > 0 ? superPad : getResources().getDimensionPixelSize(R.dimen.y_padding_default);
    }

    /**
     * we need a minimum size based on dot and gap size, so we'll go straight for the required size
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.d(LOG_TAG, String.format("widthMeasureSpec: %d, mode: %d", MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(widthMeasureSpec)));
        Log.d(LOG_TAG, String.format("heightMeasureSpec: %d, mode: %d", MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec)));

        int padLeft = getPaddingLeft();
        int padTop = getPaddingTop();
        int padRight = getPaddingRight();
        int padBottom = getPaddingBottom();

        //calculate the view size
        int width = (3 * 2 * dotRadius) + // 3 dots * 2 radius
                (2 * gapSize) + // 2 gaps
                padLeft +
                padRight;
        int height = barHeight + padTop + padBottom;

        //calculate positions of the dots
        halfHeight = height / 2;
        dotOneX = padLeft + dotRadius;
        dotTwoX = padLeft + (3 * dotRadius) + gapSize;
        dotThreeX = padLeft + (5 * dotRadius) + (2 * gapSize);


        Log.d(LOG_TAG, String.format("final dimensions: %d * %d", width, height));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Log.d(LOG_TAG, dotOneX + ", " + dotTwoX + ", " + dotThreeX);
        Log.d(LOG_TAG, "half height: " + halfHeight);

        canvas.drawCircle(dotOneX, halfHeight, dotRadius, circlePaint);
        canvas.drawCircle(dotTwoX, halfHeight, dotRadius, circlePaint);
        canvas.drawCircle(dotThreeX, halfHeight, dotRadius, circlePaint);
    }
}