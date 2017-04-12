package com.drawingboardapps.inauthcodingchallenge.application;

import android.content.Context;

/**
 * Created by Zach on 4/10/2017.
 */
public class MainContentProvider {

    public static class Http {
        public static void makeRequest(Context context, String query) {
            ((MainApplication) context.getApplicationContext()).getHttpDriver().makeRequest(query);
        }
    }

    public static class GPS {
        public static void getLocation(Context context) {
            ((MainApplication) context.getApplicationContext()).getGPSDriver().getLocation();
        }
    }

    public static class FileSystem {
        public static void getInstalledApps(Context context){
            ((MainApplication) context.getApplicationContext()).getGetFileSystemDriver().getInstalledApplications();
        }
    }
}
