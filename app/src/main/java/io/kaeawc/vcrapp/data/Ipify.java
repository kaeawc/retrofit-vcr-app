package io.kaeawc.vcrapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.kaeawc.vcrapp.data.remote.IpifyApiManager;

public class Ipify {

    // initialize Executor
    @Nullable
    private static volatile Ipify sInstance;

    @NonNull
    private IpifyApiManager mApiManager;

    public Ipify() {
        mApiManager = new IpifyApiManager();
    }

    public Ipify(IpifyApiManager apiManager) {
        mApiManager = apiManager;
    }

    public static Ipify getInstance() {
        if (sInstance == null) {
            synchronized (Ipify.class) {
                if (sInstance == null) {
                    sInstance = new Ipify();
                }
            }
        }

        return sInstance;
    }

    public static void setInstance(Ipify instance) {
        sInstance = instance;
    }

    @NonNull
    public IpifyApiManager getApiManager() {
        return mApiManager;
    }

    public void getIpAddress() {
        getApiManager().getIpAddress();
    }
}
