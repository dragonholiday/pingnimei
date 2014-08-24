package com.example.pingnimei;

import java.util.List;

import android.content.Context;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class NetworkManager {

	private static String APPID = "0323f2613a0835e16f1128a219c5142d";

	private static NetworkManager mInstance;

	private Context mContext;

	public static NetworkManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NetworkManager(context);
		}

		return mInstance;
	}

	public static NetworkManager getInstance() {
		if (mInstance == null) {
			return null;
		}

		return mInstance;
	}

	private NetworkManager(Context context) {
		mContext = context;

		Bmob.initialize(mContext, APPID);
	}

	public void queryAllPings(PingsHandler handler) {
		final PingsHandler tempHandler = handler;
    	BmobQuery<PingMessage> bmobQuery = new BmobQuery<PingMessage>();
    	bmobQuery.findObjects(mContext, new FindListener<PingMessage>() {
			
			@Override
			public void onSuccess(List<PingMessage> pings) {
				tempHandler.onSuccess(pings);
			}

			@Override
			public void onError(int code, String msg) {
				tempHandler.onError(code, msg);
			}
		});
    }

	public interface PingsHandler {
		public void onSuccess(List<PingMessage> pings);

		public void onError(int code, String msg);
	}
}
