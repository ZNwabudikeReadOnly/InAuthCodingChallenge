package com.drawingboardapps.inauthcodingchallenge.activity;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import com.drawingboardapps.inauthcodingchallenge.interfaces.MainView;
import com.drawingboardapps.inauthcodingchallenge.R;
import com.drawingboardapps.mainsdk.sdk.external.models.DataTransfer;
import com.drawingboardapps.mainsdk.sdk.hidden.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends BasePermissionActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.button1)
    public Button button1;
    @BindView(R.id.btn_gps)
    public Button button2;
    @BindView(R.id.btn_apps)
    public Button button3;
    @BindView(R.id.tv_status)
    public TextView status;

    private MainPresenterImpl presenter;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.presenter = new MainPresenterImpl(this);
        super.setPresenter(presenter);
    }

    /*******************
     * Click Listeners *
     *******************/
    @OnClick(R.id.button1)
    public void onHttpClicked() {
        presenter.onHttpClicked(this);
    }

    @OnClick(R.id.btn_gps)
    public void onGpsClicked() {
        presenter.onGPSClicked(this);
    }

    @OnClick(R.id.btn_apps)
    public void onAppsClicked() {
        presenter.onFilesClicked(this);
//        presenter.onReceivedInstalledApps(installedApps);
    }

    /*****************************************************************
     * Begin Objectives                             *
     *****************************************************************/
    @Override
    public void showInstalledApps(LinkedList<String> installedApps) {
        String installedText = getString(R.string.txt_installed_apps);
        for (String app : installedApps) {
            installedText += app + "\n";
        }

        status.setText(status.getText().toString() + "\n" + installedText);
    }

    @Override
    public void showLocation(Location location) {
        String locationText = String.format("%s%s:%s",
                getString(R.string.txt_location),
                location.getLongitude(),
                location.getLatitude());
        status.setText(status.getText().toString() + "\n" + locationText);
    }


    @Override
    public void showHttpResult(String string) {
        status.setText(
                status.getText().toString() + "\n"
                        + getString(R.string.txt_http_result) + "\n"
                        + string);
    }


    @Override
    public void askPermission(String manifestPermission,
                              PermissionsHelper.Callback permissionCallback) {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, manifestPermission) != PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    manifestPermission)) {
                showDialogPermissionNeeded(this, manifestPermission, permissionCallback);
            } else {
                requestedPermissions.put(manifestPermission, permissionCallback);
                ActivityCompat.requestPermissions(this,
                        new String[]{manifestPermission},
                        permissionCallback.getRequestCode());
            }
        } else {
            permissionCallback.granted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[],
                                           @NotNull int[] grantResults) {

        switch (requestCode) {
            case INTERNET_PERMISSION:
                ifHasGrantOrDeny(permissions, grantResults);
                break;
            case GPS_PERMISSION:
                ifHasGrantOrDeny(permissions, grantResults);
                break;
            case DISK_PERMISSION:
                ifHasGrantOrDeny(permissions, grantResults);
                break;
            default:
                //this should not happen
                logError(TAG, "Unregistered permission requested");
                break;
        }
    }

    /***** End Objectives ****/

    /*****
     * View Methods
     *****/

    @Override
    public void showError(String msg) {
        showToastError(msg);
    }


    @Override
    public void setButtonEnabled(int buttonId, boolean enabled) {
        findViewById(buttonId).setEnabled(enabled);
    }


    /**
     * Subscribe to EventBus event {@link DataTransfer}
     *
     * @param event suppied by EventBus
     */
    @Override
    @Subscribe
    @SuppressWarnings("unchecked")
    final protected void onDataTransfer(DataTransfer event) {
        presenter.onDataTransfer(event);
    }

    /*****
     * Implemented Methods
     *****/

    @Override
    public void getQueryParam() {
        //TODO create a dialog to get the api of choice. hardcoded for now
        presenter.onQueryParamSet(this, Constants.Http.QUERY_PARAM);
    }

    @Override
    public Context getContext() {
        return this;
    }


}
