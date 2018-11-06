package com.geog686.geog686finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        getSupportActionBar().setTitle("Directions");
        listview = (ListView) findViewById(R.id.route_list);

    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<String> directions = Route.getDirections();

        if(directions != null && directions.size() > 0){
            System.out.println("Directions is not null");
            listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, directions));
        }else{
            listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        }
    }

    public void onStopsPressed(View view){
        Intent intent = new Intent(RouteActivity.this, StopsActivity.class);
        startActivity(intent);
    }
    public void onRoutePressed(View view){
        Intent intent = new Intent(RouteActivity.this, RouteActivity.class);
        startActivity(intent);
    }
    public void onMapPressed(View view){
        Intent intent = new Intent(RouteActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
