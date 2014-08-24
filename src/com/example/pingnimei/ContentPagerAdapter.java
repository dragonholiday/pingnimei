package com.example.pingnimei;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ContentPagerAdapter extends PagerAdapter {

	private List<PingMessage> mPings;

	@Override
	public int getCount() {
		return 0;
//		return mPings.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return false;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		return super.instantiateItem(container, position);
	}

	public List<PingMessage> getPings() {
		return mPings;
	}

	public void setPings(List<PingMessage> pings) {
		mPings = pings;
	}

}
