package com.tiregram.glove.bluetoothglove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.squareup.otto.Subscribe;

public class FlexSensorActivity extends DrawerActivity {

    private TextView[] flex;

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flex = new TextView[3];

        setDrawerContentView(R.layout.activity_flex_sensor);

        GloveConnectTo.bus.register(this);

        flex[0] = (TextView)findViewById(R.id.text_flex1);
        flex[1] = (TextView)findViewById(R.id.text_flex2);
        flex[2] = (TextView)findViewById(R.id.text_flex3);

        mGraph = (GraphView)findViewById(R.id.graph_accelerometer);
    }

    @Subscribe
    public void answerAvailable(final AnswerFlex event) {

        if(event.n<1 || 3<event.n)
            return;

        flex[event.n-1].setText(event.value);

    }

}
