package me.tuanna.aadsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * View that draw Vietnam national flag.
 */
public class FlagView extends View {

    private Paint mBackgroundPaint;
    private Paint mStarPaint;
    private Path mStarPath;

    public FlagView(Context context) {
        super(context);
        init();
    }

    public FlagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.RED);

        mStarPaint = new Paint();
        mStarPaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int measuredW = resolveSizeAndState(minW, widthMeasureSpec, 1);

        // Calculate the height based on the flag ratio
        float withToHeightRatio = 2f / 3;
        int minH = (int) (MeasureSpec.getSize(measuredW) * withToHeightRatio);
        int measuredH = resolveSizeAndState(minH, heightMeasureSpec, 0);

        setMeasuredDimension(measuredW, measuredH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Construct the path used to draw the star.
        // Imagine the star is enclosed with a circle, then each of its tip differs from
        // the next by an angle of 360 / 5 = 72 degrees.

        float centerX = w / 2f;
        float centerY = h / 2f;
        float radius = h / 4f;
        double angleFromTheTop;

        mStarPath = new Path();
        // Top point
        mStarPath.moveTo(centerX, centerY - radius);
        // Bottom right point
        angleFromTheTop = Math.toRadians(90 + 72 * 3);
        mStarPath.lineTo(centerX + (float) Math.cos(angleFromTheTop) * radius,
                         centerY - (float) Math.sin(angleFromTheTop) * radius);
        // Top left point
        angleFromTheTop = Math.toRadians(90 + 72);
        mStarPath.lineTo(centerX + (float) Math.cos(angleFromTheTop) * radius,
                         centerY - (float) Math.sin(angleFromTheTop) * radius);
        // Top right point
        angleFromTheTop = Math.toRadians(90 + 72 * 4);
        mStarPath.lineTo(centerX + (float) Math.cos(angleFromTheTop) * radius,
                         centerY - (float) Math.sin(angleFromTheTop) * radius);
        // Bottom left point
        angleFromTheTop = Math.toRadians(90 + 72 * 2);
        mStarPath.lineTo(centerX + (float) Math.cos(angleFromTheTop) * radius,
                         centerY - (float) Math.sin(angleFromTheTop) * radius);
        // Close the path
        mStarPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);
        canvas.drawPath(mStarPath, mStarPaint);
    }
}