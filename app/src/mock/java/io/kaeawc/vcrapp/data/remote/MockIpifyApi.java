package io.kaeawc.vcrapp.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.kaeawc.vcrapp.data.Ipify;
import io.kaeawc.vcrapp.data.local.Disk;
import io.kaeawc.vcrapp.data.models.IpAddress;
import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.Query;
import retrofit.mock.Calls;
import timber.log.Timber;

public class MockIpifyApi implements IpifyApi {

    @NonNull
    String mScenario;

    @NonNull
    Retrofit mRetrofit;

    public MockIpifyApi(@NonNull Retrofit retrofit, @NonNull String scenario) {
        mRetrofit = retrofit;
        mScenario = scenario;
    }

    @Override
    public Call<IpAddress> getIpAddress(@Query("format") @NonNull String format) {
        Timber.d("getIpAddress");
        String key = String.format("GET:%s?format=%s:%s", IpifyApiManager.BASE_URL, format, mScenario);
        IpAddress ipAddress = getCachedScenario(IpAddress.class, key);

        if (ipAddress == null) {
            Timber.d("Didn't find a cached IP address on disk with key %s", key);
            return null;
        }

        Timber.d("Retrieved cached response from disk for key:[%s]", key);
        return Calls.response(ipAddress, mRetrofit);
    }

    public <T> T getCachedScenario(Class<T> clazz, String key) {
        try {
            String value = Disk.getInstance().read(key);
            Gson gson = new Gson();
            return gson.fromJson(value, clazz);
        } catch (JsonSyntaxException jsonSyntax) {
            Timber.w(jsonSyntax, "Could not parse cached scenario as JSON");
            return null;
        } catch (Exception exception) {
            Timber.e(exception, "Unknown error");
            return null;
        }
    }
}
