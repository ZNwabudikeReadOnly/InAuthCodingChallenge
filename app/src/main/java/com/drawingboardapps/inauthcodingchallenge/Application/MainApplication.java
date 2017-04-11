package com.drawingboardapps.inauthcodingchallenge.Application;

import android.app.Application;

import com.drawingboardapps.inauthcodingchallenge.Driver.GPSDriver;
import com.drawingboardapps.inauthcodingchallenge.Driver.HttpAPI;

/**
 * Created by Zach on 4/10/2017.
 */

public class MainApplication extends Application{

    private static HttpAPI httpDriver;

    @Override
    public void onCreate(){
        setupHttpDriver();
        setupGPSDriver();
    }

    private void setupHttpDriver() {

    }

    private void setupGPSDriver(){
        this.gpsDriver = new GPSDriver();
    }

    public HttpAPI getHttpDriver() {
        return httpDriver;
    }

    public GPSDriver getGPSDriver() {
        return GPSDriver;
    }
}
