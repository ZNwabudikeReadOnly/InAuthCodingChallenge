package com.drawingboardapps.inauthcodingchallenge.application;

import android.app.Application;
import com.drawingboardapps.mainsdk.sdk.external.MainSDK;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zach on 4/10/2017.
 *
 * Main Application class
 */
public class MainApplication extends Application{

    private MainSDK mainSDK;

    @Override
    public void onCreate(){
        super.onCreate();
        setupSDK();
    }

    private void setupSDK() {
        mainSDK = new MainSDK(EventBus.getDefault());
    }

    public MainSDK getSDK() {
        return mainSDK;
    }
}
