package com.example.pingnimei;

import android.app.Application;

public class PingnimeiApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		NetworkManager.getInstance(this);
	}

}
