package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.app.Dialog;

import com.drawingboardapps.inauthcodingchallenge.Application.MainContentProvider;

/**
 * Created by Zach on 4/10/2017.
 */
public interface MainInteractor {

    void onQueryParamSet(final String param);
    void onCancel(Dialog dialog);

    interface OnReceivedHttpResult {
        void onReceivedHttpResult(Object result);
    }
}
