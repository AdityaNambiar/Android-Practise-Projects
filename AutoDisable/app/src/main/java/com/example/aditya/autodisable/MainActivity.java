package com.example.aditya.autodisable;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.OnClickListener;
import static com.example.aditya.autodisable.R.color.colorAccent;
import static com.example.aditya.autodisable.R.color.colorGreen;

/* BUG: 1. The "Network status" displays "Unknown" always unless you follow a standard procedure as follows:
             - Make sure you haven't switched on the network connection
             - Open the app
             - Press the button (most probably not required but still...
                                because it most likely changes only the Download Status?)
             - Then you should be getting the different network statuses (ON/OFF)
           Now, ideally just for the sake of this app, it's fine. But my actual idea was that the
           app should automatically detect the network status on start of the app. Not like after I
           start the app and then make changes to the network connection..

   PROBLEM/TASK:
        1. There's no direct implementation of disabling mobile network connectivity.
        -- There is! Use stuff from SubscriptionManager or something (Telephony stuff in Android)
        2. After I agreed and implemented things to work on disabling Wifi, I found out that the
            cursor DOES NOT return the correct results... Or maybe I am doing something wrong..

*/


public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView netStat;
    TextView downloadStat;

    BroadcastReceiver bNetReceiver, bDownloadReceiver;
    IntentFilter itf;

    DownloadManager dm = null;
    DownloadManager.Query dmquery;
    Cursor c;
    DatabaseUtils cContent;
    WifiManager wm;
    //TelephonyManager tm;

    //NotificationCompat.Builder nfct;
    //private final static int uniqueID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performAct();
            }
        });


        //nfct = new NotificationCompat.Builder(this,"Notify");
        //nfct.setAutoCancel(true);

        downloadStat = (TextView) findViewById(R.id.downloadStat);
        // Display the Downloads pending status (YES/NO):
        try {
            if (dmquery == null) {
                dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                dmquery = new DownloadManager.Query();

                Toast.makeText(this, "dmquery is null BUT ASSIGNED NOW, dmquery: "+dmquery, Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this, "dmquery is not null, dmquery: "+dmquery, Toast.LENGTH_LONG).show();

            c = dm.query(dmquery.setFilterByStatus(
                    DownloadManager.STATUS_PENDING
            ));
            Log.e("CDUMP","cursor(if only <<<<<<< comes, then c is null):\n "+ DatabaseUtils.dumpCursorToString(c));
            if(c.moveToFirst()) {
                Toast.makeText(this, "Count is > 0 also T2 (numRows): "+c.getCount(), Toast.LENGTH_LONG).show();

                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch(status){
                    case DownloadManager.STATUS_RUNNING:
                        Toast.makeText(this,"status running",Toast.LENGTH_LONG).show();
                        break;

                    case DownloadManager.STATUS_PAUSED:
                        Toast.makeText(this,"status paused",Toast.LENGTH_LONG).show();
                        break;

                    case DownloadManager.STATUS_SUCCESSFUL:
                        Toast.makeText(this,"status successful",Toast.LENGTH_LONG).show();
                        break;

                    case DownloadManager.STATUS_PENDING:
                        Toast.makeText(this,"status pending",Toast.LENGTH_LONG).show();
                        break;

                }
                downloadStat.setText("YES");
                downloadStat.setTextColor(getResources().getColor(colorAccent, getTheme()));

                c.close();
            }
            else{
                Toast.makeText(this,"Count is 0 also T3(numRows): "+c.getCount(), Toast.LENGTH_LONG).show();
                downloadStat.setText("NO");
                downloadStat.setTextColor(getResources().getColor(colorGreen, getTheme()));

                // For Mobile Data connectivity:
                //tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                //tm.setDataEnabled(false);

                // For Wifi connectivity:
                wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wm.disconnect();

                c.close();
            }

        }
        catch(NullPointerException e){
            Log.e("CURSOR ERROR", "ERROR");
            e.printStackTrace();
            if (!c.moveToFirst()) {
                Toast.makeText(this,"Count is 0 also T4: "+c.moveToFirst(), Toast.LENGTH_LONG).show();
                downloadStat.setText("NO");
                downloadStat.setTextColor(getResources().getColor(colorGreen, getTheme()));
            }

            // For Mobile Data connectivity:


            // For Wifi connectivity:
            wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wm.disconnect();

            c.close();
        }

    }


    private void performAct() { // This method works when I tap on the button.
        // Can be helpful if there's a download going on and the below broadcast receiver gets triggered.
        netStat = (TextView) findViewById(R.id.netStat);
        downloadStat = (TextView) findViewById(R.id.downloadStat);
        itf = new IntentFilter(NetworkConnCheckReceiver.ACTIVITY);



        // The below broadcast receiver will be "triggered" when the downloads complete
        // (See why I put the "ACTION_DOWNLOAD_COMPLETE" for the intentFilter?)

        bDownloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    //String myAction = intent.getAction();

                    downloadStat.setText("NO");
                    downloadStat.setTextColor(getResources().getColor(colorGreen, getTheme()));



                    // For Wifi connectivity:
                    wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    wm.disconnect();
            }
        };
        // Display the network status (ON\OFF):
        bNetReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetAvailable = intent.getBooleanExtra("NetworkAvailability",false);
                if(isNetAvailable) {
                    netStat.setText("ON");
                    netStat.setTextColor(getResources().getColor(colorGreen, getTheme()));
                }else {
                    netStat.setText("OFF");
                    netStat.setTextColor(getResources().getColor(colorAccent, getTheme()));
                }

            }
        };


        // Registering broadcast receivers:
        LocalBroadcastManager.getInstance(this).registerReceiver(bNetReceiver,itf);
        LocalBroadcastManager.getInstance(this).registerReceiver(bDownloadReceiver, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE
                )
        );



        /*// First we build the notification
        nfct.setSmallIcon(R.drawable.ic_launcher);
        nfct.setTicker("Ticker arrived!");
        nfct.setWhen(System.currentTimeMillis());
        nfct.setContentTitle("New Notification!");
        nfct.setContentText("Notification text");

        Intent it = new Intent(this, MainActivity.class);
        PendingIntent pdit = PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
        nfct.setContentIntent(pdit);

        // Issue nfct
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,nfct.build());


    */}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bNetReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bDownloadReceiver);
    }
}
