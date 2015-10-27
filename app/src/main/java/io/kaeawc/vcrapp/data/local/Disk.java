package io.kaeawc.vcrapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class Disk {

    private WeakReference<Context> mContext;
    private static volatile Disk sInstance;

    public Disk(@NonNull Context context) {
        mContext = new WeakReference<>(context);
    }

    @NonNull
    public static Disk getInstance() {
        return sInstance;
    }

    @NonNull
    public static Disk getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (Disk.class) {
                if (sInstance == null) {
                    sInstance = new Disk(context);
                }
            }
        }

        return sInstance;
    }

    public static void setInstance(Disk instance) {
        sInstance = instance;
    }

    @Nullable
    public Context getContext() {
        return mContext.get();
    }

    public boolean write(String key, String value) {
        Context context = getContext();

        if (context == null) {
            return false;
        }

        SharedPreferences preferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);

        if (preferences == null) {
            Timber.w("SharedPreferences was null. This is crazy");
            return false;
        }

        return preferences.edit().putString(key, value).commit();
    }

    @Nullable
    public String read(String key) {
        Context context = getContext();

        if (context == null) {
            return null;
        }

        SharedPreferences preferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);

        if (preferences == null) {
            Timber.w("SharedPreferences was null. This is crazy");
            return null;
        }

        return preferences.getString(key, null);
    }
}
