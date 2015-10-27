package io.kaeawc.vcrapp.data.remote;

import android.support.annotation.NonNull;

import io.kaeawc.vcrapp.data.models.IpAddress;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface IpifyApi {
    @GET(" ") Call<IpAddress> getIpAddress(@Query("format") @NonNull String format);
}
