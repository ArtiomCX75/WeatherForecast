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
        PrefCursorCity pcc = new PrefCursorCity();
        pcc.updateData(getApplicationContext());
        for(int i = 0; i < 10_000_000; i++){}
        setContentView(R.layout.activity_pref_container);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), AddCityActivity.class);
                startActivity(intent);
            }
        };
        ((Button) findViewById(R.id.add_city_button)).setOnClickListener(ocl);
    }
}
