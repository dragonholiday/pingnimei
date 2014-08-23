package com.example.pingnimei;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class PingPublishActivity extends Activity {

	private ImageView mIvPhoto;
	private EditText mEtDes;
	private EditText mEtTag;

	private String mPhotoPath;

	private ProgressDialog progressDialog;

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

				progressDialog.show();
			}
			default: {
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
