package com.drawingboardapps.inauthcodingchallenge.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.drawingboardapps.mainsdk.sdk.external.models.DataTransfer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Zach on 4/10/2017.
 */
abstract class BaseActivity extends AppCompatActivity{

    private MainPresenterImpl presenter;

    void showToastError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    void logError(String TAG, String msg) {
        //can log to wherever, logging to logd now.
        Log.d(TAG, "logError: " + msg);
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        presenter.onPause();
    }

    @Subscribe
    protected abstract void onDataTransfer(DataTransfer event);

    void setPresenter(MainPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        presenter.onResume();
    }
}
