package net.callofdroidy.wealendar.ui.customview;

import android.arch.persistence.room.OnConflictStrategy;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Grid extends View {

    private Paint paint;
    private Path path;

    public Grid(Context context) {
        super(context);
    }

    public Grid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        path = new Path();
    }

    public Grid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16);

        path.moveTo(getLeft() + 20, getTop());
        path.lineTo(getLeft() + 20, getBottom() - 20);
        path.lineTo(getRight(), getBottom() - 20);

        canvas.drawPath(path, paint);
    }
}
