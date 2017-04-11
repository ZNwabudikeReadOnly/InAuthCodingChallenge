package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.drawingboardapps.inauthcodingchallenge.Application.MainContentProvider;
import com.drawingboardapps.inauthcodingchallenge.Constants;
import com.drawingboardapps.inauthcodingchallenge.Interface.MainPresenter;
import com.drawingboardapps.inauthcodingchallenge.Models.DataTransfer;
import com.drawingboardapps.inauthcodingchallenge.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import static com.drawingboardapps.inauthcodingchallenge.Activity.BasePermissionActivity.GPS_PERMISSION;
import static com.drawingboardapps.inauthcodingchallenge.Activity.BasePermissionActivity.INTERNET_PERMISSION;
import static com.drawingboardapps.inauthcodingchallenge.Constants.EventBus.GET_API;
import static com.drawingboardapps.inauthcodingchallenge.Constants.EventBus.GET_APPS;
import static com.drawingboardapps.inauthcodingchallenge.Constants.EventBus.GET_GPS;
import static com.drawingboardapps.inauthcodingchallenge.Constants.EventBus.Status.OK;

/**
 * Created by Zach on 4/10/2017.
 */
final class MainPresenterImpl implements MainPresenter, MainInteractor.OnReceivedHttpResult {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onHttpClicked(Context context) {
        mainView.getQueryParam(MainPresenterImpl.this);
    }

    @Override
    public void onReceivedInstalledApps(@NonNull LinkedList<String> installedApps) {
        mainView.showInstalledApps(installedApps);
    }

    @Override
    public void onQueryParamSet(final Context context, final String queryParam) {
        mainView.askPermission(Manifest.permission.INTERNET, getApiPermissionCallback(context, queryParam));
    }

    /***
     * Receivers
     ***/
    @Override
    public void onReceivedHttpResult(Object data) {
        if (data instanceof JSONObject) {
            try {
                mainView.showHttpResult(((JSONObject) data).toString(1));
            } catch (JSONException e) {
                mainView.showError("JSONException: Bad JSON Formatting");
            }
        }

    }

    @Override
    public void onReceivedGpsResult(Location data) {
        mainView.showLocation(data);
    }

    @Override
    public void onReceivedAppsResult(LinkedList<String> data) {

    }

    @Override
    public void onGPSClicked(final Context context) {
        mainView.askPermission(Manifest.permission.ACCESS_FINE_LOCATION, gpsPermissionCallback);
    }

    @Override
    public void onDataTransfer(DataTransfer event) {
        //error handled in super
        if (checkError(event)) {
            return;
        }

        switch (event.getMsg()) {
            case GET_API:
                onReceivedHttpResult(event.getData());
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

    public class PermissionsHelper2{
        private final MainView mainView;
        private final MainPresenter mainPresenter;

        public PermissionsHelper2(MainView mainView, MainPresenter mainPresenter){
            this.mainView = mainView;
            this.mainPresenter = mainPresenter;
        }

        private PermissionsHelper.Callback gpsPermissionCallback = new PermissionsHelper.Callback() {

            @Override
            public void granted() {
                mainView.getQueryParam(MainPresenterImpl.this);
            }

            @Override
            public void denied() {
                mainView.setButtonEnabled(R.id.button1, false);
            }

            @Override
            public int getRequestCode() {
                return GPS_PERMISSION;
            }

            @Override
            public void askAgain() {
                mainView.askPermission(Manifest.permission.ACCESS_FINE_LOCATION, this);
            }
        };

        private PermissionsHelper.Callback getApiPermissionCallback(final Context context, final String queryParam) {
            return new PermissionsHelper.Callback() {
                @Override
                public void granted() {
                    MainContentProvider.Http.makeRequest(mainView.getContext(),
                            queryParam,
                            MainPresenterImpl.this);
                }

                @Override
                public void denied() {
                    mainView.setButtonEnabled(R.id.button1, false);
                }

                @Override
                public int getRequestCode() {
                    return INTERNET_PERMISSION;
                }

                @Override
                public void askAgain() {
                    onQueryParamSet(context, queryParam);
                }
            };
        }
    }

    boolean checkError(DataTransfer event) {
        if (event == null) {
            mainView.showToastError("Data Transfer Event null");
            return true;
        }
        if (event.getStatus() != OK) {
            mainView.showToastError("Error: " + event.getMsg());
            return true;
        }

        if (event.getData() == null) {
            mainView.showToastError("Error, Data null");
            return true;
        }
        return false;
    }
}
