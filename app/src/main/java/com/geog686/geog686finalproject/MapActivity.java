package com.geog686.geog686finalproject;

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

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav1:
                    mTextMessage.setText(R.string.nav1);
                    return true;
                case R.id.nav2:
                    mTextMessage.setText(R.string.nav2);
                    return true;
                case R.id.nav3:
                    mTextMessage.setText(R.string.nav3);
                    return true;
            }
            return false;
        }
    };

}
