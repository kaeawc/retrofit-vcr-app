package io.kaeawc.vcrapp;

import android.app.Application;

import io.kaeawc.vcrapp.data.local.Disk;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Disk.getInstance(getApplicationContext());
    }
}
