package com.geog686.geog686finalproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.TextView;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.DirectionManeuver;
import com.esri.arcgisruntime.tasks.networkanalysis.PointBarrier;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteParameters;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteResult;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteTask;
import com.esri.arcgisruntime.tasks.networkanalysis.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Route {

    private static RouteParameters routeParameters;
    private static RouteTask routeTask;
    private static SpatialReference EPSG_37001; // will need to change EPSG
    private static List<Stop> stops;
    private static RouteResult routeResult;
    private static ArrayList<String> directions = new ArrayList<String>();
    private static Graphic routeGraphic = null;

    public static Graphic getRouteGraphic() {
        return routeGraphic;
    }

    public static void setRouteGraphic(Graphic routeGraphic) {
        Route.routeGraphic = routeGraphic;
    }

    public static ArrayList<String> getDirections() {
        return directions;
    }

    public static void setDirections(ArrayList<String> directions) {
        Route.directions = directions;
    }

    public static boolean run(Context context, ArrayList<StopItem> stopItems) {
        String LOG_TAG = "TEST";
        final String routeTaskWorld = "http://sampleserver6.arcgisonline.com/arcgis/rest/services/NetworkAnalysis/SanDiego/NAServer/Route";
        // Create route task from the World service
        routeTask = new RouteTask(context, routeTaskWorld);
        // Load route task
        routeTask.loadAsync();
        routeTask.addDoneLoadingListener(() -> {
            if (routeTask.getLoadError() == null && routeTask.getLoadStatus() == LoadStatus.LOADED) {
                // route task has loaded successfully
                Log.d(LOG_TAG, "RouteTask loaded successfully.");
                try {
                    // get default route parameters
                    routeParameters = routeTask.createDefaultParametersAsync().get();
                    Log.d(LOG_TAG,"Got default parameters for RouteTask.");
                    // set flags to return stops and directions
                    routeParameters.setReturnStops(true);
                    routeParameters.setReturnDirections(true);
                } catch (Exception e) {
                    Log.d(LOG_TAG,"Error getting default parameters for RouteTask.");
                    e.printStackTrace();
                }
                // add route stops
                stops = new ArrayList<Stop>();
                for (int i = 0; i < stopItems.size(); i++) {
                    Point stopLoc = new Point(stopItems.get(i).getLon(), stopItems.get(i).getLat(), SpatialReferences.getWgs84()); // , SpatialReference.create(102100)
                    stops.add(new Stop(stopLoc));
                }
                routeParameters.setStops(stops);

                Log.d(LOG_TAG, "Size of stops: " + routeParameters.getStops().size());
                Log.d(LOG_TAG, "Stops set");
                final ListenableFuture<RouteResult> routeResultFuture = routeTask.solveRouteAsync(routeParameters);
                routeResultFuture.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        if (routeResultFuture.isDone()) {
                            Log.d(LOG_TAG, "routeResultFeature is done");
                            try {
                                Log.d(LOG_TAG, "DoneListener inside executeRouteTask");
                                RouteResult routeResult = routeResultFuture.get();
                                Log.d(LOG_TAG, "RouteResult language: " + routeResult.getDirectionsLanguage());

                                // Add directions as static object
                                List<DirectionManeuver> route = routeResult.getRoutes().get(0).getDirectionManeuvers();
                                directions = new ArrayList<String>();
                                for (int i = 0; i < route.size(); i++) {
                                    directions.add(route.get(i).getDirectionText());
                                }

                                // Add to map
                                // Access the whole route geometry and add it as a graphic
                                Geometry routeGeom = routeResult.getRoutes().get(0).getRouteGeometry();
                                SimpleLineSymbol routeLine = new SimpleLineSymbol();
                                routeLine.setColor(Color.BLUE);
                                routeLine.setWidth(3);
                                routeGraphic = new Graphic(routeGeom, routeLine);

                            } catch (Throwable e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Result catch: " + e.getCause());
                            }
                        } else {
                            Log.e(LOG_TAG, "routeResultFeature not done");
                        }
                    }
                });

            } else {
                System.out.println("FAILED loading RouteTask.");
            }
        });


        return true;
    }


}
