package io.kaeawc.vcrapp;

import android.support.annotation.Nullable;

public class BuildConfigManager {

    public static final String FLAVOR_MOCK = "mock";

    @Nullable
    private static volatile BuildConfigManager sInstance;

    public BuildConfigManager() {
    }

    public static BuildConfigManager getInstance() {
        if (sInstance == null) {
            synchronized (BuildConfigManager.class) {
                if (sInstance == null) {
                    sInstance = new BuildConfigManager();
                }
            }
        }

        return sInstance;
    }

    public static void setInstance(BuildConfigManager instance) {
        sInstance = instance;
    }

    public boolean isMockMode() {
        return BuildConfig.FLAVOR.equals(FLAVOR_MOCK);
    }

}
