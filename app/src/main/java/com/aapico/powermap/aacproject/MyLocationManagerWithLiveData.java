package com.aapico.powermap.aacproject;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

/**
 * Copyright 2017-present, AAPICO ITS Co., Ltd. All rights reserved.
 * <p>
 * Project      : [SC#A] SmartCity - Amata
 * Author       : teepop.r@aapico.com
 * Date Create  : 12/7/2016 AD
 **/
public class MyLocationManagerWithLiveData extends LiveData<Location> implements LocationListener {

    private LocationManager locationManager;

    public MyLocationManagerWithLiveData(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, this, Looper.getMainLooper());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, this, Looper.getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        setValue(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
