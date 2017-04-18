package com.google.code.twisty;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.wakereality.ifengineprovider.AnnounceEngineProvider;
import com.wakereality.ifengineprovider.AppInfoSpot;
import com.wakereality.twisty.engineprovider.LaunchStoryHelper;


/**
 * Created by Stephen A. Gutknecht on 4/17/17.
 */

public class TwistyApplication extends Application {

    protected LaunchStoryHelper launchStoryHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        AppInfoSpot.applicationEngineProviderPackageName = BuildConfig.APPLICATION_ID;

        launchStoryHelper = new LaunchStoryHelper(getApplicationContext());

        // Hack to detect restart of activity/app
        AppInfoSpot.appStartupOnCreate = true;
        AppInfoSpot.appLaunchFromReceiver = false;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            AppInfoSpot.applicationPermissionWriteStorageConfirmed = true;
            AnnounceEngineProvider.announceAvailableInterpretersAsEngineProvider(getApplicationContext());
        }

        Log.i("Twisty", "[appStartup] TwistyApplication onCreate completed.");
    }
}
