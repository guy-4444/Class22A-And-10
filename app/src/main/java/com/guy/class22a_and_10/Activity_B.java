package com.guy.class22a_and_10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Activity_B extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        txt = findViewById(R.id.txt);

        txt.setText("No File");
    }

    private void notifyFileDownloaded() {
        txt.setText(System.currentTimeMillis() + "\nFile Downloaded");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(fileBroadcastReceiver, new IntentFilter(MyService.FILE_DOWNLOADED_ACTION));
        registerReceiver(fileBroadcastReceiver, new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED"));
        registerReceiver(fileBroadcastReceiver, new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(fileBroadcastReceiver);
    }

    private BroadcastReceiver fileBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                txt.setText(System.currentTimeMillis() + "\nPOWER_CONNECTED");
            } else if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                txt.setText(System.currentTimeMillis() + "\nPOWER_DISCONNECTED");
            }
            //notifyFileDownloaded();
        }
    };

}