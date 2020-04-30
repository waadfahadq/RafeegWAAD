package com.example.myapplication;

import android.graphics.PointF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Map extends AppCompatActivity {

    PointF Elisabetta = new PointF(250,570);

    PointF Uterque = new PointF(430,70);

    PointF Stradivarius = new PointF(250,200);

    PointF Zara = new PointF(430,480);

    PointF Coach = new PointF(250,1250);

    PointF Ipekyol = new PointF(430,1250);

    private LineView mLineView ;


    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map);

        mLineView = (LineView) findViewById(R.id.lineView1);

        mLineView.setPointA(Zara);

        mLineView.setPointB(Coach);

        mLineView.draw();

    }
}


