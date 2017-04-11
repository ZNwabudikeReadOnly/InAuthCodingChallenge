package com.drawingboardapps.inauthcodingchallenge.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telecom.Call;

import com.drawingboardapps.inauthcodingchallenge.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by Zach on 4/11/2017.
 */
public abstract class BasePermissionActivity extends BaseActivity {
    public static final int INTERNET_PERMISSION = 0;
    public static final int GPS_PERMISSION = 1;
    public static final int DISK_PERMISSION = 2;

    HashMap<String, PermissionsHelper.Callback> requestedPermissions = new HashMap<>();
    private final String TAG = BasePermissionActivity.class.getSimpleName();

    @Override
    public abstract void onRequestPermissionsResult(int requestCode,
                                                    @NotNull String permissions[],
                                                    @NotNull int[] grantResults);

    boolean hasPermission(int[] grantResults) {
        return grantResults.length > 0
                && grantResults[0] == PERMISSION_GRANTED;
    }

    void ifHasGrantOrDeny(String[] permissions, int[] grantResults) {
        if (hasPermission(grantResults)) {
            requestedPermissions.get(permissions[0]).granted();
        } else {
            requestedPermissions.get(permissions[0]).denied();
        }
    }

    void showDialogPermissionNeeded(Context context, String manifestPermission, final PermissionsHelper.Callback callback) {
        int id = getPermissionReason(manifestPermission);
        if (id == -1) {
            logError(TAG, "Unrecognized Permission: " + manifestPermission);
            return;
        }

        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage(getString(id));
        d.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.askAgain();
            }
        });
        d.setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.denied();
            }
        });
        d.show();
    }

    private int getPermissionReason(String manifestPermission) {
        switch (manifestPermission) {
            case Manifest.permission.INTERNET:
                return R.string.reason_internet;
            default:
                break;
        }
        return -1;
    }
}
