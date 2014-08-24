package com.example.pingnimei;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SinglePingView extends RelativeLayout {

	// 用于计算地图上两点距离
	private final double EARTH_RADIUS = 6378137.0;
	
	private Context mContext;

	public SinglePingView(Context context) {
		this(context, null);
	}

	public SinglePingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SinglePingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mContext = context;
	}
	
	public void updateView(PingMessage pingMsg) {
		TextView textView = (TextView) findViewById(R.id.text);
		if (textView != null) {
			textView.setText(pingMsg.getImageDesribe());
		}

		int imageSize = (int) mContext.getResources().getDimension(R.dimen.preview_image_size);
		ImageView imageView = (ImageView) findViewById(R.id.image);
		if (imageView != null) {
			pingMsg.getImage().loadImageThumbnail(mContext, imageView, imageSize, imageSize);
		}

		TextView locationDesc = (TextView) findViewById(R.id.location_decription);
		if (locationDesc != null) {
			locationDesc.setText(pingMsg.getLocationDescribe());
		}
	}

	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}
