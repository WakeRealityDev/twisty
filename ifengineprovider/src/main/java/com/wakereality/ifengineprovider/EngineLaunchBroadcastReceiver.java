package com.wakereality.ifengineprovider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wakereality.thunderstrike.dataexchange.EngineConst;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by adminsag on 4/17/17.
 */

public class EngineLaunchBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "EngineLaunch";
    protected Context appContext;
    public static int onReceiveCount = 0;


    @Override
    public void onReceive(final Context context, Intent intent) {
        onReceiveCount++;

        /* Guard against obvious fuzzing crashes */
        if (intent == null) {
            Log.e(TAG, "[outsideApp] intent is empty");
            return;
        }

        if (intent.getAction() == null) {
            Log.e(TAG, "[outsideApp] intent.getAction() is empty");
            return;
        }

        boolean isStoryLaunch = false;

        appContext = context.getApplicationContext();

        int enginePickParameter = intent.getIntExtra(EngineConst.LAUNCH_PARAM_KEY_ENGINECODE, EngineConst.ENGINE_UNKNOWN);
        int enginePickResult = EngineConst.ENGINE_UNKNOWN;
        boolean enginePickMandatory = false;
        switch (intent.getAction()) {
            case "interactivefiction.engine.automatch":
                isStoryLaunch = true;
                // engine pick is mandatory
                enginePickMandatory = true;
                Log.w(TAG, "[enginePick][outsideApp] automatch");
                break;
            case "interactivefiction.engine.zmachine":
                isStoryLaunch = true;
                enginePickResult = EngineConst.ENGINE_Z_DEFAULT;
                Log.i(TAG, "[enginePick][outsideApp] enginecode " + enginePickParameter);
                // ToDo: some will not take blorb files... validate.
                switch (enginePickParameter) {
                    case EngineConst.ENGINE_Z_BOCFEL:
                        enginePickResult = EngineConst.ENGINE_Z_BOCFEL;
                        break;
                    case EngineConst.ENGINE_Z_FROTZ:
                        enginePickResult = EngineConst.ENGINE_Z_FROTZ;
                        break;
                    case EngineConst.ENGINE_Z_NITFOL:
                        enginePickResult = EngineConst.ENGINE_Z_NITFOL;
                        break;
                }
                break;
            case "interactivefiction.engine.glulx":
                isStoryLaunch = true;
                enginePickResult = EngineConst.ENGINE_GLULX_DEFAULT;
                Log.i(TAG, "[enginePick][outsideApp] enginecode " + enginePickParameter);
                switch (enginePickParameter) {
                    case EngineConst.ENGINE_GLULX_GIT:
                        enginePickResult = EngineConst.ENGINE_GLULX_GIT;
                        break;
                    case EngineConst.ENGINE_GLULX_GLULXE:
                        enginePickResult = EngineConst.ENGINE_GLULX_GLULXE;
                        break;
                }
                break;

            case "interactivefiction.enginemeta.runstory":
                AnnounceEngineProvider.announceAvailableInterpretersAsEngineProvider(appContext);
                return;   // **** Method execution ends here
        }

        if (isStoryLaunch) {
            String filepath = intent.getStringExtra("path");

            // Twisty NDK code has a determination capability based on file extension
            Log.i(TAG, "[enginePick][outsideApp] enginePickResult " + enginePickResult + " filepath " + filepath);

            AppInfoSpot.appLaunchFromReceiver = true;
            EventBus.getDefault().post(new EventEngineProviderLaunchHere(filepath, enginePickResult));
        } else {
            Log.w(TAG, "[outsideeApp] not a story launch, ignoring: " + intent.getAction());
        }
    }

}
