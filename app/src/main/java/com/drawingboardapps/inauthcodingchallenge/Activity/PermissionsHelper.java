package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.Manifest;
import android.content.Context;

import com.drawingboardapps.inauthcodingchallenge.Application.MainContentProvider;
import com.drawingboardapps.inauthcodingchallenge.Interface.MainPresenter;
import com.drawingboardapps.inauthcodingchallenge.R;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.drawingboardapps.inauthcodingchallenge.Activity.BasePermissionActivity.GPS_PERMISSION;
import static com.drawingboardapps.inauthcodingchallenge.Activity.BasePermissionActivity.INTERNET_PERMISSION;

/**
 * Created by Zach on 4/11/2017.
 */

public class PermissionsHelper {

    public interface Callback {
        void granted();

        void denied();

        int getRequestCode();

        void askAgain();
    }


    public void handlePermission(int requestCode, int[] grantResults, Callback callback) {

    }

    private void hasPermission(int[] grantResults, Callback callback) {
        if (grantResults.length > 0
                && grantResults[0] == PERMISSION_GRANTED){
            callback.granted();
            return;
        }
        callback.denied();
    }

    private final MainView mainView;
    private final MainPresenter mainPresenter;

    public PermissionsHelper(MainView mainView, MainPresenter mainPresenter){
        this.mainView = mainView;
        this.mainPresenter = mainPresenter;
    }


    public PermissionsHelper.Callback getGpsPermissionCallback(final MainInteractor.OnReceivedHttpResult listener) {
        return new PermissionsHelper.Callback() {

            @Override
            public void granted() {
                mainView.getQueryParam(listener);
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
    }

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