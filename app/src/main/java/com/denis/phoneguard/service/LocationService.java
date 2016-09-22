package com.denis.phoneguard.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * 获取地理位置的信息
 * 
 * @author yongda
 *
 */
public class LocationService extends Service {
	@Override
	public void onCreate() {
		super.onCreate();
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = lm.getBestProvider(criteria, false);
		lm.requestLocationUpdates(locationProvider, 2000, 50, new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {
				
			}

			@Override
			public void onLocationChanged(Location location) {
				double latitude = location.getLatitude();
				double longtide = location.getLongitude();
				Log.v("Shortcut", "latitude:"+latitude+";longtidue:"+longtide);
				stopSelf();
			}
		});
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
