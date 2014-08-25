
package com.example.pingnimei;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Context;

public class LocationAction {
    private LocationClient mLocationClient;

    private BDLocationListener mListener = null;

    public LocationAction(Context context, BDLocationListener listener) {
        mListener = listener;
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(listener);
        setLocOption();
    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void stopLocation() {
        mLocationClient.unRegisterLocationListener(mListener);
        mLocationClient.stop();
    }

    private void setLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("gcj02");
        option.setScanSpan(500);// 定位失败时，定位间隔为500ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

}
