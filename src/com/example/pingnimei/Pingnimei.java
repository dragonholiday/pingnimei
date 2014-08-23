package com.example.pingnimei;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class Pingnimei extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingnimei);

		ViewPager pager = (ViewPager) findViewById(R.id.content_pager);
		pager.setAdapter(new ContentPagerAdapter());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pingnimei, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.publish_msg: {

		}
		default: {
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}
