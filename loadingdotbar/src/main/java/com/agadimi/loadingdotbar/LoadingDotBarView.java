package com.agadimi.loadingdotbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class LoadingDotBarView extends View implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener
{
    private final String LOG_TAG = "LoadingDotBarView";

    private int dotColor, dotRadius, barHeight, gapSize, duration, animationCycleDelay;
    private Paint dotbarPaint;

    private int halfHeight, dotOneX, dotTwoX, dotThreeX, movementRange;

    private ValueAnimator animator;
    private int currentBarHeightONE, currentBarHeightTWO, currentBarHeightTHREE;
    private boolean runAnimation = false;

    public LoadingDotBarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingDotBarView,
                0, 0);

        try
        {
            dotColor = a.getColor(R.styleable.LoadingDotBarView_dot_color, getResources().getColor(R.color.dot_color_default));
            dotRadius = a.getDimensionPixelSize(R.styleable.LoadingDotBarView_dot_radius, getResources().getDimensionPixelSize(R.dimen.dot_radius_default));
            barHeight = a.getDimensionPixelSize(R.styleable.LoadingDotBarView_bar_height, getResources().getDimensionPixelSize(R.dimen.bar_height_default));
            gapSize = a.getDimensionPixelSize(R.styleable.LoadingDotBarView_gap_size, getResources().getDimensionPixelSize(R.dimen.gap_size_default));
            duration = a.getInt(R.styleable.LoadingDotBarView_duration, 1000);
            animationCycleDelay = a.getInt(R.styleable.LoadingDotBarView_animation_cycle_delay, 1000);
        } finally
        {
            a.recycle();
        }

        init();
    }

    private void init()
    {
        movementRange = barHeight - (2 * dotRadius);

        dotbarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotbarPaint.setColor(dotColor);

        animator = ValueAnimator.ofInt(0, 7 * (movementRange / 2)); // animating dots is divided to 7 parts
        animator.setDuration(duration);
        animator.addUpdateListener(this);
        animator.addListener(this);
    }

    public void start()
    {
        runAnimation = true;
        animator.setStartDelay(0);
        animator.start();
    }

    public void end(boolean immediateStop)
    {
        runAnimation = false;

        if (immediateStop)
        {
            animator.end();
        }
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

    /**
     * we need a minimum size based on dot and gap size, so we'll go straight for the required size
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
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

        //calculate positions and helper sizes
        halfHeight = height / 2;
        dotOneX = padLeft + dotRadius;
        dotTwoX = padLeft + (3 * dotRadius) + gapSize;
        dotThreeX = padLeft + (5 * dotRadius) + (2 * gapSize);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        drawBar(canvas, dotOneX, currentBarHeightONE);
        drawBar(canvas, dotTwoX, currentBarHeightTWO);
        drawBar(canvas, dotThreeX, currentBarHeightTHREE);
    }


    /**
     * to draw a rounded bar, we draw two circles and a rectangle in between covering half of the circles
     *
     * @param canvas
     * @param barXCenter
     * @param barHeight
     */
    private void drawBar(Canvas canvas, int barXCenter, int barHeight)
    {
        if (barHeight == 0)
        {
            canvas.drawCircle(barXCenter, halfHeight, dotRadius, dotbarPaint);
        } else if (barHeight == 1)
        {
            canvas.drawCircle(barXCenter, halfHeight, dotRadius, dotbarPaint);
            canvas.drawCircle(barXCenter, halfHeight + 1, dotRadius, dotbarPaint);
        } else
        {
            int halfBarHeight = barHeight / 2;
            canvas.drawCircle(barXCenter, halfHeight + halfBarHeight, dotRadius, dotbarPaint); // draw below circle
            canvas.drawCircle(barXCenter, halfHeight - halfBarHeight, dotRadius, dotbarPaint); // draw above circle
            canvas.drawRect(barXCenter - dotRadius,
                    halfHeight + halfBarHeight,
                    barXCenter + dotRadius,
                    halfHeight - halfBarHeight,
                    dotbarPaint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation)
    {
        int value = (int) animation.getAnimatedValue();
        int halfMovementRange = movementRange / 2;

        byte animationPart = (byte) (value / halfMovementRange);

        switch (animationPart)
        {
            case 0:
                currentBarHeightONE = value;
                currentBarHeightTWO = 0;
                currentBarHeightTHREE = 0;
                break;

            case 1:
                currentBarHeightONE = value;
                currentBarHeightTWO = value - halfMovementRange;
                currentBarHeightTHREE = 0;
                break;

            case 2:
                currentBarHeightONE = movementRange;
                currentBarHeightTWO = value - halfMovementRange;
                currentBarHeightTHREE = value - movementRange;
                break;

            case 3:
                currentBarHeightONE = movementRange - (value - (3 * halfMovementRange));
                currentBarHeightTWO = movementRange;
                currentBarHeightTHREE = value - movementRange;
                break;

            case 4:
                currentBarHeightONE = movementRange - (value - (3 * halfMovementRange));
                currentBarHeightTWO = movementRange - (value - (4 * halfMovementRange));
                currentBarHeightTHREE = movementRange;
                break;

            case 5:
                currentBarHeightONE = 0;
                currentBarHeightTWO = movementRange - (value - (4 * halfMovementRange));
                currentBarHeightTHREE = movementRange - (value - (5 * halfMovementRange));
                break;

            case 6:
                currentBarHeightONE = 0;
                currentBarHeightTWO = 0;
                currentBarHeightTHREE = movementRange - (value - (5 * halfMovementRange));
                break;
        }

        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation)
    {
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        if (runAnimation)
        {
            animator.setStartDelay(animationCycleDelay);
            animator.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation)
    {
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
    }
}