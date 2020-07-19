package com.zndroid.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * @name:AppUtil
 * @author:lazy
 * @email:luzhenyuxfcy@sina.com
 * @date : 2020/7/19 22:33
 * @version:
 * @description:TODO
 */
public class AppUtil {
    private final String TAG = AppUtil.class.getSimpleName();

    /**
     * Gets the version name in the manifest file
     * @param context
     * @return String
     */
    @NonNull
    public String getVersion(@Nullable Context context) {
        String versionName = "Unknown";
        if (context == null) return versionName;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            Log.w(TAG, "Package Not found:" + context.getPackageName());
        }
        return versionName;
    }

    /**
     * Gets the version code in the manifest file
     * @param context
     * @return long
     */
    @NonNull
    public long getVersionCode(@Nullable Context context) {
        long versionCode = 0;
        if (context == null) return versionCode;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
            else
                versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            Log.w(TAG, "Package Not found:" + context.getPackageName());
        }
        return versionCode;
    }

    /**
     * Gets the application name specified by android:label in the AndroidManifest.xml.
     * It does not work if you hard-code your app name like android:label="MyApp".
     * Use a string resource such as @string/app_name.
     * @param context
     * @return application name
     */
    public String getApplicationName(@NonNull Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

    /**
     * Checks if the target app is installed on the device
     * @param context
     * @param targetPackageName
     * @return boolean
     */
    public boolean isAppInstalled(@NonNull Context context, String targetPackageName) {
        PackageManager pm = context.getPackageManager();

        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo info : list) {
            if (targetPackageName.equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if an activity with the given intent is successfully started
     * @param context
     * @param intent
     * @return
     */
    public boolean startActivity(@NonNull Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ae) {
            return false;
        }

        return true;
    }

    /**
     * 重启当前APP
     * */
    public void restartApp(@NonNull Context context) {
        Intent intent = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (null != intent) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent restartIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, 500, restartIntent);

            System.exit(0);
        }
    }

}
