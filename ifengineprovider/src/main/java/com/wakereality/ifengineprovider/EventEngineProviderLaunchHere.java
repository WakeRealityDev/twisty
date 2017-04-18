package com.wakereality.ifengineprovider;

import com.wakereality.thunderstrike.dataexchange.EngineConst;

/**
 * Created by adminsag on 4/17/17.
 */

public class EventEngineProviderLaunchHere {
    // convention is null or populated, never empty string
    public String filePath = null;
    public int engineCode = EngineConst.ENGINE_UNKNOWN;

    public EventEngineProviderLaunchHere(String filePathString, int enginePickResult) {
        filePath = filePathString;
        engineCode = enginePickResult;
    }
}
