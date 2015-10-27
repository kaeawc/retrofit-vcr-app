package io.kaeawc.vcrapp.ui;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import io.kaeawc.vcrapp.R;

public class MockPresenter extends MainPresenter {

    @Override
    @LayoutRes
    public int getLayout() {
        return R.layout.activity_main_mock;
    }

    @Override
    @IdRes
    public int getMockModeToggleId() {
        return R.id.mock_mode_toggle;
    }
}
