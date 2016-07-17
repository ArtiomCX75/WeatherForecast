package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefCursorCity.updateData(getApplicationContext());
        for(int i = 0; i < 10_000_000; i++){}
        setContentView(R.layout.activity_pref_container);
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), AddCityActivity.class);
                startActivityForResult(intent, 1);
            }
        };
        ((Button) findViewById(R.id.add_city_button)).setOnClickListener(addListener);
        View.OnClickListener refreshListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        };
        ((Button) findViewById(R.id.refresh_button)).setOnClickListener(refreshListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
