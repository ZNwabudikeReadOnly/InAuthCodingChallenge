package com.drawingboardapps.inauthcodingchallenge.application;

import android.app.Application;

import com.drawingboardapps.mainsdk.sdk.hidden.FileSystemDriver;
import com.drawingboardapps.mainsdk.sdk.hidden.GPSDriver;
import com.drawingboardapps.mainsdk.sdk.hidden.HttpAPIDriver;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zach on 4/10/2017.
 */

public class MainApplication extends Application{

    private static HttpAPIDriver httpDriver;
    private static GPSDriver gpsDriver;
    private static FileSystemDriver fsDriver;

    @Override
    public void onCreate(){
        setupHttpDriver();
        setupGPSDriver();
        setupFileSystemDriver();
    }

    private void setupFileSystemDriver() {fsDriver = new FileSystemDriver();}

    private void setupHttpDriver() {
        httpDriver = new HttpAPIDriver(EventBus.getDefault());
    }

    private void setupGPSDriver(){
        gpsDriver = new GPSDriver();
    }

    public HttpAPIDriver getHttpDriver() {
        return httpDriver;
    }

    public GPSDriver getGPSDriver() {
        return gpsDriver;
    }

    public FileSystemDriver getGetFileSystemDriver() {return fsDriver;}
}
