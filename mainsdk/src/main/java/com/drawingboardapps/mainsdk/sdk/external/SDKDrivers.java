package com.drawingboardapps.mainsdk.sdk.external;

import com.drawingboardapps.mainsdk.sdk.hidden.FileSystemDriver;
import com.drawingboardapps.mainsdk.sdk.hidden.GPSDriver;

/**
 * Created by Zach on 4/11/2017.
 */

public class SDKDrivers extends GPSDriver{

    protected FileSystemDriver getFileSystemDriver() {
        return new FileSystemDriver();
    }
}
