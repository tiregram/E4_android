package com.tiregram.glove.bluetoothglove;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.squareup.otto.Bus;


import static android.R.attr.resource;


public class BluetoothAdaptateur extends ArrayAdapter<MyBluetoothDevice> {

    LayoutInflater inflater;
    int resource ;
    private List<MyBluetoothDevice> mItems;
    private  Context context;


    public BluetoothAdaptateur(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        this.resource = textViewResourceId;
        inflater = LayoutInflater.from( context );
        mItems = objects;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {



        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bluetooth_device, parent, false);
        }

        final MyBluetoothDevice  b = mItems.get(position);
        TextView txtName = (TextView) convertView.findViewById(R.id.blue_adap_title);
        txtName.setText(b.getName());

        TextView txtWiki = (TextView) convertView.findViewById(R.id.blue_adap_addr);
        txtWiki.setText(b.getAddr());

        Button butCO = (Button) convertView.findViewById(R.id.Connect_to);
        butCO.setText(">>");
        butCO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent();

                Intent i = new Intent(context, GloveConnectTo.class);
                // sending data to new activit
                i.putExtra("name", b.getName());
                i.putExtra("addr", b.getAddr());

                context.startActivity(i);

            }
        });


        return convertView;
    }

    @Override
    public int getCount()
    {
        return mItems.size();
    }

    @Override
    public MyBluetoothDevice getItem(int position)
    {
        return mItems.get(position);
    }

    @Override
    public void add(MyBluetoothDevice d)
    {
         mItems.add(d);
    }


}
