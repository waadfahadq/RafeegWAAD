package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CarvedBottomNavigationView extends BottomNavigationView {

    private android.graphics.Path mpath ;
    private Paint mPaint ;

    public  final int  Carve_circle_raduis = 90;


    //<<the coordinate of the first curve>>
    public Point mFirstCurveStartPoint = new Point();
    public Point mFirstCurveEndPoint = new Point();
    public Point mFirstCurveControlPoint1 = new Point();
    public Point mFirstCurveControlPoint2 = new Point();

    //<<the coordinate of the Second curve>>
    public Point mSecondCurveStartPoint = new Point();
    public Point mSecondCurveEndPoint = new Point();
    public Point mSecondCurveControlPoint1 = new Point();
    public Point mSecondCurveControlPoint2 = new Point();


    public int navigationBarWidth , navigationBarHight ;



    //_______________________________________________________
    public CarvedBottomNavigationView(Context context) {
        super(context);
        initView();
    }

    public CarvedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CarvedBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        mpath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        navigationBarWidth = getWidth();
        navigationBarHight = getHeight();

        mFirstCurveStartPoint.set((navigationBarWidth/2)
                -(Carve_circle_raduis*2)
                -(Carve_circle_raduis/3),0);


        mFirstCurveEndPoint.set((navigationBarWidth/2)
        ,Carve_circle_raduis
        +(Carve_circle_raduis/4));

        mSecondCurveStartPoint = mSecondCurveEndPoint;
        mSecondCurveEndPoint.set((navigationBarWidth/2)+(Carve_circle_raduis*2)+(Carve_circle_raduis/3),0);

        mFirstCurveControlPoint1.set(mFirstCurveEndPoint.x - (Carve_circle_raduis*2)+Carve_circle_raduis,mFirstCurveEndPoint.y);

        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x-(Carve_circle_raduis*2)+Carve_circle_raduis,mFirstCurveEndPoint.y);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x+(Carve_circle_raduis*2)-Carve_circle_raduis,mSecondCurveStartPoint.y);

        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x-(Carve_circle_raduis+(Carve_circle_raduis/4)),mSecondCurveEndPoint.y);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mpath.reset();
        mpath.moveTo(0,0);
        mpath.lineTo(mFirstCurveStartPoint.x,mFirstCurveStartPoint.y);

        mpath.cubicTo(mFirstCurveControlPoint1.x,mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x,mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x,mFirstCurveEndPoint.y);


        mpath.cubicTo(mSecondCurveControlPoint1.x,mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x,mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x,mSecondCurveEndPoint.y);

        mpath.lineTo(navigationBarWidth,0);
        mpath.lineTo(navigationBarWidth,navigationBarHight);
        mpath.lineTo(0,navigationBarHight);
        mpath.close();

        canvas.drawPath(mpath,mPaint);




    }
}
