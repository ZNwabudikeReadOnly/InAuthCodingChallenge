package com.drawingboardapps.inauthcodingchallenge.Application;

import android.content.Context;

import com.drawingboardapps.inauthcodingchallenge.Activity.MainInteractor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zach on 4/10/2017.
 */
public class MainContentProvider {

    public static class Apps{
        public static LinkedList<String> getInstalledApps(Context context){
            return Tools.getInstalledApps(context);
        }
    }

    public static class Http {
        public static void makeRequest(Context context, String query, MainInteractor.OnReceivedHttpResult onReceivedHttpResult) {
            ((MainApplication) context.getApplicationContext()).getHttpDriver().makeRequest(query);
        }
    }

    public static class GPS {
        public static void getLocation(Context context) {
            ((MainApplication) context.getApplicationContext()).getGPSDriver().getLocation();
        }
    }
}
