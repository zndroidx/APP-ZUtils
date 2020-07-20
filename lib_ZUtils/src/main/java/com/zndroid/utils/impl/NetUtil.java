package com.zndroid.utils.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

/**
 * @name:NetUtil
 * @author:lazy
 * @email:luzhenyuxfcy@sina.com
 * @date : 2020/7/20 22:17
 * @version:
 * @description:
 * */
public class NetUtil {
    public boolean isNetworkConnected(@NonNull Context context) {
        NetworkInfo activeNetwork = getActiveNetwork(context);
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public boolean isWifiConnected(@NonNull Context context) {
        NetworkInfo activeNetwork = getActiveNetwork(context);
        boolean isWifiConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting() &&
                activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        return isWifiConnected;
    }

    public boolean isWiMaxConnected(@NonNull Context context) {
        NetworkInfo activeNetwork = getActiveNetwork(context);
        boolean isWiMaxConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting() &&
                activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX;

        return isWiMaxConnected;
    }

    private NetworkInfo getActiveNetwork(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
