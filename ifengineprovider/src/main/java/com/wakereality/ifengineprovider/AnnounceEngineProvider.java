package com.wakereality.ifengineprovider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Stephen A. Gutknecht on 4/17/17.
 */

public class AnnounceEngineProvider {

    public static final String TAG = "EngineProvider";

    public static final String[] enginesHere = new String[] {
            "interactivefiction.engine.zmachine",
            "interactivefiction.engine.glulx",
    };


    public static void announceAvailableInterpretersAsEngineProvider(Context appContext) {
        if (!AppInfoSpot.applicationPermissionWriteStorageConfirmed) {
            Log.e(TAG, "[engineProvider] not announcing with sendBroadcast, storage write permission not yet granted.");
            Toast.makeText(appContext, "WARNING: " + appContext.getResources().getText(R.string.app_name) + " needs storage permission", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent engineMetaIntent = new Intent("interactivefiction.enginemeta.storyengines");

        engineMetaIntent.putExtra("engines_available", enginesHere);
        //engineMetaIntent.putExtra("stories_built_in", StoriesConst.storiesHereBuiltIn);
        //engineMetaIntent.putExtra("stories_built_in_description_EN", StoriesConst.storiesHereBuiltInDescription_EN);
        //engineMetaIntent.putExtra("stories_built_in_engine_code", StoriesConst.storiesHereBuiltInEngineCode);
        engineMetaIntent.putExtra("sender", AppInfoSpot.applicationEngineProviderPackageName);
        engineMetaIntent.putExtra("sender_versioncode", BuildConfig.VERSION_CODE);
        //engineMetaIntent.putExtra("story_save_path", EasyAppControlsStoryData.gameEngineAppFlavorSpecificWorkingDirectory);
        appContext.sendBroadcast(engineMetaIntent);
        Log.i(TAG, "[engineProvider] sendBroadcast of engines_available");
    }

    /*
    Send in negative engineCode to act as wildcard, all engines.
   */
    public static void announceFoundStoriesAsEngineProvider(Context appContext, int matchEngineCode) {
        Intent engineMetaIntent = new Intent("interactivefiction.enginemeta.stories");

        //engineMetaIntent.putExtra("stories_found", StoriesConst.storiesHereBuiltIn);
        engineMetaIntent.putExtra("sender", AppInfoSpot.applicationEngineProviderPackageName);
        engineMetaIntent.putExtra("sender_versioncode", BuildConfig.VERSION_CODE);
        engineMetaIntent.putExtra("list_type", 1);

        // Use codes, smaller size in RAM
        // Hashes CAN compress, as they are only ASCII representations of 256 bit values. Otherwise, binary hash would not be compressible.
        //final int[] storiesEngineCodes = FindStoryFilesHashSave.buildArrayOfEngineCodesForAllFoundStories(appContext, matchEngineCode);

        if (1==1)
        {
            //engineMetaIntent.putExtra("stories", FindStoryFilesHashSave.buildArrayOfHashForAllFoundStories(appContext, matchEngineCode));
            //engineMetaIntent.putExtra("stories_engine_code", storiesEngineCodes);
        }
        appContext.sendBroadcast(engineMetaIntent);
        // Warn level so logged even if squelched
        //Log.w(TAG ,"[engineStories] sent " + engineMetaIntent.getAction() + " count " + storiesEngineCodes.length);
        System.gc();
    }

}
