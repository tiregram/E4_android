package com.tiregram.glove.bluetoothglove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.squareup.otto.Subscribe;

public class FlexSensorActivity extends DrawerActivity {

    private TextView[] flexG;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flexG = new TextView[3];

        setDrawerContentView(R.layout.activity_flex_sensor);

        GloveConnectTo.bus.register(this);

        flexG[0] = (TextView)findViewById(R.id.text_flex1);
        flexG[1] = (TextView)findViewById(R.id.text_flex2);
        flexG[2] = (TextView)findViewById(R.id.text_flex3);

        mGraph = (GraphView)findViewById(R.id.graph_accelerometer);
    }

    @Subscribe
    public void answerAvailable(final AnswerFlex event) {

        if(event.n<1 || 3<event.n)
            return;

        flexG[event.n-1].setText("lapin"+event.v + " " +event.n);

    }

}
