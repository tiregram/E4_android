package com.tiregram.glove.bluetoothglove;


import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

public class GyroscopeActivity extends DrawerActivity {

    private TextView mRo, mTheta, mPhi;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_gyroscope);

        mRo = (TextView)findViewById(R.id.text_ro);
        mTheta= (TextView)findViewById(R.id.text_theta);
        mPhi = (TextView)findViewById(R.id.text_phi);

        mGraph = (GraphView)findViewById(R.id.graph_gyroscope);
    }
}
