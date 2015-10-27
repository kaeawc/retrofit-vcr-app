package io.kaeawc.vcrapp.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.ResponseBody;

import de.greenrobot.event.EventBus;
import io.kaeawc.vcrapp.data.models.IpAddress;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

public class IpifyApiManager {

    public static final String BASE_URL = "https://api.ipify.org/";

    public static final String ARG_JSON = "json";

    protected IpifyApi mApi;

    public IpifyApiManager() {
        mApi = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(IpifyHttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .callbackExecutor(CallbackExecutor.EXECUTOR)
                .validateEagerly()
                .build()
                .create(IpifyApi.class);
    }

    public IpifyApiManager(IpifyApi api) {
        mApi = api;
    }

    /**
     * Need these converters for bad chat_enabled API data.
     */
    public Gson gsonBuilder = new GsonBuilder().create();

    public void getIpAddress() {
        Call<IpAddress> call = mApi.getIpAddress(ARG_JSON);
        call.enqueue(new IpAddressResponse());
    }

    private static class IpAddressResponse implements Callback<IpAddress> {

        @Override
        public void onResponse(Response<IpAddress> response, Retrofit retrofit) {
            IpAddress ipAddress = response.body();
            ResponseBody errorBody = response.errorBody();

            if (ipAddress != null) {
                EventBus.getDefault().post(ipAddress);
            } else {
                EventBus.getDefault().post(errorBody);
                Timber.e("Bad request?");
                Timber.e(errorBody.toString());
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Timber.e(t, "Retrofit error");
            EventBus.getDefault().post(t);
        }
    }
}
