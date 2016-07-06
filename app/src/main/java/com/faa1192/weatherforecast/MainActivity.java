package com.faa1192.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends FragmentActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_container);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefCity pc = new PrefCity("1sd212", 222, "fff");
                Toast.makeText(getApplicationContext(), pc.toString(), Toast.LENGTH_LONG).show();
                Intent intent =  new Intent(getApplicationContext(), AddCityActivity.class);
                startActivity(intent);
            }
        };
        ((Button) findViewById(R.id.add_city_button)).setOnClickListener(ocl);
    }
}
