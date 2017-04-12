package com.drawingboardapps.mainsdk.sdk.external;

import com.drawingboardapps.mainsdk.sdk.hidden.SDKWrapper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zach on 4/11/2017.
 */

public class MainSDK{

    private final SDKWrapper wrapper;

    public MainSDK(EventBus eventBusInstance){
        wrapper = new SDKWrapper(eventBusInstance);
    }

    public void getListOfApplications(){
        wrapper.getListOfApplications();
    }

    public void getGpsLocation(){
        wrapper.getGpsLocation();
    }

    public void getLEDSavingsData(String query){
        wrapper.makeHttpRequest(query);
    }

}
