package br.com.example.eduarena.testeviewpager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.example.eduarena.testeviewpager.adapter.PagerAdapterAoRedor;
import br.com.example.eduarena.testeviewpager.model.Coordinates;
import br.com.example.eduarena.testeviewpager.monitoring.MonitoringConnections;


public class MainActivity extends FragmentActivity {

    private final static String TAG = "MainActivity";

    private PagerAdapterAoRedor mAdapterAoRedor;
    private ViewPager pager;
    private Boolean isGPSEnabled = false;
    private Boolean isNetworkEnabled = false;

    ArrayList<Integer> listEstab = new ArrayList<Integer>();

    public static final String SHARED_PREFERENCES_NAME = "my_preferences";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListEstablishments();
        setupViewPager();
        setupListeners();
        controlNetworkConnections();

    }

    private void populateListEstablishments(){
        listEstab.add(0, 1);
        listEstab.add(1, 2);
        listEstab.add(2, 3);
        listEstab.add(3, 4);
        listEstab.add(4, 5);
    }

    private void setupViewPager(){
        pager = (ViewPager) findViewById(R.id.viewpager);
        mAdapterAoRedor = new PagerAdapterAoRedor(this,listEstab);
        pager.setAdapter(mAdapterAoRedor);
    }

    private void setupListeners(){

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i(TAG, "LOG onPageScrolled " + position);

            }

            @Override
            public void onPageSelected(int position) {

                Log.i(TAG, "LOG onPageSelected " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.i(TAG, "LOG onPageScrollStateChanged");

            }
        });

    }

    public void controlNetworkConnections(){

        MonitoringConnections mc = new MonitoringConnections(getApplicationContext());

        Boolean isNetworkEnabled = mc.isConnectingToInternet();

        Boolean isGPSEnabled = mc.isGPSActive();

        if(isNetworkEnabled) {
            Toast.makeText(this, "Conexão Ativa!", Toast.LENGTH_SHORT).show();
        }else{
            AlertConnections("Internet", "Verifique sua conexão!", "Settings", "Close");
        }

        if(isGPSEnabled){
            Toast.makeText(this,"GPS Ativo!", Toast.LENGTH_SHORT).show();
        }else{
            showSettingsAlertGPS();
        }

    }

    private void savePreferencesLocation(){

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();

        //Save it as a float since SharedPreferences can't deal with doubles
        /*edit.putFloat(LATITUDE, (float) Location.getLatitude());
        edit.putFloat(LONGITUDE, (float) Location.getLongitude());
        edit.commit();*/
    }

    private void getPreferencesLocation(){
        SharedPreferences prefsDetails = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        /*double lat = (double)prefs.getFloat(LATITUDE, 0);
        double lon = (double)prefs.getFloat(LONGITUDE, 0);*/
    }

    private void getLocation(){
        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();


        Coordinates coordinates = new Coordinates();

        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
    }

    private void AlertConnections(String title, String message, String titleButtonSetting, String titleButtonCancel){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.alert)
                //.setNeutralButton(titleButton, null)

                //On pressing Settings button
                .setPositiveButton(titleButtonSetting, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                })

                // on pressing cancel button
                .setNegativeButton(titleButtonCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })

                .show();
    }

    public void showSettingsAlertGPS(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
