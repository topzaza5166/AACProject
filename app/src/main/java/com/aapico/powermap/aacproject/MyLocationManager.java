package com.aapico.powermap.aacproject;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

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
public class MyLocationManager implements LifecycleObserver, LocationListener {

    private boolean enabled = false;
    private Lifecycle lifecycle;
    private MyLocationListener listener;
    private LocationManager locationManager;

    interface MyLocationListener {
        void onLocationChange(Location location);
    }

    public MyLocationManager(Context context, Lifecycle lifecycle, MyLocationListener listener) {
        this.listener = listener;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

    void locationEnable() {
        if (!enabled) {
            enabled = true;
            start();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        if (enabled) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                        10f, this, Looper.getMainLooper());
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,
                        10f, this, Looper.getMainLooper());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        locationManager.removeUpdates(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup() {
        lifecycle.removeObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void doAfterStateOnCreate() {
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            // ทำงานหลังจาก Activity/Fragment/Service ทำงานผ่าน onCreate() ไปแล้ว
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        listener.onLocationChange(location);
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
