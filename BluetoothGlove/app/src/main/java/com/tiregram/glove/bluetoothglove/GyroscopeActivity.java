package com.tiregram.glove.bluetoothglove;


import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
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

        mGraph.addSeries(seriesX);
        mGraph.addSeries(seriesY);
        mGraph.addSeries(seriesZ);

        GloveConnectTo.bus.register(this);


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

        seriesX.appendData(new DataPoint(x++,event.x),true,100);
        seriesY.appendData(new DataPoint(x++,event.y),true,100);
        seriesZ.appendData(new DataPoint(x++,event.z),true,100);

        mRo.setText(""+event.x);
        mTheta.setText(""+event.y);
        mPhi.setText(""+event.z);

        if(x > 100) {
            mGraph.removeAllSeries();

        }

        mGraph.addSeries(seriesX);
        mGraph.addSeries(seriesY);
        mGraph.addSeries(seriesZ);

    }





}
