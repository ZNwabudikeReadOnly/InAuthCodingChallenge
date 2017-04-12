package com.drawingboardapps.mainsdk.sdk.hidden;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zach on 4/11/2017.
 */
class FileSystemDriver {

    private final EventBus eventBusInstance;

    public FileSystemDriver(EventBus eventBusInstance) {
        this.eventBusInstance = eventBusInstance;
    }

    protected void getInstalledApplications() {

    }
}
