package com.aapico.powermap.aacproject;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends LifecycleActivity {

    private static final String TAG = MainActivity.class.toString();
    private MyLocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        locationManager = new MyLocationManager(this, getLifecycle(), new MyLocationManager.MyLocationListener() {
//            @Override
//            public void onLocationChange(Location location) {
//                Log.d(TAG, location.toString());
//            }
//        });
        MainActivityPermissionsDispatcher.enableLocationWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION})
    void enableLocation() {
//        locationManager.locationEnable();
        MyLocationManagerWithLiveData locationManagerWithLiveData =
                new MyLocationManagerWithLiveData(MainActivity.this);
        locationManagerWithLiveData.observe(MainActivity.this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                Log.d(TAG, location.toString());
            }
        });
    }

    @OnShowRationale({Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION})
    void showDenied() {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
