package com.ls.citychoose;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.qqtheme.framework.picker.AddressPicker;

public class MainActivity extends Activity {

    private TextView city_tv;
    private TextView province_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city_tv = (TextView) findViewById(R.id.city_tv);
        province_tv = (TextView) findViewById(R.id.province_tv);
    }

    public void onAddress3Picker(View view) {
        new AddressInitTask(this, true, new MyAddressPicker() {
            @Override
            public void getAddress(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                city_tv.setText(province.getAreaId() + "-------" + city.getAreaId());
            }
        }).execute("北京", "北京");
    }

    public void onAddressPicker(View view) {
        new AddressInitTask(this, new MyAddressPicker() {
            @Override
            public void getAddress(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                province_tv.setText(province.getAreaId() + "-------" + city.getAreaId() + "------------" + county.getAreaId());
            }
        }).execute("北京", "北京", "东城");
    }
}
