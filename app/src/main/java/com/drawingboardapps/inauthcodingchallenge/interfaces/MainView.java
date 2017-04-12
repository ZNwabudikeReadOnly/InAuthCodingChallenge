package com.drawingboardapps.inauthcodingchallenge.interfaces;

import android.content.Context;
import android.location.Location;

import com.drawingboardapps.inauthcodingchallenge.activity.PermissionsHelper;

import java.util.LinkedList;

/**
 * Created by Zach on 4/10/2017.
 */
public interface MainView {
    void showInstalledApps(LinkedList<String> installedApps);
    void showLocation(Location location);
    void getQueryParam();
    Context getContext();
    void showError(String msg);
    void showHttpResult(String string);
    void askPermission(String manifestPermission, PermissionsHelper.Callback permissionCallback);

    void setButtonEnabled(int buttonId, boolean enabled);
}
