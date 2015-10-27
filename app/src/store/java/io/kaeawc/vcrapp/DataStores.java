package io.kaeawc.vcrapp;

import io.kaeawc.vcrapp.data.Ipify;

public class DataStores {

    public static Ipify getIpify() {
        return Ipify.getInstance();
    }

    public static void setMockMode(boolean mockMode) {
        Ipify.setInstance(new Ipify());
    }
}
