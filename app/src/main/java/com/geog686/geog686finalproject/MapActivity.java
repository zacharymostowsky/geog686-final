package com.geog686.geog686finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;


public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private GraphicsOverlay graphicsOverlay;
    private BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.OPEN_STREET_MAP,
                34.056295, -117.195800, 16);
        mMapView.setMap(map);

        graphicsOverlay = addGraphicsOverlay(mMapView);

        nav = (BottomNavigationView) findViewById(R.id.navigation);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1:
                        //item.setChecked(true);
                        intent = new Intent(MapActivity.this, StopsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav2:
                        //item.setChecked(true);
                        intent = new Intent(MapActivity.this, RouteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav3:
                        //item.setChecked(true);
                        intent = new Intent(MapActivity.this, MapActivity.class);
                        startActivity(intent);
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

        // Clear all graphics each time we resume
        graphicsOverlay.getGraphics().clear();

        Graphic routeGraphic = Route.getRouteGraphic();
        if(routeGraphic != null){
            graphicsOverlay.getGraphics().add(routeGraphic);
            mMapView.setViewpointGeometryAsync(routeGraphic.getGeometry());
            // mMapView.setAnimation(new ScaleAnimation());
            mMapView.animate();
        }else{ // TODO: zoom to current position

        }

        // Add markers with Stops



        mMapView.resume();
    }

    private GraphicsOverlay addGraphicsOverlay(MapView mapView) {
        //create the graphics overlay
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        //add the overlay to the map view
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        return graphicsOverlay;
    }

}
