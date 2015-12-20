package com.example.tharunkrishna.grocessory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tharun Krishna on 12/19/2015.
 */
public class locationoffarmer extends Activity {

    EditText edtlonglat;
    // GPSTracker class
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        edtlonglat = (EditText) findViewById(R.id.edtlonglat);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void getcurrentlocationuser(View V) {
        gps = new GPSTracker(locationoffarmer.this);
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line

           // edtlonglat.setText("Your Location is - \nLat: " + latitude + "\nLong: " + longitude);

            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude,longitude , 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude+"\nplace: "+cityName, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            edtlonglat.setText(cityName);
            Intent intent=new Intent();
            intent.putExtra("MESSAGE",edtlonglat.getText().toString());
            setResult(2,intent);
            finish();//finishing activity
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
}
