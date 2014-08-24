package com.example.pingnimei;

import android.app.Application;

import com.baidu.location.LocationClient;

public class PingnimeiApplication extends Application {

	private LocationClient mLocationClient;

	@Override
	public void onCreate() {
		super.onCreate();

		NetworkManager.getInstance(this);
		mLocationClient = new LocationClient(this);
	}

}
