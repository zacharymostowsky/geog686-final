package com.geog686.geog686finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;

import java.util.ArrayList;


public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private GraphicsOverlay graphicsOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setTitle("Mapped Route");
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud9148674549,none,0JMFA0PL4PY9RJE15164");
        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.OPEN_STREET_MAP,
                34.056295, -117.195800, 16);
        mMapView.setMap(map);

        graphicsOverlay = addGraphicsOverlay(mMapView);

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

            // Add markers with stops
            ArrayList<StopItem> stopItems = StopItem.getStopItems();
            // create a point marker symbol (red, size 10, of type circle)
            SimpleMarkerSymbol simpleMarker = new SimpleMarkerSymbol();
            simpleMarker.setColor(Color.RED);
            simpleMarker.setSize(10);
            simpleMarker.setStyle(SimpleMarkerSymbol.Style.CIRCLE);
            System.out.println(stopItems.size());
            for(int i=0; i < stopItems.size(); i++){
                //add a new graphic with the same location as the initial viewpoint
                Point pinStarBluePoint = new Point(stopItems.get(i).getLon(), stopItems.get(i).getLat(), SpatialReferences.getWgs84());
                Graphic pointGraphic = new Graphic(pinStarBluePoint, simpleMarker);
                graphicsOverlay.getGraphics().add(pointGraphic);

                TextSymbol stopText = new TextSymbol(10, Integer.toString(i+1), Color.WHITE, TextSymbol.HorizontalAlignment.CENTER,
                        TextSymbol.VerticalAlignment.MIDDLE);
                graphicsOverlay.getGraphics().add(new Graphic(pinStarBluePoint, stopText));

            }



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
    public void onStopsPressed(View view){
        Intent intent = new Intent(MapActivity.this, StopsActivity.class);
        startActivity(intent);
    }
    public void onRoutePressed(View view){
        Intent intent = new Intent(MapActivity.this, RouteActivity.class);
        startActivity(intent);
    }
    public void onMapPressed(View view){
        Intent intent = new Intent(MapActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
