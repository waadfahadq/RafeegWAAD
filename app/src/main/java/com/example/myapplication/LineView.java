package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class LineView extends View {

    private Paint paint = new Paint();

    private PointF pointA, pointB;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setColor(Color.RED);

        paint.setStrokeWidth(15);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y,paint);
        VectorDrawableCompat starterPin = VectorDrawableCompat.create(getContext().getResources(), R.drawable.ic_radio_button_checked_black_24dp, null);
        starterPin.setBounds(0, 0, starterPin.getIntrinsicWidth(), starterPin.getIntrinsicHeight());
        canvas.translate(630,70);

        VectorDrawableCompat LocationPin = VectorDrawableCompat.create(getContext().getResources(), R.drawable.ic_place_black_24dp, null);
        LocationPin.setBounds(0, 0, LocationPin.getIntrinsicWidth(), LocationPin.getIntrinsicHeight());
        canvas.translate(250,1250);
        LocationPin.draw(canvas);



        super.onDraw(canvas);
    }



    public void setPointA(PointF point)
    {
        pointA = point ;
    }

    public void setPointB(PointF point)
    {
        pointB = point ;
    }

    public void draw()
    {
        invalidate();
        requestLayout();
    }
}
