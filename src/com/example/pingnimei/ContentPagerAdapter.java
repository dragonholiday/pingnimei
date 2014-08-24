package com.example.pingnimei;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentPagerAdapter extends PagerAdapter {

	private List<PingMessage> mPings;
	private Context mContext;
	private LayoutInflater mInflater;
	private ViewPager mViewPager;

	public ContentPagerAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (mPings == null) {
			return 0;
		}
		return mPings.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View page = mInflater.inflate(R.layout.single_ping_view, mViewPager, false);
		PingMessage pingMsg = mPings.get(position);

		TextView text = (TextView) page.findViewById(R.id.text);
		text.setText(pingMsg.getImageDesribe());

		int imageSize = (int) mContext.getResources().getDimension(R.dimen.preview_image_size);
		ImageView imageView = (ImageView) page.findViewById(R.id.image);
		pingMsg.getImage().loadImageThumbnail(mContext, imageView, imageSize, imageSize);

		TextView locationDesc = (TextView) page.findViewById(R.id.location_decription);
		locationDesc.setText(pingMsg.getLocationDescribe());

		container.addView(page);
		return page;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

	}

	public List<PingMessage> getPings() {
		return mPings;
	}

	public void setPings(List<PingMessage> pings) {
		mPings = pings;
	}

	public ViewPager getViewPager() {
		return mViewPager;
	}

	public void setViewPager(ViewPager viewPager) {
		mViewPager = viewPager;
	}

}
