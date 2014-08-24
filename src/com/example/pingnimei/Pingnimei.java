package com.example.pingnimei;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pingnimei.util.FileTool;

public class Pingnimei extends Activity {
	public static final String TAG_PHOTO_PATH = "photo";
	private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;
	private static final int REQUEST_CODE_PICK_IMAGE = REQUEST_CODE_CAPTURE_CAMEIA + 1;
	
	private String mPhotoPath;
	private Uri mUriPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingnimei);

		ViewPager pager = (ViewPager) findViewById(R.id.content_pager);
		final ContentPagerAdapter adapter = new ContentPagerAdapter(this);
		adapter.setViewPager(pager);
		pager.setAdapter(adapter);
		
		NetworkManager.getInstance().queryAllPings(new NetworkManager.PingsHandler() {

			@Override
			public void onSuccess(List<PingMessage> pings) {
				adapter.setPings(pings);
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int code, String msg) {
				
			}
		});
	}

	protected void getPhotoFromCamera() {
		Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
		mPhotoPath = FileTool.getImagePath() + Calendar.getInstance().getTimeInMillis()
				+ ".jpg";
		File image = new File(mPhotoPath);
		try {
			if (image.exists() == false) {
				image.getParentFile().mkdirs();
				image.createNewFile();
			}
		} catch (IOException e) {
			Log.e("camera", "Could not create file.", e);
		}
		mUriPhoto = Uri.fromFile(image);
		getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhoto);
		startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
	}

	protected void getPhotoFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent = new Intent(this, PingPublishActivity.class);
		if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
			mUriPhoto = data.getData();
			mPhotoPath = FileTool.getFilePathFromUri(this, mUriPhoto);
			intent.putExtra(TAG_PHOTO_PATH, mPhotoPath);
			startActivity(intent);
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA && resultCode == RESULT_OK) {
			intent.putExtra(TAG_PHOTO_PATH, mPhotoPath);
			startActivity(intent);
		} 
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
			showDialogToChosePhoto();
		}
		default: {
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private void showDialogToChosePhoto() {
		new AlertDialog.Builder(this).setTitle("选择照片")
		.setItems(new String[] { "相册", "拍照" }, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					getPhotoFromAlbum();
				} else if (which == 1) {
					getPhotoFromCamera();
				}
			}

		}).show();		
	}
}
