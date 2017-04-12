package com.drawingboardapps.mainsdk.sdk.hidden;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zach on 4/10/2017.
 */
public class GPSDriver implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final EventBus eventBusInstance;
    Location location = null;
    private final String TAG = GPSDriver.class.getSimpleName();

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public GPSDriver(EventBus eventBusInstance, Context context) {
        this.eventBusInstance = eventBusInstance;
        init(context);
    }

    private void init(Context context) {
        mLocationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    public void destory(){
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

//        LocationServices.FusedLocationApi.removeLocationUpdates(
//                mGoogleApiClient, (LocationListener) this);
    }
    /**
     * This function assumes the permissions have already been requested
     * @return
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
        Log.d(TAG, "onLocationChanged: ");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
