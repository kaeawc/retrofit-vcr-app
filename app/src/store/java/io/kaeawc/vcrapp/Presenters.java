package io.kaeawc.vcrapp;

import io.kaeawc.vcrapp.ui.MainPresenter;

public class Presenters {

    public static MainPresenter getMain() {
        return new MainPresenter();
    }

    public static void setMockMode(boolean mockMode) {
        MainPresenter.setInstance(new MainPresenter());
    }
}
