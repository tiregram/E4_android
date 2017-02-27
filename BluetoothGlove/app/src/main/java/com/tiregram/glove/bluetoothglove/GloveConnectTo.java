package com.tiregram.glove.bluetoothglove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GloveConnectTo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glove_connect_to);

        Intent i = this.getIntent();
        System.out.println("name:"+i.getStringExtra("name"));
        System.out.println("addr:"+i.getStringExtra("addr"));
    }

    @Override
    protected  void onStart()
    {
        super.onStart();

        Intent i = this.getIntent();

        TextView tv_name = (TextView) this.findViewById(R.id.ConnectTo_name);
        TextView tv_addr = (TextView) this.findViewById(R.id.ConnectTo_addr);
        TextView tv_state = (TextView) this.findViewById(R.id.ConnectTo_state);

        tv_name.setText(i.getStringExtra("name"));
        tv_addr.setText(i.getStringExtra("addr"));


    }

}
