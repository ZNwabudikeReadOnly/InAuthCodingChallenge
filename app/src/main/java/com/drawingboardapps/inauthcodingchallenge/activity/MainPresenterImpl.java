package com.drawingboardapps.inauthcodingchallenge.activity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.drawingboardapps.inauthcodingchallenge.application.MainContentProvider;
import com.drawingboardapps.inauthcodingchallenge.interfaces.MainPresenter;
import com.drawingboardapps.inauthcodingchallenge.interfaces.MainView;
import com.drawingboardapps.mainsdk.sdk.external.models.DataTransfer;
import com.drawingboardapps.mainsdk.sdk.external.models.LEDData;

import java.util.LinkedList;

import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.GET_API;
import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.GET_APPS;
import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.GET_GPS;
import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.Status.OK;


/**
 * Created by Zach on 4/10/2017.
 */
public final class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;
    private final PermissionsHelper permissionHelper;

    public MainPresenterImpl(@NonNull MainView mainView) {
        this.mainView = mainView;
        this.permissionHelper = new PermissionsHelper(mainView, this);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    /****
     * Click Handlers
     ****/
    @Override
    public void onHttpClicked(Context context) {
        mainView.getQueryParam();
    }

    @Override
    public void onFilesClicked(MainActivity mainActivity) {
        mainView.askPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                permissionHelper.getFileSystemPermissionCallback()
        );
    }

    @Override
    public void onGPSClicked(final Context context) {
        mainView.askPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                permissionHelper.getGpsPermissionCallback()
        );
    }

    /***
     * Receivers
     ***/

    @Override
    @SuppressWarnings("unchecked")
    public void onDataTransfer(DataTransfer event) {
        //error handled in super
        if (event == null) {
            mainView.showError("Data Transfer Event null");
            return;
        }
        if (event.getStatus() != OK) {
            mainView.showError("Error: " + event.getMsg());
            return;
        }

        if (event.getData() == null) {
            mainView.showError("Error, Data null");
            return;
        }

        switch (event.getMsg()) {
            case GET_API:
                onReceivedApiResponse(event);
                break;
            case GET_GPS:
                if (event.getData() instanceof Location) {
                    onReceivedGpsResult((Location) event.getData());
                }
                break;
            case GET_APPS:
                onReceivedAppsResult((LinkedList<String>) event.getData());
                break;
            default:
                mainView.showError("Unknown Function");
                break;
        }
    }

    @Override
    public void onReceivedInstalledApps(@NonNull LinkedList<String> installedApps) {
        mainView.showInstalledApps(installedApps);
    }

    @Override
    public void onReceivedApiResponse(DataTransfer event) {
        if (event.getData() instanceof LEDData) {
            mainView.showHttpResult(((LEDData) event.getData()).toString());
            return;
        }
        mainView.showError("Unexpected Response Object");
    }

    @Override
    public void onReceivedGpsResult(Location data) {
        mainView.showLocation(data);
    }

    @Override
    public void onReceivedAppsResult(LinkedList<String> data) {
        mainView.showInstalledApps(data);
    }

    @Override
    public void onQueryParamSet(final Context context, final String queryParam) {
        MainContentProvider.Http.makeRequest(
                mainView.getContext(),
                queryParam
        );
    }

    @Override
    public void getInstalledAppList() {
        MainContentProvider.FileSystem.getInstalledApps(mainView.getContext());
    }

    @Override
    public void getGpsLocation() {
        MainContentProvider.GPS.getLocation(mainView.getContext());
    }

    @Override
    public String getString(int resId) {
        return mainView.getContext().getString(resId);
    }

}
