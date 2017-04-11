package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import com.drawingboardapps.inauthcodingchallenge.Application.MainContentProvider;
import com.drawingboardapps.inauthcodingchallenge.Models.DataTransfer;
import com.drawingboardapps.inauthcodingchallenge.R;

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
    private Button button1;
    @BindView(R.id.button2)
    private Button button2;
    @BindView(R.id.button3)
    private Button button3;
    @BindView(R.id.tv_status)
    private TextView status;

    private MainPresenterImpl presenter;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    /*******************
     * Click Listeners *
     *******************/
    @OnClick(R.id.button1)
    private void onHttpClicked() {
        presenter.onHttpClicked(this);
    }

    @OnClick(R.id.button2)
    private void onGpsClicked() {
        presenter.onGPSClicked(this);
    }

    @OnClick(R.id.button3)
    private void onAppsClicked() {
        LinkedList<String> installedApps = MainContentProvider.Apps.getInstalledApps(this);
        presenter.onReceivedInstalledApps(installedApps);
    }

    /*****
     * Objectives
     ****/
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
        status.setText(status.getText().toString() + "\n" + getString(R.string.txt_http_result));
    }

    @Override
    public void askPermission(String manifestPermission,
                              PermissionsHelper.Callback permissionCallback) {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, manifestPermission) != PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    manifestPermission)){
                showDialogPermissionNeeded(this, manifestPermission, permissionCallback);
            }else{
                requestedPermissions.put(manifestPermission, permissionCallback);

                ActivityCompat.requestPermissions(this,
                        new String[]{manifestPermission},
                        permissionCallback.getRequestCode());
            }
        }else{
            permissionCallback.granted();
        }
    }

    /*****View Methods *****/
    @Override
    public void setButtonEnabled(int buttonId, boolean enabled) {
        findViewById(buttonId).setEnabled(enabled);
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
     * EventBus
     *****/
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
    public void showError(String msg) {
        showToastError(msg);
    }

    @Override
    public void getQueryParam(MainInteractor.OnReceivedHttpResult listener) {
        showDialogApiChoices(listener);
    }

    //TODO show dialog to pick what to query
    private void showDialogApiChoices(MainInteractor.OnReceivedHttpResult listener) {
        String param = "PARAM";
        listener.onReceivedHttpResult(param);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
