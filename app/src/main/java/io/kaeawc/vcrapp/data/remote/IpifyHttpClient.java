package io.kaeawc.vcrapp.data.remote;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import io.kaeawc.vcrapp.BuildConfigManager;
import io.kaeawc.vcrapp.data.local.Disk;
import okio.Buffer;
import timber.log.Timber;

public class IpifyHttpClient extends OkHttpClient {

    public static final String HEADER_TOKEN = "X-Token";

    private static volatile IpifyHttpClient sInstance;

    public IpifyHttpClient() {
        super();
        interceptors().add(new LogInterceptor());
    }

    public static IpifyHttpClient getInstance() {
        if (sInstance == null) {
            synchronized (IpifyHttpClient.class) {
                if (sInstance == null) {
                    sInstance = new IpifyHttpClient();
                }
            }
        }

        return sInstance;
    }

    public static void setInstance(IpifyHttpClient instance) {
        sInstance = instance;
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) {
            Request request = chain.request();

            // Log request
            long t1 = System.nanoTime();

            Request.Builder builder = request.newBuilder();
            builder.addHeader(HEADER_TOKEN, "asdf");
            request = builder.build();

            // Log request
            logRequest(request, chain);

            // Proceed with request
            com.squareup.okhttp.Response response = null;
            String bodyString = null;
            try {
                response = chain.proceed(request);
                long t2 = System.nanoTime();
                bodyString = response.body().string();

                // Log response
                double duration = (t2 - t1) / 1e6d;
                logResponse(response, bodyString, duration);
            } catch (IOException ioExpception) {
                Timber.e(ioExpception, "Failed to get or parse response");
            }

            if (response == null || StringUtils.isBlank(bodyString)) {
                return null;
            }

            if (BuildConfigManager.getInstance().isMockMode()) {
                saveResponse(request, response, bodyString);
            }

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }

        public void logResponse(
                @NonNull Response response,
                @NonNull String bodyString,
                double duration) throws IOException {

            String responseLog = String.format(
                    "Received response for %s in %.1fms\n%s",
                    response.request().url(),
                    duration,
                    response.headers());

            Timber.v("HTTP <-- \n%s\n%s", responseLog, bodyString);
        }

        public void logRequest(@NonNull Request request, @NonNull Chain chain) {

            String requestLog = String.format("Sending request %s on \n%s", request.url(), request.headers());

            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = String.format("\n%s\n%s", requestLog, bodyToString(request));
            }

            Timber.v("HTTP --> \n%s", requestLog);
        }

        @NonNull
        public String bodyToString(final @NonNull Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return StringUtils.EMPTY;
            }
        }

        public void saveResponse(
                @NonNull Request request,
                @NonNull Response response,
                @NonNull String body) {

            String key = String.format(
                    "%s:%s:%s",
                    request.method(),
                    request.urlString(),
                    response.code());

            Timber.d("Saving Response key:[%s] to disk: %s", key, body);
            Disk.getInstance().write(key, body);
        }
    }
}
