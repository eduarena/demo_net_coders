package br.com.example.eduarena.testeviewpager.monitoring;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by edu.arena on 22/07/2015.
 */
public class MonitoringConnections {

    private Context context;
    private Boolean isGPSEnabled = false;

    public MonitoringConnections(){}

    public MonitoringConnections(Context context){ this.context = context; }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    public boolean isGPSActive(){
        try {

            //GPS available on the device
            PackageManager pm = context.getPackageManager();
            Boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION);

            //getting GPS status
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Boolean isGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(hasGps) {
                if (isGPS) {
                    isGPSEnabled = true;
                }
            }else{
                Toast.makeText(context, "Not available GPS in your a device!", Toast.LENGTH_SHORT).show();
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }

        return isGPSEnabled;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}