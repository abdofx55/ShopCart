package com.shopcart.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;


public class NetworkUtils {




    // Method to check network state
    public static boolean isNetworkConnected(Context context) {
        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        isConnected = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        isConnected = true;
                    }
                }

//                connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
//                                                                       @Override
//                                                                       public void onAvailable(@NonNull Network network) {
//                                                                           isConnected = true;
//                                                                       }
//
//                                                                       @Override
//                                                                       public void onLost(@NonNull Network network) {
//                                                                           isConnected = false;
//                                                                       }
//                                                                   }
//
//                );

            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            }

        }
        return isConnected;
    }
}
