package com.geog686.geog686finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        listview = (ListView) findViewById(R.id.route_list);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1:
                        //mTextMessage.setText(R.string.nav1);
                        intent = new Intent(RouteActivity.this, StopsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav2:
                        //mTextMessage.setText(R.string.nav2);
                        //intent = new Intent(RouteActivity.this, RouteActivity.class);
                        //startActivity(intent);
                        return true;
                    case R.id.nav3:
                        //mTextMessage.setText(R.string.nav3);
                        //nav.setSelectedItemId(R.id.nav3);
                        //item.setChecked(true);
                        intent = new Intent(RouteActivity.this, MapActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<String> directions = Route.getDirections();

        if(directions != null && directions.size() > 0){
            System.out.println("Directions is not null");
            listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, directions));
        }
    }
}
