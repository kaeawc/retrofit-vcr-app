package io.kaeawc.vcrapp.ui;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.kaeawc.vcrapp.DataStores;
import io.kaeawc.vcrapp.Presenters;
import io.kaeawc.vcrapp.R;
import io.kaeawc.vcrapp.data.Ipify;
import io.kaeawc.vcrapp.data.models.IpAddress;
import timber.log.Timber;

public class MainPresenter {

    @Nullable
    private static volatile MainPresenter sInstance;

    public MainPresenter() {
    }

    public static MainPresenter getInstance() {
        if (sInstance == null) {
            synchronized (MainPresenter.class) {
                if (sInstance == null) {
                    Timber.d("Creating new instance of MainPresenter");
                    sInstance = new MainPresenter();
                }
            }
        }

        return sInstance;
    }

    public static void setInstance(MainPresenter instance) {
        Timber.d("setInstance");
        sInstance = instance;
    }

    @LayoutRes
    public int getLayout() {
        return R.layout.activity_main;
    }

    @IdRes
    public int getHelloWorldId() {
        return R.id.hello_world_text;
    }

    @IdRes
    public int getIpAddressButtonId() {
        return R.id.get_ip_address_button;
    }

    @IdRes
    public int getMockModeToggleId() {
        return R.id.mock_mode_toggle;
    }

    public String getUpdatedIpAddressText() {
        return "Updated IP Address";
    }

    public String getIpAddressText(@NonNull IpAddress ipAddress) {
        return String.format("IP Address: %s", ipAddress.getIp());
    }

    public void onGetIpAddressClicked() {
        Timber.d("onGetIpAddressClicked");
        Ipify.getInstance().getIpAddress();
    }

    public void onMockModeToggled(boolean mockMode) {
        Timber.d("onMockModeToggled");
        DataStores.setMockMode(mockMode);
        Presenters.setMockMode(mockMode);
    }
}
