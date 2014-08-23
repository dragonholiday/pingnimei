package com.example.pingnimei;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManager {

	private static NetworkManager mInstance;

	private Context mContext;
	private RequestQueue mRequestQueue;

	public static NetworkManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NetworkManager(context);
		}

		return mInstance;
	}

	private NetworkManager(Context context) {
		mContext = context;

		mRequestQueue = Volley.newRequestQueue(mContext);
	}

}
