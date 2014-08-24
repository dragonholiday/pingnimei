package com.example.pingnimei;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class PingMessage extends BmobObject {
	private BmobFile image;
	private String imageDesribe;
	private BmobGeoPoint location;
	private String locationDescribe;

	// 已经附带创建日期createdAt
	// 更新日期uptimeAt

	public BmobFile getImage() {
		return image;
	}

	public void setImage(BmobFile image) {
		this.image = image;
	}

	public String getImageDesribe() {
		return imageDesribe;
	}

	public void setImageDesribe(String imageDesribe) {
		this.imageDesribe = imageDesribe;
	}

	public BmobGeoPoint getLocation() {
		return location;
	}

	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}

	public String getLocationDescribe() {
		return locationDescribe;
	}

	public void setLocationDescribe(String locationDescribe) {
		this.locationDescribe = locationDescribe;
	}
}
