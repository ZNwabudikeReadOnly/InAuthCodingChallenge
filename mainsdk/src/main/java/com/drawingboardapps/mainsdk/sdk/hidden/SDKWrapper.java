package com.drawingboardapps.mainsdk.sdk.hidden;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zach on 4/12/17.
 */

public final class SDKWrapper {
    private final FileSystemDriver fileSystem;
    private final GPSDriver gps;
    private final HttpAPIDriver http;

    public SDKWrapper(EventBus eventBusInstance, Context context){
        fileSystem = new FileSystemDriver(eventBusInstance);
        gps = new GPSDriver(eventBusInstance, context);
        http = new HttpAPIDriver(eventBusInstance);
    }

    public final void getListOfApplications() {
        fileSystem.getInstalledApplications();
    }


    public final void getGpsLocation() {
        gps.getLocation();
    }

    public void makeHttpRequest(String query) {
        http.makeRequest(query);
    }
}
