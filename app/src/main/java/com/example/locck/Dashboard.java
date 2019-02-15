package com.example.locck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat, city,you_are_here;;
    String lat;
    String provider;
    protected double latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private FirebaseAuth mAuth;
    private String user;
    private DatabaseReference myref;

    private ProgressDialog loadingbar;
    LinearLayout l1, l2, l3;
    ImageView i1, i2, i3;
    Button btn;
    EditText userLandmark;
    private Geocoder geocoder;
    private  String address;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_dashboard);

        // Write a message to the database
        mAuth = FirebaseAuth.getInstance ();
        user = mAuth.getCurrentUser ().getUid ();
        myref = FirebaseDatabase.getInstance ().getReference ();

        if(mAuth.getCurrentUser ()== null){
            finish ();;
            startActivity (new Intent (this,Login.class));
        }



        you_are_here = (TextView)findViewById (R.id.here);
        btn = (Button) findViewById (R.id.upload);
        txtLat = (TextView) findViewById (R.id.latlong);
        userLandmark = (EditText)findViewById (R.id.landmark);

        btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                String landmark = userLandmark.getText ().toString ().trim ();
                String addres = address;


                if(TextUtils.isEmpty (landmark))
                {
                    Toast.makeText (Dashboard.this, "Please Enter The landmark To continue", Toast.LENGTH_SHORT).show ();
                }else {

                    txtLat.setVisibility (View.INVISIBLE);
                    userLandmark.setVisibility (View.INVISIBLE);
                    btn.setVisibility (View.INVISIBLE);
                    you_are_here.setVisibility (View.INVISIBLE);

                    User_address user_address = new User_address (landmark,addres);
                    FirebaseUser user = mAuth.getCurrentUser ();

                    myref.child (user.getUid ()).setValue (user_address);

                    Toast.makeText (Dashboard.this, "Address Saved", Toast.LENGTH_SHORT).show ();


                }

            }
        });


        LocationManager locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 30000, 0, this);




        Criteria criteria = new Criteria ();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location == null) {
            Toast.makeText(getApplicationContext(), "GPS signal not found",
                    3000).show();
        }
        if (location != null) {
            Log.e("locatin", "location--" + location);

            Log.e("latitude at beginning",
                    "@@@@@@@@@@@@@@@" + location.getLatitude());
            onLocationChanged(location);
        }



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



    }


    @Override
    public void onLocationChanged(Location location) {

        List<Address> addresses;

        geocoder = new Geocoder(this, Locale.getDefault());

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.e("latitude", "latitude--" + latitude);

        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);


            if (addresses != null && addresses.size() > 0) {
                 address = addresses.get(0).getAddressLine(0);
                //String city = addresses.get(0).getLocality();
                //String state = addresses.get(0).getAdminArea();
                //String country = addresses.get(0).getCountryName();
                //String postalCode = addresses.get(0).getPostalCode();
                //String knownName = addresses.get(0).getFeatureName();


                txtLat.setText(address + " " /*+ city + " " */);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


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


    /*Exit Button*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

}




