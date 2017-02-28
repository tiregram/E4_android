package com.tiregram.glove.bluetoothglove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

public class AccelerometerActivity extends DrawerActivity {

    private TextView mX, mY, mZ;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_accelerometer);

        mX = (TextView)findViewById(R.id.text_x);
        mY = (TextView)findViewById(R.id.text_y);
        mZ = (TextView)findViewById(R.id.text_z);

        mGraph = (GraphView)findViewById(R.id.graph_accelerometer);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        mGraph.addSeries(series);
    }
}

