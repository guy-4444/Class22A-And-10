package com.guy.class22a_and_10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Activity_A extends AppCompatActivity {

    private TextView txt;
    private MaterialButton next;
    private MaterialButton start;
    private MaterialButton stop;
    private MaterialButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);
        next = findViewById(R.id.next);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        info = findViewById(R.id.info);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_A.this, Activity_B.class));
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService();
            }
        });

        next.setOnClickListener(clickListener);
        start.setOnClickListener(clickListener);
        stop.setOnClickListener(clickListener);
        info.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            infoData();
        }
    };

    private void infoData() {
        txt.setText(System.currentTimeMillis() + "\n" + checkServiceRunning(MyService.class));
    }

    private void startService() {
        startService(new Intent( this, MyService.class ) );
    }

    private void stopService() {
        stopService(new Intent( this, MyService.class ) );
    }

    /**
     * Check if the service is Running
     * @param serviceClass the class of the Service
     *
     * @return true if the service is running otherwise false
     */
    public boolean checkServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void notifyFileDownloaded() {
        txt.setText("File Downloaded");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(fileBroadcastReceiver, new IntentFilter(MyService.FILE_DOWNLOADED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(fileBroadcastReceiver);
    }

    private BroadcastReceiver fileBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            notifyFileDownloaded();
        }
    };

}