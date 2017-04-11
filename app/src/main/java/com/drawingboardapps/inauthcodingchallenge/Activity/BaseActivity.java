package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.drawingboardapps.inauthcodingchallenge.Models.DataTransfer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.drawingboardapps.inauthcodingchallenge.Constants.EventBus.Status.OK;

/**
 * Created by Zach on 4/10/2017.
 */
abstract class BaseActivity extends AppCompatActivity{

    void showToastError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    void logError(String TAG, String msg) {
        //can log to wherever, logging to logd now.
        Log.d(TAG, "logError: " + msg);
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    protected abstract void onDataTransfer(DataTransfer event);
}
