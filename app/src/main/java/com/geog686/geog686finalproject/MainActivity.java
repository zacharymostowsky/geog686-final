package com.geog686.geog686finalproject;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends android.support.v4.app.FragmentActivity implements OnMapReadyCallback{

    private GoogleMap google_map;
    // Can optionally use an ArrayList to store these 4 markers
    private Marker place1, place2, place3, place4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapSearch(View view) {
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
            google_map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            google_map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public void onNormalMap(View view) {
        google_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onSatelliteMap(View view) {
        google_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void onTerrainMap(View view) {
        google_map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }


    public void onHybridMap(View view) {
        google_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void place1(View view){
        google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(), 15.0f));
    }
    public void place2(View view){
        google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(place2.getPosition(), 15.0f));
    }
    public void place3(View view){
        google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(place3.getPosition(), 15.0f));
    }
    public void place4(View view){
        google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(place4.getPosition(), 15.0f));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        google_map = map;
        place1 = google_map.addMarker(new MarkerOptions().position(new LatLng(25.953442, -80.126916)).title("Florida Apartment").snippet("A home away from home.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        place2 = google_map.addMarker( new MarkerOptions().position(new LatLng(25.948343, -80.148199)).title("Mario the Baker").snippet("The best Garlic Knots on the East Coast.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        place3 = google_map.addMarker(new MarkerOptions().position(new LatLng(25.957159, -80.143188)).title("Aventura Mall").snippet("A huge mall for all your shopping needs.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        place4 = google_map.addMarker( new MarkerOptions().position(new LatLng(25.818531, -80.122287)).title("LIV Night Club").snippet("Home to fun nightlife!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(), 15.0f));
    }
}
