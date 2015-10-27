package io.kaeawc.vcrapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import de.greenrobot.event.EventBus;
import io.kaeawc.vcrapp.BuildConfigManager;
import io.kaeawc.vcrapp.data.models.IpAddress;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    TextView mHelloText;
    Button mIpAddressButton;
    ToggleButton mMockModeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainPresenter presenter = MainPresenter.getInstance();
        setContentView(presenter.getLayout());
        Timber.d("Setting up layout of MainActivity");

        mHelloText = (TextView) findViewById(presenter.getHelloWorldId());
        mIpAddressButton = (Button) findViewById(presenter.getIpAddressButtonId());
        mIpAddressButton.setOnClickListener(new GetIpClickListener());

        if (BuildConfigManager.getInstance().isMockMode()) {
            mMockModeToggle = (ToggleButton) findViewById(presenter.getMockModeToggleId());
            mMockModeToggle.setOnCheckedChangeListener(new MockModeToggleListener());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(IpAddress ipAddress) {
        MainPresenter presenter = MainPresenter.getInstance();
        mHelloText.setText(presenter.getIpAddressText(ipAddress));
        Toast.makeText(this, presenter.getUpdatedIpAddressText(), Toast.LENGTH_SHORT).show();
    }

    private static class GetIpClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MainPresenter.getInstance().onGetIpAddressClicked();
        }
    }

    private static class MockModeToggleListener implements ToggleButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            MainPresenter.getInstance().onMockModeToggled(isChecked);
        }
    }
}
