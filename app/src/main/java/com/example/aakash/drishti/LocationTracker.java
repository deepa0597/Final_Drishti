package com.example.aakash.drishti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationTracker extends AppCompatActivity
//change
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    TextView tvData;
    Button btnShare;
    GoogleApiClient gac;
    Location loc;

    Button btnTakePic, btnShare2;
    ImageView iv1;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracker);

        tvData = (TextView) findViewById(R.id.tvData);
        btnShare = (Button) findViewById(R.id.btnShare);

      /*  btnTakePic = (Button) findViewById(R.id.btnTakePic);
        iv1 = (ImageView) findViewById(R.id.iv1);
        btnShare2 = (Button) findViewById(R.id.btnShare2);*/

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        gac = builder.addApi(AppIndex.API).build();


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save number and send it
              Toast.makeText(LocationTracker.this, tvData.getText(),Toast.LENGTH_LONG).show();
               Intent intent = new Intent ( LocationTracker.this, MainActivityPCR.class );
               intent.putExtra ( "TextBox", tvData.getText().toString() );
                startActivity(intent);
                ChatFragment chatFragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TextBox", tvData.getText().toString());
                chatFragment.setArguments(bundle);


            }
        });

    /*    btnShare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File(getExternalCacheDir(), "p1.png");
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT, tvData.getText().toString());
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                startActivity(i);
            }
        });*/
/*
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 123);
            }
        });*/


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null)
            gac.connect();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (gac != null)
            gac.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc != null)
        {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            // tvData.setText(lat + " " + lon);

            Geocoder g = new Geocoder(this, Locale.ENGLISH);


            try {
                List<Address> la = null;
                la = g.getFromLocation(lat, lon, 1);
                android.location.Address add = la.get(0);
                String msg = add.getCountryName() + " " +add.getAdminArea() + " " + add.getSubAdminArea() + " " + add.getLocality()
                        + " " + add.getSubLocality() + add.getThoroughfare() + " " + add.getSubThoroughfare() + " " + add.getPostalCode();
                tvData.setText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else
        {
            Toast.makeText(this, "Enable GPS/Come in an open area", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        gac.connect();
        AppIndex.AppIndexApi.start(gac, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(gac, getIndexApiAction());
        gac.disconnect();
    }
}

