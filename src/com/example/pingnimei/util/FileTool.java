package com.example.pingnimei.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class FileTool {

	public static File createPath(String path) {
		File file = new File(FileTool.getImagePath() + path);
		file.mkdir();
		return file;
	}

	public static File createFile(String fileName) throws Exception {
		File f = new File(fileName);
		f.createNewFile();
		return f;
	}

	public static boolean isExist(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}

	public static void write2SDCARD(String path, String filename, InputStream input) {
		try {
			createPath(path);
			File file = createFile(path + filename);
			OutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte[4 * 1024];
			int line = 0;
			while ((line = input.read(buffer)) != -1) {
				output.write(buffer, 0, line);
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			Log.e("FileUtils", "write2SDCARD throws exception=" + e.toString());
		}
	}

	public static String getFileNameFromURL(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

	// 图片目录
	public static String getImagePath() {
		String root = initRootPath();

		String imagePath = root + "/image/";
		File videoDir = new File(imagePath);
		if (!videoDir.exists()) {
			videoDir.mkdir();
		}
		return imagePath;
	}

	// 保存缓存数据
	public static String getTempPath() {
		String root = initRootPath();

		String tmpPath = root + "/tmp";
		File tmpDir = new File(tmpPath);
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		return tmpPath;
	}

	// 保存更新的apk
	public static String getUpdateApkPath() {
		String root = initRootPath();

		String apkPath = root + "/update";
		File apkDir = new File(apkPath);
		if (!apkDir.exists()) {
			apkDir.mkdir();
		}
		return apkPath;
	}
	
	/**
	 * 通过Uri获得文件的绝对路径
	 * 
	 * @param activity
	 * @param uri
	 * @return
	 */
	public static String getFilePathFromUri(Activity activity, Uri uri) {
		String filepath = uri.toString();
		Uri temUri = Uri.parse(filepath);
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = activity.managedQuery(temUri, proj, null,
				null, null);
		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		return actualimagecursor.getString(actual_image_column_index);
	}

	private static String initRootPath() {
		File sdcardfile = Environment.getExternalStorageDirectory();

		String root = sdcardfile.getPath() + "/pingnimei";
		File rootDir = new File(root);
		if (!rootDir.exists()) {
			rootDir.mkdir();
		}

		return root;
	}

	public static boolean isWebImageHasDownload(String url) {
		return isExist(getImagePath() + getFileNameFromURL(url));
	}

	public static String getTempPathOfWebImage(String url) {
		if (isWebImageHasDownload(url)) {
			return getImagePath() + getFileNameFromURL(url);
		} else {
			return "";
		}
	}

	public static boolean deleteFile(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}

	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static long getFileSize(File f) throws Exception {// 取得文件大小
		long size = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			size = fis.available();
		}
		return size;
	}

	// 递归
	public static long getDirectorySize(File f) throws Exception// 取得文件夹大小
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getDirectorySize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;
	}

	public static String resizeImageFile(String path) throws IOException {
		// 对图片进行压缩
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);// 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = 1;
		if (options.outHeight > options.outWidth) {
			be = (int) (options.outWidth / (float) 400);
		} else {
			be = (int) (options.outHeight / (float) 400);
		}
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
		bitmap = BitmapFactory.decodeFile(path, options);

		// 保存入sdCard
		String temp = FileTool.getImagePath() + Calendar.getInstance().getTimeInMillis() + ".jpg";
		File file2 = new File(temp);
		FileOutputStream out = new FileOutputStream(file2);
		// 比例压缩+质量压缩
		if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
			out.flush();
			out.close();
		}
		return temp;
	}
}