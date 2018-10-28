package com.geog686.geog686finalproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class StopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1:
                        //mTextMessage.setText(R.string.nav1);
                        //intent = new Intent(StopsActivity.this, StopsActivity.class);
                        //startActivity(intent);
                        return true;
                    case R.id.nav2:
                        //mTextMessage.setText(R.string.nav2);
                        intent = new Intent(StopsActivity.this, RouteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav3:
                        //mTextMessage.setText(R.string.nav3);
                        //nav.setSelectedItemId(R.id.nav3);
                        //item.setChecked(true);
                        intent = new Intent(StopsActivity.this, MapActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    public void onAddStop(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;


        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }

        // Create a stoop object and add it to the list corresponding to the view
    }

}
