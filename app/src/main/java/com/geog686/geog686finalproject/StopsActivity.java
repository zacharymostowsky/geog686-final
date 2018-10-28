package com.geog686.geog686finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class StopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        MenuItem map = (MenuItem) findViewById(R.id.nav3);
        /*map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StopsActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
    }


    public void launchStops(MenuItem menuItem) {
        Intent intent = new Intent(StopsActivity.this, StopsActivity.class);
        startActivity(intent);
    }
    public void launchMap(MenuItem menuItem) {
        Intent intent = new Intent(StopsActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
