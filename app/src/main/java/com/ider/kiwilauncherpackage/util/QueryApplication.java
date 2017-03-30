package com.ider.kiwilauncherpackage.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/3/13.
 */

public class QueryApplication {
    public static List<PackageHolder> query(Context context) {
        List<PackageHolder> enties = new ArrayList<>();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (!packageName.equals("com.ider.tools")) {
                enties.add(new PackageHolder(packageName, null));
            }
        }
        return enties;
    }
}
