package com.faa1192.weatherforecast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
public String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHelper nh = new NetworkHelper();
        nh.act = this;
        nh.execute();
    }

    public void updateText(View v){
        ((TextView) findViewById(R.id.hello)).setText(text);
    }

    public void updateData(View view) {
        NetworkHelper nh = new NetworkHelper();
        nh.act = this;
        nh.execute();
    }
}
