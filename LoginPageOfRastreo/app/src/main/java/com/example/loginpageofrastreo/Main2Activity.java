package com.example.loginpageofrastreo;
//package com.example.myapplication786;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    static Main2Activity mainActivity;
    public ArrayList<Details> arrayList = new ArrayList<Details>();
    public ArrayList<String>  arrayList1= new ArrayList<String>();
    public ArrayList<String>  arrayList2= new ArrayList<String>();
    private MyReciever myReciever = new MyReciever();
    Myadapter myadapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String FACEBOOK_COUNTER = "Facebook_Counter";
    public static String WHATSAPP_COUNTER = "Whatsapp_Counter";


    private TextView Whatsapp_view;
    String Facebook_val;
    String Whatsapp_val;
    int flag = 0;
    // MainActivity app;
    public static Main2Activity getInstance() {
        return mainActivity;
    }


    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public static final String ACTION_CUSTOM = "com.example.loginpageofrastreo.MyAction2";
        @Override
        public void onReceive(Context context, Intent received) {
            if (ACTION_CUSTOM.equals(received.getAction())) {
                Bundle mbundle = received.getExtras();
                if (mbundle != null) {
                    arrayList = (ArrayList<Details>) mbundle.getSerializable("list");

                    for (int i = 0; i < arrayList.size(); i++) {
                        Details detail = arrayList.get(i);
                        arrayList1.add(detail.name);
                        arrayList2.add(detail.val);

                    }
                    if (flag == 1) {
                        Log.i("array list2", arrayList2 + "");

                        myadapter.getContents(Main2Activity.this, arrayList1, arrayList2);
                        listView.setAdapter(myadapter);


                        Log.i("Array list1", arrayList1 + "");

                        Log.e("Array list", arrayList + "");
                    } else {
                        flag = 1;
                        myadapter = new Myadapter(Main2Activity.this, arrayList1, arrayList2);
                        listView.setAdapter(myadapter);
                    }

                }
            }
        }
    };
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.



        //this will bind your MainActivity.class file with activity_main.

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i=new Intent(MainActivity.this,
//                        Splash_Screen.class);
//                //Intent is used to switch from one activity to another.
//
//                startActivity(i);
//                //invoke the SecondActivity.
//                //the current activity will get finished.
//            }
//        }, SPLASH_SCREEN_TIME_OUT);

        mainActivity = this;
       // registerReceiver(broadcastReceiver, new IntentFilter("com.example.loginpageofrastreo.MyAction"));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("com.example.loginpageofrastreo.MYACTION"));

        Main2Activity app = new Main2Activity();
        listView = findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences("My Application786", MODE_PRIVATE);
        if (!checkUsageStatsAllowedOrNot()) {
            Intent usageAccessIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            usageAccessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(usageAccessIntent);

            if (checkUsageStatsAllowedOrNot()) {
                startService(new Intent(Main2Activity.this, BackgroundServices.class));
            } else {
                Toast.makeText(getApplicationContext(), "Please give us acess", Toast.LENGTH_SHORT).show();
            }
        } else {
            startService(new Intent(Main2Activity.this, BackgroundServices.class));

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Details details = arrayList.get(i);
                Intent intent = new Intent(getApplicationContext(),ApkInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("appPackage",details.name);
                bundle.putString("appPackageval",details.val);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


//        Iterator itr=arrayList.iterator();
//        Details dt=(Details)itr();

    }
    public boolean checkUsageStatsAllowedOrNot() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error usage stats are not getting any list", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    class Myadapter extends ArrayAdapter<String>
    {
        Context context;
        private ArrayList<String> rarrayList2 = new ArrayList<String>();
        private ArrayList<String>  rarrayList1= new ArrayList<String>();
        //private ArrayList<String> rarrayList3 = new ArrayList<String >();

        Myadapter(Context c,ArrayList<String> array1,ArrayList<String> array2)
        {

            super(c,R.layout.custom,R.id.text1,array1);
            notifyDataSetChanged();
            this.context=c;
            this.rarrayList1=array1;
            this.rarrayList2=array2;
//              this.rarrayList3=array3;

        }
        void getContents(Context c,ArrayList<String> array1,ArrayList<String> array2)
        {
            this.notifyDataSetChanged();
            this.context=c;
            this.rarrayList1=array1;
            this.rarrayList2=array2;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.custom,parent,false);
            ImageView image = row.findViewById(R.id.image);
            TextView text1=row.findViewById(R.id.text1);
            TextView text2=row.findViewById(R.id.text2);
            // TextView text3=row.findViewById(R.id.text3)
            try
            {
                Drawable icon = getPackageManager().getApplicationIcon(arrayList1.get(position));
                image.setImageDrawable(icon);
            }
            catch (PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }
            final String packageName = arrayList1.get(position);
            PackageManager packageManager= getApplicationContext().getPackageManager();
            String appName = null;
            try {
                appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            text1.setText(appName);
            text2.setText(arrayList2.get(position));
            //text3.setText(arrayList3.get(position));

            return row;
        }
    }


}
