package com.example.aditya.autodisable;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

public class NetworkConnCheckReceiver extends BroadcastReceiver {


    public final static String ACTIVITY = "com.example.aditya.autodisable";
    ConnectivityManager cm;
    NetworkInfo netInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();



        Intent i = new Intent("com.example.aditya.autodisable");
        //i.setAction("ACTION_DOWNLOAD_PENDING");
        i.putExtra("NetworkAvailability", connStatus());
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

    public boolean connStatus() {
        if(netInfo != null && netInfo.isConnected())
            return netInfo.isConnected();
        else
            return false;
    }

}
