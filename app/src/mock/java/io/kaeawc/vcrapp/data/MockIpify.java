package io.kaeawc.vcrapp.data;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.kaeawc.vcrapp.data.remote.IpifyApi;
import io.kaeawc.vcrapp.data.remote.IpifyApiManager;
import io.kaeawc.vcrapp.data.remote.MockIpifyApi;
import retrofit.Retrofit;
import retrofit.mock.CallBehaviorAdapter;
import retrofit.mock.MockRetrofit;
import retrofit.mock.NetworkBehavior;
import timber.log.Timber;

public class MockIpify extends Ipify {

    private String mScenario;

    public MockIpify(@NonNull String scenario) {
        Timber.d("MockIpify");
        mScenario = scenario;
    }

    @Override
    @NonNull
    public IpifyApiManager getApiManager() {

        Timber.d("getApiManager");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpifyApiManager.BASE_URL)
                .build();

        // Create the Behavior object which manages the fake behavior and the background executor.
        NetworkBehavior behavior = NetworkBehavior.create();

        ExecutorService bg = Executors.newSingleThreadExecutor();

        // Create the mock implementation and use MockRetrofit to apply the behavior to it.
        MockRetrofit mockRetrofit = new MockRetrofit(behavior, new CallBehaviorAdapter(retrofit, bg));
        MockIpifyApi mockIpify = new MockIpifyApi(retrofit, mScenario);
        IpifyApi api = mockRetrofit.create(IpifyApi.class, mockIpify);
        return new IpifyApiManager(api);
    }
}
