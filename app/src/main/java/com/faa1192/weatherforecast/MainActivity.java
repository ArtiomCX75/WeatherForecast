package com.faa1192.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
public String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);

     //   NetworkHelper nh = new NetworkHelper();
     //   nh.act = this;
      //  nh.execute();
       // ListAdapter la = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, City.citiesList);
   //     ListView lv = getListView();
      //  lv.setAdapter(la);

    }


  /*  public void updateData(View view) {
        NetworkHelper nh = new NetworkHelper();
        nh.act = this;
        nh.execute();
    }*/
}
