package com.example.myapplication786;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import android.widget.TextView;
import java.util.ArrayList;


import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication786.MainActivity.FACEBOOK_COUNTER;
import static com.example.myapplication786.MainActivity.WHATSAPP_COUNTER;
import static com.example.myapplication786.MainActivity.mainActivity;

public class BackgroundService extends Service {
    private ArrayList<Details> arrayList = new ArrayList<Details>();
    //private ArrayList<D>  arrayList1= new ArrayList<String>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long TimeInforground = 500;
    private String PackageName="";
    private TextView Facebook_view;

    public BackgroundService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        sharedPreferences=getSharedPreferences("My Application786",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        TimerTask detectApp=new TimerTask() {

            @Override
            public void run() {
                sharedPreferences=getSharedPreferences("My Application786",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                UsageStatsManager usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
                long endtime=System.currentTimeMillis();
                long begintime=endtime-(1000000);
                List<UsageStats>usageStats=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                        begintime,endtime);
                if(usageStats!=null)
                {
                    for(UsageStats usageStats1:usageStats)
                    {
                        TimeInforground=usageStats1.getTotalTimeInForeground();

                        PackageName=usageStats1.getPackageName();
                       // Log.e("Name", PackageName);

                       int minutes = (int) ((TimeInforground / (1000*60)) % 60);
                       String minutes1 = Integer.toString(minutes);
                       if(minutes>=0 && minutes<=9)
                       {
                           minutes1 = "0" + minutes1;
                       }

                        int seconds = (int) (TimeInforground / 1000) % 60 ;
                        String seconds1 = Integer.toString(seconds);
                        if(seconds>=0 && seconds<=9)
                        {
                            seconds1 = "0" + seconds1;
                        }
                       int  hours   = (int) ((TimeInforground / (1000*60*60)) % 24);
                        String hours1 = Integer.toString(hours);
                        if(hours>=0 && hours<=9)
                        {
                            hours1 = "0" + hours1;
                        }
                        Log.i("BAC", "PackageName is"+PackageName +"Time is: "+hours+"h"+":"+minutes+"m"+seconds+"s");
                       String packageName = usageStats1.getPackageName();
                        String val="Time is: "+hours1+"h"+":"+minutes1+"m"+seconds1+"s";

                        arrayList.add(new Details(packageName,val));
                        Log.e("arraylistbackground",arrayList+"");

             //           System.out.println("hello bhati");

                    }
                    //System.out.println("hello pankaj");
                    Intent intent1=new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",arrayList);
                    intent1.putExtras(bundle);
                    intent1.setAction("MyAction");
                    LocalBroadcastManager.getInstance(BackgroundService.this).sendBroadcast(intent1);
                    //mainActivity.getInstance().setArrayList2(arrayList2);
                }
            }
        };
        Timer detectAppTimer=new Timer();
        detectAppTimer.scheduleAtFixedRate(detectApp,0,5000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
