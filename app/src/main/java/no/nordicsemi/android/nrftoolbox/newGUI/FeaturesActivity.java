package no.nordicsemi.android.nrftoolbox.newGUI;

import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import no.nordicsemi.android.nrftoolbox.AppHelpFragment;
import no.nordicsemi.android.nrftoolbox.R;
import no.nordicsemi.android.nrftoolbox.adapter.AppAdapter;
import no.nordicsemi.android.nrftoolbox.hrs.HRSActivity;
import no.nordicsemi.android.nrftoolbox.hts.HTSService;
import no.nordicsemi.android.nrftoolbox.newGUI.NavigationHost;
import no.nordicsemi.android.nrftoolbox.newGUI.ProductGridFragment;
import no.nordicsemi.android.nrftoolbox.profile.BleProfileService;
import no.nordicsemi.android.nrftoolbox.profile.BleProfileServiceReadyActivity;
import no.nordicsemi.android.nrftoolbox.template.TemplateManager;
import no.nordicsemi.android.nrftoolbox.template.TemplateService;

import static no.nordicsemi.android.nrftoolbox.newGUI.sensorData.initSensorDataList;

public class FeaturesActivity extends BleProfileServiceReadyActivity<TemplateService.LocalBinder> implements NavigationHost  {
	private static final String NRF_CONNECT_CATEGORY = "no.nordicsemi.android.nrftoolbox.LAUNCHER";
	private static final String UTILS_CATEGORY = "no.nordicsemi.android.nrftoolbox.UTILS";
	private static final String NRF_CONNECT_PACKAGE = "no.nordicsemi.android.mcp";
	private static final String NRF_CONNECT_CLASS = NRF_CONNECT_PACKAGE + ".DeviceListActivity";
	private static final String NRF_CONNECT_MARKET_URI = "market://details?id=no.nordicsemi.android.mcp";

	// Extras that can be passed from NFC (see SplashscreenActivity)
	public static final String EXTRA_APP = "application/vnd.no.nordicsemi.type.app";
	public static final String EXTRA_ADDRESS = "application/vnd.no.nordicsemi.type.address";

	private ActionBarDrawerToggle mDrawerToggle;



    @Override //graphical initialization usually takes place here
    protected void onCreateView(final Bundle savedInstanceState) {
        setContentView(R.layout.activity_features);

        sensorDataList = initSensorDataList();

        /**DRAWER
         * */
        DrawerLayout mDrawerLayout;
        final DrawerLayout drawer = mDrawerLayout = findViewById(R.id.drawer_layout);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(final View drawerView, final float slideOffset) {
                // Disable the Hamburger icon animation
                super.onDrawerSlide(drawerView, 0);
            }
        };
        drawer.addDrawerListener(mDrawerToggle);

        /**
         * FRAGMENT
         */
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new ProductGridFragment())
                    .commit();
        }
    }

    /** Mandatory implementations
     * */
    @Override
    protected void setDefaultUI() {/* TODO clear your UI*/}
    @Override
    protected int getDefaultDeviceName() { return R.string.template_default_name; }
    @Override
    protected int getAboutTextId() {
        return R.string.template_about_text;
    }

//	@Override
//	public boolean onCreateOptionsMenu(final Menu menu) {
//		getMenuInflater().inflate(R.menu.help, menu);
//		return true;
//	}

	@Override
	protected void onPostCreate(final Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(@NonNull final Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
			case R.id.action_about:
				final AppHelpFragment fragment = AppHelpFragment.getInstance(R.string.about_text, true);
				fragment.show(getSupportFragmentManager(), null);
				break;
		}
		return true;
	}

