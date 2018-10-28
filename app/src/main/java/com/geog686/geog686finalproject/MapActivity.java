package com.geog686.geog686finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;


public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC,
                34.056295, -117.195800, 16);
        mMapView.setMap(map);


        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1:
                        //mTextMessage.setText(R.string.nav1);
                        intent = new Intent(MapActivity.this, StopsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav2:
                        //mTextMessage.setText(R.string.nav2);
                        intent = new Intent(MapActivity.this, RouteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav3:
                        //mTextMessage.setText(R.string.nav3);
                        //nav.setSelectedItemId(R.id.nav3);
                        //item.setChecked(true);
                        //intent = new Intent(MapActivity.this, MapActivity.class);
                        //startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

}
