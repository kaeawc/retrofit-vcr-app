package io.kaeawc.vcrapp;

import io.kaeawc.vcrapp.ui.MainPresenter;
import io.kaeawc.vcrapp.ui.MockPresenter;
import timber.log.Timber;

public class Presenters {

    public static void setMockMode(boolean mockMode) {

        if (mockMode) {
            Timber.d("Enabling MockPresenter");
            MainPresenter.setInstance(new MockPresenter());
        } else {
            Timber.d("Enabling MainPresenter");
            MainPresenter.setInstance(new MainPresenter());
        }
    }
}
