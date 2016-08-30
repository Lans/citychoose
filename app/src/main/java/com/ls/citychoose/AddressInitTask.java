package com.ls.citychoose;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * 获取地址数据并显示地址选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @version 2015/12/15
 */
public class AddressInitTask extends AsyncTask<String, Void, ArrayList<cn.qqtheme.framework.picker.AddressPicker.Province>> {
    private Activity activity;
    private ProgressDialog dialog;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideCounty = false;

    private MyAddressPicker MyAddressPicker;

    /**
     * 初始化为不显示区县的模式
     *
     * @param activity
     * @param hideCounty is hide County
     */
    public AddressInitTask(Activity activity, boolean hideCounty, MyAddressPicker MyAddressPicker) {
        this.MyAddressPicker = MyAddressPicker;
        this.activity = activity;
        this.hideCounty = hideCounty;
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    public AddressInitTask(Activity activity, MyAddressPicker MyAddressPicker) {
        this.MyAddressPicker = MyAddressPicker;
        this.activity = activity;
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    @Override
    protected ArrayList<cn.qqtheme.framework.picker.AddressPicker.Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<cn.qqtheme.framework.picker.AddressPicker.Province> data = new ArrayList<cn.qqtheme.framework.picker.AddressPicker.Province>();
        try {
            String json = AssetsUtils.readText(activity, "city.json");
            data.addAll(JSON.parseArray(json, cn.qqtheme.framework.picker.AddressPicker.Province.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<cn.qqtheme.framework.picker.AddressPicker.Province> result) {
        dialog.dismiss();
        if (result.size() > 0) {
            cn.qqtheme.framework.picker.AddressPicker picker = new cn.qqtheme.framework.picker.AddressPicker(activity, result);
            picker.setHideCounty(hideCounty);
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(new cn.qqtheme.framework.picker.AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(cn.qqtheme.framework.picker.AddressPicker.Province province, cn.qqtheme.framework.picker.AddressPicker.City city, cn.qqtheme.framework.picker.AddressPicker.County county) {
                    if (county == null) {
                        Toast.makeText(activity, "province : " + province + ", city: " + city, Toast.LENGTH_LONG).show();
                        MyAddressPicker.getAddress(province, city, null);
                    } else {
                        Toast.makeText(activity, "province : " + province + ", city: " + city + ", county: " + county, Toast.LENGTH_LONG).show();
                        MyAddressPicker.getAddress(province, city, county);
                    }
                }
            });
            picker.show();
        } else {
            Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

}
