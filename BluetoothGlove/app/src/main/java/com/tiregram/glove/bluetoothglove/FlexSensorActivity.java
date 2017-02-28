package com.tiregram.glove.bluetoothglove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

public class FlexSensorActivity extends DrawerActivity {

    private TextView mFlex1, mFlex2, mFlex3;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_flex_sensor);

        mFlex1 = (TextView)findViewById(R.id.text_flex1);
        mFlex2 = (TextView)findViewById(R.id.text_flex2);
        mFlex3 = (TextView)findViewById(R.id.text_flex3);

        mGraph = (GraphView)findViewById(R.id.graph_accelerometer);
    }
}
