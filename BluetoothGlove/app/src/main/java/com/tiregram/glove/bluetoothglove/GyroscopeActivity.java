package com.tiregram.glove.bluetoothglove;


import android.graphics.Color;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

public class GyroscopeActivity extends DrawerActivity {

    double x;
    private TextView mRo, mTheta, mPhi;
    LineGraphSeries<DataPoint> seriesX,seriesY,seriesZ;
    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x=0;
        setDrawerContentView(R.layout.activity_gyroscope);


    }


    public void onStart()
    {
        super.onStart();
        mRo    = (TextView)findViewById(R.id.text_ro);
        mTheta = (TextView)findViewById(R.id.text_theta);
        mPhi   = (TextView)findViewById(R.id.text_phi);

        mGraph = (GraphView)findViewById(R.id.graph_gyroscope);

        mGraph.setTitle("Graph Gyro");

        seriesX = new LineGraphSeries<>();
        seriesY = new LineGraphSeries<>();
        seriesZ = new LineGraphSeries<>();

        seriesX.setColor(Color.argb(255,255,0,0));
        seriesY.setColor(Color.argb(255,0,255,0));
        seriesZ.setColor(Color.argb(255,0,0,255));

        mGraph.addSeries(seriesX);
        mGraph.addSeries(seriesY);
        mGraph.addSeries(seriesZ);


        GloveConnectTo.bus.register(this);
        Viewport vp = mGraph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(200);


    }

    public void onResume()
    {
        super.onResume();


    }
    public void onPause()
    {
        super.onPause();

    }

    @Subscribe
    public void answerAvailable(final AnswerGyro event) {

        seriesX.appendData(new DataPoint(x++,event.x),true,200);
        seriesY.appendData(new DataPoint(x,event.y),true,200);
        seriesZ.appendData(new DataPoint(x,event.z),true,200);

        mRo.setText(""+event.x);
        mTheta.setText(""+event.y);
        mPhi.setText(""+event.z);





    }





}
