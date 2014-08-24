package com.example.pingnimei;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;

public class PingPublishActivity extends Activity {

	private ImageView mIvPhoto;
	private EditText mEtDes;
	private EditText mEtTag;

	private String mPhotoPath;

	private ProgressDialog progressDialog;

	private double latitude = 0.0;
	private double longitude = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);

		mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
		mEtDes = (EditText) findViewById(R.id.et_des);
		mEtTag = (EditText) findViewById(R.id.et_tag);

		mPhotoPath = getIntent().getStringExtra(Pingnimei.TAG_PHOTO_PATH);
		Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, null);
		mIvPhoto.setImageBitmap(bitmap);

		mIvPhoto.setOnClickListener(photoClickListener);

		initLocation();
	}

	private void initLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
	}

	private OnClickListener photoClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse("file://" + mPhotoPath), "image/*");
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_publish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_publish: {
				progressDialog = new ProgressDialog(PingPublishActivity.this);

				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage("发布中...");

				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.show();

				publish();
			}
			default: {
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void publish() {
		PingMessage message = new PingMessage();

		message.setImage(new BmobFile(new File(mPhotoPath)));
		message.setImageDesribe(mEtDes.getText().toString());
		message.setLocationDescribe(mEtTag.getText().toString());
		message.setLocation(new BmobGeoPoint(longitude, latitude));

		NetworkManager.getInstance(this).publishPing(new SaveListener() {

			@Override
			public void onSuccess() {
				progressDialog.cancel();
				PingPublishActivity.this.finish();
				Toast.makeText(PingPublishActivity.this, "发布成功^_^", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				progressDialog.cancel();
				Toast.makeText(PingPublishActivity.this, "发布失败~_~", Toast.LENGTH_LONG).show();
			}
		}, message);
	}
}
