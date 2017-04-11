package com.drawingboardapps.inauthcodingchallenge.Application;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zach on 4/10/2017.
 */
public class Tools {

    public static LinkedList<String> getInstalledApps(Context context) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        LinkedList<String> installedApps = new LinkedList<>();
        int i = 0;
        for (ResolveInfo info : resolveInfo){
            if (info.activityInfo != null){
                installedApps.add(i++, info.activityInfo.packageName);
            }
        }
        return installedApps;
    }

}
