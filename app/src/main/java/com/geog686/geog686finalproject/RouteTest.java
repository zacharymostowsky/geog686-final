package com.geog686.geog686finalproject;

import android.content.Context;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.tasks.networkanalysis.PointBarrier;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteParameters;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteResult;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteTask;
import com.esri.arcgisruntime.tasks.networkanalysis.Stop;

import java.util.Arrays;

public class RouteTest {

    RouteParameters routeParameters;
    RouteTask routeTask;

    // Default constructor
    public RouteTest() {
        System.out.println("Created routing object.");
    }

    public boolean testRoute(Context context, TextView textbox){
        System.out.println("In Route test.");
        final String routetTaskSandiego = "http://sampleserver6.arcgisonline.com/arcgis/rest/services/NetworkAnalysis/SanDiego/NAServer/Route";
        // create route task from San Diego service
        routeTask = new RouteTask(context, routetTaskSandiego);
        System.out.println("HEREEEE.");
        // load route task
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
            // set flags to return stops and directions
            routeParameters.setReturnStops(true);
            routeParameters.setReturnDirections(true);
        } catch (Exception e) {
            System.out.println("Error getting default parameters.");
            e.printStackTrace();
        }

        // set stop locations
        SpatialReference ESPG_3857 = SpatialReference.create(102100);
        Point stop1Loc = new Point(-1.3018598562659847E7, 3863191.8817135547, ESPG_3857);
        Point stop2Loc = new Point(-1.3036911787723785E7, 3839935.706521739, ESPG_3857);

        // add route stops
        routeParameters.setStops(Arrays.asList(new Stop(stop1Loc), new Stop(stop2Loc)));


        // create barriers - we probably wont have any of these
        //PointBarrier pointBarrier = new PointBarrier(new Point(-1.302759917994629E7, 3853256.753745117, ESPG_3857));
        // add barriers to routeParameters
        //routeParameters.setPointBarriers(Arrays.asList(pointBarrier));
        //System.out.println("In route Test");

        try {
            final RouteResult result = routeTask.solveRouteAsync(routeParameters).get();
            routeTask.addDoneLoadingListener(new Runnable(){
                @Override public void run(){
                    textbox.setText(result.getRoutes().get(0).getDirectionManeuvers().get(1).getDirectionText());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }


}
