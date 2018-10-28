package com.geog686.geog686finalproject;

import android.content.Context;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.loadable.LoadStatus;
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
    private static SpatialReference EPSG_3857; // will need to change EPSG
    private static ArrayList<Stop> stops;
    private static RouteResult routeResult;
    private static ArrayList<String> directions = new ArrayList<String>();

    public static boolean resetRoute(Context context){
        final String routeTaskWorld = "https://route.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World";
        // Create route task from the World service
        routeTask = new RouteTask(context, routeTaskWorld);
        // Load route task
        //routeTask.loadAsync();
        routeTask.addDoneLoadingListener(() -> {
            if (routeTask.getLoadError() == null && routeTask.getLoadStatus() == LoadStatus.LOADED) {
                // route task has loaded successfully
                System.out.println("RouteTask loaded successfully.");
            }
            else{
                System.out.println("FAILED loading RouteTask.");
            }
        });

        try {
            // get default route parameters
            routeParameters = routeTask.createDefaultParametersAsync().get();
            System.out.println("Got default parameters for RouteTask.");
            // set flags to return stops and directions
            routeParameters.setReturnStops(true);
            routeParameters.setReturnDirections(true);
        } catch (Exception e) {
            System.out.println("Error getting default parameters for RouteTask.");
            e.printStackTrace();
        }

        EPSG_3857 = SpatialReference.create(102100);
        stops = new ArrayList<Stop>();

        return true;
    }

    public static boolean addStop(double lat, double lon){
        // Create
        Point stopLoc = new Point(lat, lon, EPSG_3857);
        stops.add(new Stop(stopLoc));
        return true;
    }

    public static boolean removeStop(){
        // TODO - implement
        return true;
    }

    public static boolean runRoute(Context context, TextView textbox){

        // add route stops
        routeParameters.setStops(stops);

        try {
            routeResult = routeTask.solveRouteAsync(routeParameters).get();
            routeTask.addDoneLoadingListener(new Runnable(){
                @Override public void run(){
                    // Need to set the list in other activity.
                    ArrayList<DirectionManeuver> route = (ArrayList) routeResult.getRoutes().get(0).getDirectionManeuvers();
                    directions = new ArrayList<String>();
                    for(int i = 0; i < route.size(); i++){
                        directions.add(route.get(i).getDirectionText());
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public static ArrayList<String> getDirections(){
        return directions;
    }


}
