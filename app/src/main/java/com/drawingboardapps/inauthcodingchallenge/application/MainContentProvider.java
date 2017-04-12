package com.drawingboardapps.inauthcodingchallenge.application;

import android.content.Context;

/**
 * Created by Zach on 4/10/2017.
 *
 * MainContentProvider, is used to deliver content to the app
 */
public class MainContentProvider {

    /**
     * Http class communicates with SDK to perform functions
     */
    public static class Http {
        public static void makeRequest(Context context, String query) {
            ((MainApplication) context.getApplicationContext()).getSDK().getLEDSavingsData(query);
        }
    }

    /**
     * GPS class communicates with SDK to perform functions
     */
    public static class GPS {
        public static void getLocation(Context context) {
            ((MainApplication) context.getApplicationContext()).getSDK().getGpsLocation();
        }
    }

    /**
     * FileSystem class communicates with SDK to perform functions
     */
    public static class FileSystem {
        public static void getInstalledApps(Context context){
            ((MainApplication) context.getApplicationContext()).getSDK().getListOfApplications();
        }
    }
}
