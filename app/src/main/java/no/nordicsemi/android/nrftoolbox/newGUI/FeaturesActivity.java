package no.nordicsemi.android.nrftoolbox.newGUI;

import android.Manifest;
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
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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

public class FeaturesActivity extends BleProfileServiceReadyActivity<TemplateService.LocalBinder> implements NavigationHost, NavigationView.OnNavigationItemSelectedListener  {

	// Extras that can be passed from NFC (see SplashscreenActivity)
	public static final String EXTRA_APP = "application/vnd.no.nordicsemi.type.app";
	public static final String EXTRA_ADDRESS = "application/vnd.no.nordicsemi.type.address";

	private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView nv;

    @Override //graphical initialization usually takes place here
    protected void onCreateView(final Bundle savedInstanceState) {
        setContentView(R.layout.activity_features);



        /**DRAWER
         * */
        DrawerLayout mDrawerLayout;
        final DrawerLayout drawer = mDrawerLayout = findViewById(R.id.drawer_layout);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

//        Animation t = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
//        t.setDuration(android.R.integer.config_shortAnimTime);

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(final View drawerView, final float slideOffset) {
                // Disable the Hamburger icon animation
                super.onDrawerSlide(drawerView, 0);
            }
            @Override
            public void onDrawerClosed(View DrawerView){
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }

            }
        };

        drawer.addDrawerListener(mDrawerToggle);


        nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);


        /**
         * FRAGMENT
         */
        if (savedInstanceState == null) {
            navigateTo(new ProductGridFragment(), false);
        }

        configureToolbar();
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_action_add);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
     public boolean onNavigationItemSelected(MenuItem item) {
         displaySelectedScreen(item.getItemId());
         return true;
     }

    Fragment fragment = null;
    private void displaySelectedScreen(int itemId) {

            //initializing the fragment object which is selected
            switch (itemId) {
                case R.id.dashboard_page:
                    fragment = new ProductGridFragment();
                    getSupportActionBar().setTitle(getResources().getString(R.string.drawer_mainpage_title));
                    break;
                case R.id.history_page:
                    getSupportActionBar().setTitle(getResources().getString(R.string.drawer_history_title));
                    break;
                case R.id.settings_page:
                    fragment = new SettingsFragment();
                    getSupportActionBar().setTitle(getResources().getString(R.string.drawer_settings_title));
                    break;
                case R.id.help_feedback_page:
                    getSupportActionBar().setTitle(getResources().getString(R.string.drawer_help_title));
                    break;
                case R.id.about_page:
                    fragment = new AboutFragment();
                    getSupportActionBar().setTitle(getResources().getString(R.string.drawer_about_title));
                    break;
                default: break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

//    public void onDrawerClosed(View drawerView) {
//        /**
//         * Change fragment for all items excluding nav_five
//         * as it opens up an Activity
//         */
//
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//        }
//
//    }




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
            case R.id.bluetooth_connect:
                onConnectClicked();
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

    @Override
    public void onDeviceDisconnected(final BluetoothDevice device) {
        super.onDeviceDisconnected(device);
        //mBatteryLevelView.setText(R.string.not_available);
        String disconnected = "-";
        onHeartRateReceived(disconnected);
        onBatteryReceived(disconnected);
        onBloodOxReceived(disconnected);
        onStepCountReceived(disconnected);
        onTemperatureReceived(disconnected);

        mViewModel.getCurrentValue().setValue(sensorDataList);
    }


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
                final float value = intent.getFloatExtra(TemplateService.EXTRA_TEMPERATURE, 0.0f);
                int val = Math.round(value);
                onTemperatureReceived(Integer.toString(val));
            }

            //JF2
            else if (TemplateService.BROADCAST_STEPCOUNT_MEASUREMENT.equals(action)) {
                final int value = intent.getIntExtra(TemplateService.EXTRA_STEPCOUNT, 0);
                Log.w("P24", "Received Step count integer: " + value);
                onStepCountReceived(Integer.toString(value));
            }

            //JF3
            else if (TemplateService.BROADCAST_BLOODOX_MEASUREMENT.equals(action)) {
                final int value = intent.getIntExtra(TemplateService.EXTRA_BLOODOX, 0);
                Log.w("P24", "Received blood ox integer: " + value);
                onBloodOxReceived(Integer.toString(value));
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
        //JF2
        intentFilter.addAction(TemplateService.BROADCAST_STEPCOUNT_MEASUREMENT);
        //JF3
        intentFilter.addAction(TemplateService.BROADCAST_BLOODOX_MEASUREMENT);
        return intentFilter;
    }

    public List<sensorData> sensorDataList = initSensorDataList();

    public int numOfServices = 0;
    private int heartRateIDX = 0;
    private int bloodPressIDX = 1;
    private int temperatureIDX = 2;
    private int bloodOxIDX = 3;
    private int stepIDX = 4;
    private int batteryIDX = 5;


    private void onHeartRateReceived(String newReading){
            sensorDataList.get(heartRateIDX).sensorReading = newReading;
    }
    private void onBatteryReceived(String newReading){
        sensorDataList.get(batteryIDX).sensorReading = newReading;
    }
    private void onTemperatureReceived(String newReading) {
        sensorDataList.get(temperatureIDX).sensorReading = newReading;
    }
    private void onStepCountReceived(String newReading) {
        Log.w("P24", "Received Step count: " + newReading);
        sensorDataList.get(stepIDX).sensorReading = newReading;
    }
    private void onBloodOxReceived(String newReading) {
        Log.w("P24", "Received blood ox: " + newReading);
        sensorDataList.get(bloodOxIDX).sensorReading = newReading;
    }

}