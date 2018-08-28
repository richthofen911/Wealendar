package net.callofdroidy.wealendar.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import net.callofdroidy.wealendar.R;

public class CoordAxis extends View {

    private Paint paint;
    private Path path;
    private Paint textPaint;

    private int color;

    private String axisXTag;
    private String axisYTag;

    public CoordAxis(Context context) {
        super(context);
    }

    public CoordAxis(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(48);

        path = new Path();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CoordAxis);
        axisXTag = typedArray.getString(R.styleable.CoordAxis_axis_x_tag);
        axisYTag = typedArray.getString(R.styleable.CoordAxis_axis_y_tag);
        color = typedArray.getColor(R.styleable.CoordAxis_axis_color, 0xff000000);

        typedArray.recycle();

        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16);


    }

    public CoordAxis(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {



        path.moveTo(getLeft() + 20, getTop());
        path.lineTo(getLeft() + 20, getBottom() - 20);
        path.lineTo(getRight(), getBottom() - 20);

        canvas.drawPath(path, paint);

        canvas.drawText(axisXTag, getRight() - 80, getBottom() - 40, textPaint);
    }
}
