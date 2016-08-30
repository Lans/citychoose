package com.ls.citychoose;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView city_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city_tv = (TextView) findViewById(R.id.city_tv);
    }

    public void onAddress3Picker(View view) {
        new AddressInitTask(this, true, new MyAddressPicker() {
            @Override
            public void getAddress(cn.qqtheme.framework.picker.AddressPicker.Province province, cn.qqtheme.framework.picker.AddressPicker.City city) {
                city_tv.setText(province.getAreaName()+city.getAreaName());
            }
        }).execute("北京", "北京");
    }
}
