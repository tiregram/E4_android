package com.tiregram.glove.bluetoothglove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import org.w3c.dom.Text;

public class AccelerometerActivity extends DrawerActivity {

    private TextView mX, mY, mZ;
    double x;

    private GraphView mGraph;

    LineGraphSeries<DataPoint> seriesX,seriesY,seriesZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_accelerometer);

        x = 0;

        mX = (TextView)findViewById(R.id.text_x);
        mY = (TextView)findViewById(R.id.text_y);
        mZ = (TextView)findViewById(R.id.text_z);

        GloveConnectTo.bus.register(this);

        mGraph = (GraphView)findViewById(R.id.graph_accelerometer);

        mGraph.setTitle("Graph Ace");

        seriesX = new LineGraphSeries<>();
        seriesY = new LineGraphSeries<>();
        seriesZ = new LineGraphSeries<>();

        mGraph.addSeries(seriesX);
        mGraph.addSeries(seriesY);
        mGraph.addSeries(seriesZ);

    }

    @Subscribe
    public void answerAvailable(final AnswerAce event) {


        seriesX.appendData(new DataPoint(x++,event.x),true,100);
        seriesY.appendData(new DataPoint(x++,event.y),true,100);
        seriesZ.appendData(new DataPoint(x++,event.z),true,100);

        mX.setText(""+event.x);
        mY.setText(""+event.y);
        mZ.setText(""+event.z);


        mGraph.removeAllSeries();

        mGraph.addSeries(seriesX);
        mGraph.addSeries(seriesY);
        mGraph.addSeries(seriesZ);

       // mGraph.addSeries(series);
      //  mGraph.getSeries().get(0).


    }
}

