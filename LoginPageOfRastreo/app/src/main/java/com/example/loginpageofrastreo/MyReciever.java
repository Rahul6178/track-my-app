package com.example.loginpageofrastreo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyReciever extends BroadcastReceiver {
    private ArrayList<Details> arrayList = new ArrayList<Details>();
    public static final String ACTION_CUSTOM = "com.example.loginpageofrastreo.MyAction";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_CUSTOM.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            arrayList = (ArrayList<Details>) bundle.getSerializable("list");
            Log.i("recieverlist", arrayList + "");
            if (arrayList != null) {
                Log.i("Reciever List", arrayList + "");
                Toast.makeText(context, "broadcast has been recived", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent("com.example.loginpageofrastreo.MyAction2");
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle mbundle = new Bundle();
                mbundle.putSerializable("list", arrayList);
                myIntent.putExtras(mbundle);
                myIntent.setAction("com.example.loginpageofrastreo.MyAction2");


                context.sendBroadcast(myIntent);
            }
        }
    }
}
