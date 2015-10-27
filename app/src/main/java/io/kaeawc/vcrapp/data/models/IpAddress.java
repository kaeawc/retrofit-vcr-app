package io.kaeawc.vcrapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IpAddress {

    @Expose
    @SerializedName("ip")
    private String mIp;

    public void setIp(String value) {
        mIp = value;
    }

    public String getIp() {
        return mIp;
    }
}
