package com.example.locck;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class Dashboard extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat, city;
    String lat;
    String provider;
    protected double latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    private ProgressDialog loadingbar;
    LinearLayout l1, l2, l3;
    ImageView i1, i2, i3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_dashboard);

        city = (TextView) findViewById (R.id.city);
        txtLat = (TextView) findViewById (R.id.latlong);



        locationManager = (LocationManager) getSystemService (Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0, this);



        l1 = (LinearLayout) findViewById (R.id.linear1);
        l2 = (LinearLayout) findViewById (R.id.linear2);
        l3 = (LinearLayout) findViewById (R.id.linear3);

        i1 = (ImageView) findViewById (R.id.image1);
        i2 = (ImageView) findViewById (R.id.image2);
        i3 = (ImageView) findViewById (R.id.image3);


        l1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Dashboard.this, Book.class);
                intent.putExtra ("image", R.drawable.locktype1);
                startActivity (intent);
            }
        });

        l2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Dashboard.this, Book.class);
                intent.putExtra ("image", R.drawable.locktype2);
                startActivity (intent);
            }
        });

        l3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Dashboard.this, Book.class);
                intent.putExtra ("image", R.drawable.locktype3);
                startActivity (intent);
            }
        });

        /*fetchLocationData ();*/



    }


    @Override
    public void onLocationChanged(Location location) {


        txtLat = (TextView) findViewById(R.id.latlong);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");


    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }


}



    /*LocationManager locationManager;
    public void fetchLocationData()
    {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            return;
        }
        Toast.makeText(this,"Location is triggered from here acc",Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Toast.makeText(this,"Toast call is triggered from abc",Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        loadingbar.setTitle ("Please Wait");
        loadingbar.setMessage ("Please wait, while we authenticate your location");
        loadingbar.setCanceledOnTouchOutside (false);
        loadingbar.show ();

        Toast.makeText (this,"location recieved"+location.getLatitude ()+","+location.getLongitude (),Toast.LENGTH_LONG).show();
        Log.e ("Location fetched","fetched");
        LatLng mPyos=new LatLng (location.getLatitude(),location.getLongitude());


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText (this,"Location status changed",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText (this,"provider enabled",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText (this,"Location disabled",Toast.LENGTH_LONG).show();
    }*/

