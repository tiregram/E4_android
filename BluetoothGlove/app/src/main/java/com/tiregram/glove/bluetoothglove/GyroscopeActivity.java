package com.tiregram.glove.bluetoothglove;


import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.squareup.otto.Subscribe;

public class GyroscopeActivity extends DrawerActivity {

    private TextView mRo, mTheta, mPhi;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_gyroscope);


    }


    public void onStart()
    {
        super.onStart();
        mRo    = (TextView)findViewById(R.id.text_ro);
        mTheta = (TextView)findViewById(R.id.text_theta);
        mPhi   = (TextView)findViewById(R.id.text_phi);

        mGraph = (GraphView)findViewById(R.id.graph_gyroscope);

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

        mRo.setText(""+event.x);
        mTheta.setText(""+event.y);
        mPhi.setText(""+event.z);

    }



}
