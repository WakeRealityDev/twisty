package com.wakereality.twisty.engineprovider;

import android.content.Context;
import android.content.Intent;

import com.google.code.twisty.Twisty;
import com.wakereality.ifengineprovider.EventEngineProviderLaunchHere;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by adminsag on 4/17/17.
 */

public class LaunchStoryHelper {
    protected Context appContext;

    public LaunchStoryHelper(Context context) {
        if (! EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        appContext = context;
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventEngineProviderLaunchHere event) {
        Intent startTwistyActivity = new Intent(appContext, Twisty.class);
        // Note: These param names are internal to Twisty app and not don't follow any established naming convention.
        startTwistyActivity.putExtra("filepath", event.filePath);
        startTwistyActivity.putExtra("enginecode", event.engineCode);
        appContext.startActivity(startTwistyActivity);
    }
}
