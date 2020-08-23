package com.example.loginpageofrastreo;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.TimeZone.LONG;

public class BackgroundServices extends Service {
    private ArrayList<Details> arrayList = new ArrayList<Details>();
    //private ArrayList<D>  arrayList1= new ArrayList<String>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long TimeInforground = 500;
    private String PackageName="";
    private TextView Facebook_view;

    public BackgroundServices() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        sharedPreferences=getSharedPreferences("My Application786",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Timer detectAppTimer=new Timer();
        TimerTask detectApp=new TimerTask() {

            @Override
            public void run() {
                sharedPreferences=getSharedPreferences("My Application786",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                UsageStatsManager usageStatsManager=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
                long endtime=System.currentTimeMillis();
                long begintime=endtime-(1000000);
                List<UsageStats> usageStats=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                        begintime,endtime);
                if(usageStats!=null)
                {  int counter=0;
                    for(UsageStats usageStats1:usageStats)
                    {  counter=counter+1;
                        TimeInforground=usageStats1.getTotalTimeInForeground();

                        PackageName=usageStats1.getPackageName();
                        // Log.e("Name", PackageName);

                        long  minutes =(long )((TimeInforground / (1000*60)) % 60);
                        String minutes1 = Long.toString(minutes);
                        if(minutes>=0 && minutes<=9)
                        {
                            minutes1 = "0" + minutes1;
                        }

                        long seconds = (int) (TimeInforground / 1000) % 60 ;
                        String seconds1 = Long.toString(seconds);
                        if(seconds>=0 && seconds<=9)
                        {
                            seconds1 = "0" + seconds1;
                        }
                        long   hours   = (int) ((TimeInforground / (1000*60*60)) % 24);
                        String hours1 = Long.toString(hours);
                        if(hours>=0 && hours<=9)
                        {
                            hours1 = "0" + hours1;
                        }
                       // Log.i("BAC", "PackageName is"+PackageName +"Time is: "+hours+"h"+":"+minutes+"m"+seconds+"s");
                        //String packageName = usageStats1.getPackageName();
                        String val=hours1+":"+minutes1+":"+seconds1;
                       if(PackageName.equals("com.google.android.youtube")||PackageName.equals("com.whatsapp")) {
                           Log.i("BAC", "PackageName is"+PackageName +"Time is: "+hours+"h"+":"+minutes+"m"+seconds+"s"+"counter is"+counter);
                           arrayList.add(new Details(PackageName, val));
                           //  Log.e("arraylistbackground",arrayList+"");
                       }

                        //           System.out.println("hello bhati");

                    }
                    Log.i("BAC2","IAM OUT OF IT");
                    //System.out.println("hello pankaj");
                    Intent intent1=new Intent(BackgroundServices.this,MyReciever.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",arrayList);
                    intent1.putExtras(bundle);
                    intent1.setAction("com.example.loginpageofrastreo.MYACTION");
                    //intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // sendBroadcast(intent1);
                    Log.i("BAC3","IAM ABOUT TO SEND THE SERVICES");
                     LocalBroadcastManager.getInstance(BackgroundServices.this).sendBroadcast(intent1);
                     Log.i("BAC4","I HAVE SEND IT");
                    //mainActivity.getInstance().setArrayList2(arrayList2);
                }
            }
        };

        detectAppTimer.scheduleAtFixedRate(detectApp,1000000,1000000000);
        Log.i("BAC5","IAM OUT OF TIMEDETECT APP");
        return super.onStartCommand(intent, flags, startId);
}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
