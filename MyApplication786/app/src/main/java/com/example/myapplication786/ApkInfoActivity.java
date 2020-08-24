package com.example.myapplication786;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ApkInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_info);
        Bundle bundle = getIntent().getExtras();
        String package_name = bundle.getString("appPackage");
        String package_val = bundle.getString("appPackageval");
        ImageView image = (ImageView)findViewById(R.id.image);
        try
        {
            Drawable icon = getPackageManager().getApplicationIcon(package_name);
            image.setImageDrawable(icon);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        TextView name_of_app  = (TextView)findViewById(R.id.name_of_app);
        TextView time_of_app  = (TextView)findViewById(R.id.time_of_app);
        PackageManager packageManager= getApplicationContext().getPackageManager();
        String appName = null;
        try {
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(package_name, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        name_of_app.setText(appName);
        time_of_app.setText(package_val);
        if(package_val.compareTo("Time is: 00h:05m00s") >= 0)
        {
            Toast.makeText(getApplicationContext(),"You have passed the threshold" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Great work ,You have spent optimal time here" ,Toast.LENGTH_LONG).show();
        }
    }
}
