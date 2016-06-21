package com.faa1192.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
public String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

     //   NetworkHelper nh = new NetworkHelper();
     //   nh.act = this;
      //  nh.execute();
       // ListAdapter la = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, City.citiesList);
   //     ListView lv = getListView();
      //  lv.setAdapter(la);

    }

    public void updateText(View v){
        ((TextView) findViewById(R.id.hello)).setText(text);
    }

  /*  public void updateData(View view) {
        NetworkHelper nh = new NetworkHelper();
        nh.act = this;
        nh.execute();
    }*/
}