// used to be called in onCreate which i had to remove. similar feature already exists in BleProfileServiceReadyActivity
//	private boolean ensureBLEExists() {
//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//			Toast.makeText(this, R.string.no_ble, Toast.LENGTH_LONG).show();
//			return false;
//		}
//		return true;
//	}


	/**
	 * Navigate to the given fragment.
	 *
	 * @param fragment       Fragment to navigate to.
	 * @param addToBackstack Whether or not the current fragment should be added to the backstack.
	 */
	@Override
	public void navigateTo(Fragment fragment, boolean addToBackstack) {
		FragmentTransaction transaction =
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container, fragment);

		if (addToBackstack) {
			transaction.addToBackStack(null);
		}

		transaction.commit();
	}


    /**
     * Service - Activity - Fragment Communication
     */

    //needed in featuresactivity
    @Override
    protected void onInitialize(final Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, makeIntentFilter());
    }
    //needed in featuresactivity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }


    @Override //needed in featuresactivity
    protected UUID getFilterUUID() {
        // TODO this method may return the UUID of the service that is required to be in the advertisement packet of a device in order to be listed on the Scanner dialog.
        // If null is returned no filtering is done.
        return  UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb"); // Heart Rate service
    }

    @Override //needed in featuresactivity
    protected Class<? extends BleProfileService> getServiceClass() {
        return TemplateService.class;
    }

    @Override //needed in featuresactivity
    protected void onServiceBound(final TemplateService.LocalBinder binder) {
        // not used
        float dbug = 1234.0f;
        //onTemperatureMeasurementReceived(dbug); //binder.getTemperature());
        //TemplateService service = getService();
        //binder is already the result of getService()
      //  TemplateService.LocalBinder binder = (LocalBinder) service;
//        TemplateService mService = (TemplateService) binder;
//        mService.setListener(FeaturesActivity.this);


    }

    @Override
    protected void onServiceUnbound() {
        // not used
    }

    @Override //needed in featuresactivity
    public void onDeviceDisconnected(final BluetoothDevice device) {
        super.onDeviceDisconnected(device);
        //mBatteryLevelView.setText(R.string.not_available);
    }

    //needed in featuresactivity
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            final BluetoothDevice device = intent.getParcelableExtra(TemplateService.EXTRA_DEVICE);

            if (TemplateService.BROADCAST_TEMPLATE_MEASUREMENT.equals(action)) {
                final int value = intent.getIntExtra(TemplateService.EXTRA_DATA, 0);
                onHeartRateReceived(Integer.toString(value));
            }
             else if (TemplateService.BROADCAST_BATTERY_LEVEL.equals(action)) {
                final int batteryLevel = intent.getIntExtra(TemplateService.EXTRA_BATTERY_LEVEL, 0);
                onBatteryReceived(Integer.toString(batteryLevel));
            }

            //JF
            else if (TemplateService.BROADCAST_HTS_MEASUREMENT.equals(action)) {
                final float value = intent.getFloatExtra(HTSService.EXTRA_TEMPERATURE, 0.0f);
                // Update GUI
                onTemperatureReceived(Float.toString(value));
                Log.w("jonas", "received a temperature measurement: " + value);
               // mViewModel.scoreTeamA = (int) value;
                Log.w("jonas", "view model has been updated: " + mViewModel.scoreTeamA);


            }
            mViewModel.getCurrentValue().setValue(sensorDataList);
        }
    };
    //needed in featuresactivity
    private static IntentFilter makeIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TemplateService.BROADCAST_TEMPLATE_MEASUREMENT);
        intentFilter.addAction(TemplateService.BROADCAST_BATTERY_LEVEL);
        //JF
        intentFilter.addAction(TemplateService.BROADCAST_HTS_MEASUREMENT);
        intentFilter.addAction(TemplateService.BROADCAST_BATTERY_LEVEL);
        return intentFilter;
    }

    public List<sensorData> sensorDataList = initSensorDataList();


    private void onHeartRateReceived(String newReading){
        sensorDataList.get(0).sensorReading = newReading;
    }
    private void onBatteryReceived(String newReading){
        sensorDataList.get(1).sensorReading = newReading;
    }
    private void onTemperatureReceived(String newReading){
        sensorDataList.get(2).sensorReading = newReading;
    }
}