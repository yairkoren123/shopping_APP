package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Info_Activity extends AppCompatActivity {

    private String username;
    private String level_text ;

    private TextView username_text;
    private TextView wifi_con;
    Context context = Info_Activity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        wifi_con = findViewById(R.id.text_info_wifi);
        username_text = findViewById(R.id.info_username);


        Intent intent = new Intent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        username_text.setText(username);



        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int numberOfLevels = 5;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

        switch (level) {
            case 0:
                level_text= "None";
                Log.d("LEVEL", "None");
                break;

            case 1:
                Log.d("LEVEL", "None");
                level_text = "None";
                break;

            case 2:
                Log.d("LEVEL", "Poor");
                level_text = "Poor";

                break;

            case 3:
                Log.d("LEVEL", "Moderate");
                level_text = "Moderate";

                break;

            case 4:
                Log.d("LEVEL", "Good");
                level_text = "Good";

                break;

            case 5:
                Log.d("LEVEL", "Excellent");
                level_text = "Excellent";

                break;

        }
        wifi_con.setText("WIFI : " + level_text);
    }
}