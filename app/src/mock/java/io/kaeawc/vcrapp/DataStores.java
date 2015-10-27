package io.kaeawc.vcrapp;

import io.kaeawc.vcrapp.data.Ipify;
import io.kaeawc.vcrapp.data.MockIpify;
import timber.log.Timber;

public class DataStores {

    public static void setMockMode(boolean mockMode) {

        if (mockMode) {
            Timber.d("Enabling MockIpify with fake IP address");
            Ipify.setInstance(new MockIpify("200"));
        } else {
            Timber.d("Enabling MainIpify");
            Ipify.setInstance(new Ipify());
        }
    }
}
