package com.example.myapplication786;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import java.util.ArrayList;

public class MyReciever extends BroadcastReceiver {
    private ArrayList<Details> arrayList = new ArrayList<Details>();
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        arrayList = (ArrayList<Details>) bundle.getSerializable("list");
        if(arrayList!=null) {
            Log.e("Reciever List",arrayList+"");
            Intent myIntent = new Intent("MyAction");
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle mbundle = new Bundle();
            bundle.putSerializable("list", arrayList);
            myIntent.putExtras(mbundle);
            myIntent.setAction("MyAction");


            context.sendBroadcast(myIntent);
        }
    }
}

