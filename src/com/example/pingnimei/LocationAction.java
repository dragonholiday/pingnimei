
package com.example.pingnimei;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Context;

public class LocationAction {

    private static LocationAction mInstance;

    private LocationClient mLocationClient;
    private BDLocation mLocation;

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            mLocation = arg0;
        }
    };

    public static LocationAction getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocationAction(context);
        }

        return mInstance;
    }

    public static LocationAction getInstance() {
        return mInstance;
    }

    private LocationAction(Context context) {
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(mListener);
        setLocOption();
        mLocationClient.start();
    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    private void setLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
//        option.setCoorType("gcj02");
//        option.setScanSpan(500);// 定位失败时，定位间隔为500ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public BDLocation getLocation() {
        return mLocation;
    }

}
