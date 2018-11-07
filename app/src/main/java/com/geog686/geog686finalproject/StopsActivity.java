package com.geog686.geog686finalproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopsActivity extends AppCompatActivity {

    private ListView listview;
    ArrayAdapter<String> adapter;

    // Need to save the activity state when we leave so we are able to reload it.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        getSupportActionBar().setTitle("Enter Stops");
        System.out.println("Back in Stops");
        listview = (ListView) findViewById(R.id.stop_list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StopItem.getStopItems().remove(position);
                ArrayList<String> stopStrings = StopItem.getStopNames();
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stopStrings);
                listview.setAdapter(adapter);
            }
        });
        initRouteButton();
        initResetButton();
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Resuming Stops");
        ArrayList<String> stopStrings = StopItem.getStopNames();
        final ArrayList<StopItem> stopItems = StopItem.getStopItems();

        if(stopStrings != null && stopStrings.size() > 0){
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stopStrings);
            listview.setAdapter(adapter);


        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }



    public void onAddStop(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;
        System.out.println("Adding stop...");
        if (location != null || !location.equals("")) {
            System.out.println("Location is not null...");
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                Address address = addressList.get(0);
                // Use latLng to add to map
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                System.out.println("Got the address...");
                // Create a StopItem object and add it to the list of stops
                StopItem newStopItem = new StopItem(address.getLatitude(), address.getLongitude(), location);
                StopItem.getStopItems().add(newStopItem);
                // System.out.println("Stop Item Length: " + stopItems.size());
                // System.out.println("Lat: " + stopItems.get(stopItems.size()-1).getLat() + "  Lon: " + stopItems.get(stopItems.size()-1).getLon());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, StopItem.getStopNames());
                listview.setAdapter(adapter);

                locationSearch.setText("");

            } catch (IOException e) {
                // Make a toast telling user we couldn't find the address
                System.out.println("Couldn't geocode address.");
                e.printStackTrace();
            }

        }
    }

    public void onStopsPressed(View view){
        Intent intent = new Intent(StopsActivity.this, StopsActivity.class);
        startActivity(intent);
    }
    public void onRoutePressed(View view){
        Intent intent = new Intent(StopsActivity.this, RouteActivity.class);
        startActivity(intent);
    }
    public void onMapPressed(View view){
        Intent intent = new Intent(StopsActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void initRouteButton(){
        Button routeButton = (Button) findViewById(R.id.route_button);
        routeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Route.run(StopsActivity.this, StopItem.getStopItems());
            }
        });
    }
    private void initResetButton(){
        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StopItem.setStopItems(new ArrayList<StopItem>());
                Route.setRouteGraphic(null);
                Route.setDirections(null);
                StopItem.setStopItems(new ArrayList<>());
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
                listview.setAdapter(adapter);
            }
        });
    }

}
