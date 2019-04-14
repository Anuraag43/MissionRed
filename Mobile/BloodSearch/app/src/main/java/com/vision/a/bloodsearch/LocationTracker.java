package com.vision.a.bloodsearch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationTracker extends Service {

	private static final int ONE_MINUTE = 1000 * 60 * 1;
	public LocationManager locationManager;
	public LocationTrackerListener listener;
	boolean isNetworkEnabled = false;
	boolean isGPSEnabled = false;
	static String lati,longi;
	boolean login;
	String busNo = "";

	@Override
	public void onCreate() {
		super.onCreate();
	}
	


	@Override
	public void onStart(Intent intent, int startId) {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lati = "";
		longi = "";
		listener = new LocationTrackerListener();
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (isNetworkEnabled) {

			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, ONE_MINUTE, 0, listener);
		}
		if (isGPSEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, ONE_MINUTE, 0, listener);
		}
		login = intent.getBooleanExtra("login", false);
		busNo = intent.getStringExtra("busnumber");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(listener);
	}

	public class LocationTrackerListener implements LocationListener {

		public void onLocationChanged(final Location loc) {
			lati = loc.getLatitude()+"";
			longi = loc.getLongitude()+"";
			
			Log.v("LocationTracker", "latitude:"+loc.getLatitude()+" longitude:"+loc.getLongitude() );
			String location = loc.getLatitude()+","+loc.getLongitude();

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}
	


}
