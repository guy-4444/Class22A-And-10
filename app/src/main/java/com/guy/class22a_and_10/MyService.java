package com.guy.class22a_and_10;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {

    public static final String FILE_DOWNLOADED_ACTION = "FILE_DOWNLOADED_ACTION";
    boolean isRunning = false;
    private MediaPlayer player;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {
            isRunning = true;
            player = MediaPlayer.create( this, Settings.System.DEFAULT_RINGTONE_URI );
            player.setLooping(true);
            player.start();
        }
        doSomething();
        return START_STICKY;
    }

    Runnable runnable;
    Handler handler;

    private void doSomething() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Log.d("pttt", "" + i);
                }
                stopSomething();
            }
        };
        handler.postDelayed(runnable, 15000);
    }

    private void stopSomething() {
        sendBroadcast(new Intent(FILE_DOWNLOADED_ACTION));
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
