package com.drawingboardapps.inauthcodingchallenge.activity;

import android.Manifest;

import com.drawingboardapps.inauthcodingchallenge.application.MainContentProvider;
import com.drawingboardapps.inauthcodingchallenge.interfaces.MainPresenter;
import com.drawingboardapps.inauthcodingchallenge.R;
import com.drawingboardapps.inauthcodingchallenge.interfaces.MainView;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.drawingboardapps.inauthcodingchallenge.activity.BasePermissionActivity.DISK_PERMISSION;
import static com.drawingboardapps.inauthcodingchallenge.activity.BasePermissionActivity.GPS_PERMISSION;

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

    private void hasPermission(int[] grantResults, Callback callback) {
        if (grantResults.length > 0
                && grantResults[0] == PERMISSION_GRANTED) {
            callback.granted();
            return;
        }
        callback.denied();
    }

    private final MainView mainView;
    private final MainPresenter mainPresenter;

    public PermissionsHelper(MainView mainView, MainPresenter mainPresenter) {
        this.mainView = mainView;
        this.mainPresenter = mainPresenter;
    }


    public final PermissionsHelper.Callback
    getGpsPermissionCallback() {
        return new PermissionsHelper.Callback() {

            @Override
            public void granted() {
                mainPresenter.getGpsLocation();
            }

            @Override
            public void denied() {
                mainView.setButtonEnabled(R.id.btn_gps, false);
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


    public PermissionsHelper.Callback
    getFileSystemPermissionCallback(){
        return new PermissionsHelper.Callback() {
            @Override
            public void granted() {
                MainContentProvider.FileSystem.getInstalledApps(mainView.getContext());
            }

            @Override
            public void denied() {
                mainView.setButtonEnabled(R.id.btn_apps, false);
                mainView.showError(mainPresenter.getString(R.string.perm_denied_usr));
            }

            @Override
            public int getRequestCode() {
                return DISK_PERMISSION;
            }

            @Override
            public void askAgain() {
                mainView.askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this);
            }
        };
    }

}