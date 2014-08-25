package com.example.pingnimei;

import android.app.Application;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class PingnimeiApplication extends Application {

	private LocationClient mLocationClient;

	@Override
	public void onCreate() {
		super.onCreate();

		NetworkManager.getInstance(this);
		mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
//        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(4000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
	}

    private BDLocationListener mLocationListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d("test", "经度:" + location.getLongitude() + ", 纬度:" + location.getLatitude());
        }
    };

}
