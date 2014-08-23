package com.example.pingnimei;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Pingnimei extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingnimei);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pingnimei, menu);
		return true;
	}

}
