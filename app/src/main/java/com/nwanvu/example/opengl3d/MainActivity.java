package com.nwanvu.example.opengl3d;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.nwanvu.example.opengl3d.adapters.ViewPagerAdapter;
import com.nwanvu.example.opengl3d.fragment.CubeFragment;
import com.nwanvu.example.opengl3d.fragment.DiamondFragment;


public class MainActivity extends AppCompatActivity {

    private static final String RUN_TIME = "run_app_time";
    private static final String ADMOB_APP_ID = "ca-app-pub-7209317417366395~4098186261";

    private SharedPreferences preferences;
    private int runAppTime;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, ADMOB_APP_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        runAppTime = preferences.getInt(RUN_TIME, 0);

        // Create the interstitial.
        checkAds();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        runAppTime++;
        checkAds();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences = null;
        interstitial = null;
    }

    private void checkAds() {
        if (BuildConfig.DEBUG) return;
        if (runAppTime == 5) {
            runAppTime = 0;
            showAds();
        }
        preferences.edit().putInt(RUN_TIME, runAppTime).apply();
    }

    private void showAds() {
        if (interstitial == null) {
            final String adsID = getString(R.string.main_activity_interstitial);
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(adsID);
        } else if (interstitial.isLoaded()) {
            interstitial.show();
            return;
        }
        // Create ad request.
        final String[] deviceIds = getResources().getStringArray(R.array.test_device_id);
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        for (String deviceId : deviceIds) {
            builder.addTestDevice(deviceId);
        }
        // Begin loading your interstitial.
        interstitial.loadAd(builder.build());
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitial.show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DiamondFragment.newInstance(), DiamondFragment.NAME);
        adapter.addFragment(CubeFragment.newInstance(), CubeFragment.NAME);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_gift:
                showAds();
                break;
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_source))));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
