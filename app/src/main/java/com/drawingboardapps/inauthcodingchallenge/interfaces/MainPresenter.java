package com.drawingboardapps.inauthcodingchallenge.interfaces;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.drawingboardapps.inauthcodingchallenge.activity.MainActivity;
import com.drawingboardapps.mainsdk.sdk.external.models.DataTransfer;

import java.util.LinkedList;

/**
 * Created by Zach on 4/10/2017.
 */
public interface MainPresenter {
    void onResume();

    void onReceivedInstalledApps(@NonNull LinkedList<String> installedApps);

    void onDestroy();

    void onHttpClicked(Context context);

    void onQueryParamSet(Context context, String queryParam);

    void onReceivedGpsResult(Location data);

    void onReceivedAppsResult(LinkedList<String> data);

    void onGPSClicked(Context context);

    void getInstalledAppList();

    void onDataTransfer(DataTransfer event);

    void onFilesClicked(MainActivity mainActivity);

    void onReceivedApiResponse(DataTransfer event);

    /**
     * Get String from resid
     *
     * @param resId
     * @return string
     */
    String getString(int resId);

    void getGpsLocation();

    void onPause();
}
