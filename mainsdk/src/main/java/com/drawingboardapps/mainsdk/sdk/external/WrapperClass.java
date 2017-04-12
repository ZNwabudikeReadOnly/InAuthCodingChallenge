package com.drawingboardapps.mainsdk.sdk.external;

/**
 * Created by Zach on 4/11/2017.
 */

public class WrapperClass extends SDKDrivers{

    public void getListOfApplications(){
        getFileSystemDriver().getInstalledApplications();
    }
}
