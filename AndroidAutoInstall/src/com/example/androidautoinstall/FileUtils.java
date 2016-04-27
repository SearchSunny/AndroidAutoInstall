package com.example.androidautoinstall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Created by yuyh on 2015/11/4.
 */
public class FileUtils {

	/**
	 * �?��SD卡是否存�?
	 * 
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isSdcardReady() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	/**
	 * 获得SD路径
	 * 
	 * @return
	 */
	public static String getSdcardPath() {
		return Environment.getExternalStorageDirectory().toString()
				+ File.separator;
	}

	/**
	 * 获取缓存路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getCachePath(Context context) {
		File cacheDir = context.getCacheDir();// 文件�?��目录为getFilesDir();
		return cacheDir.getPath() + File.separator;
	}

	/**
	 * 根据文件路径 递归创建文件
	 * 
	 * @param file
	 */
	public static void createDipPath(String file) {
		String parentFile = file.substring(0, file.lastIndexOf("/"));
		File file1 = new File(file);
		File parent = new File(parentFile);
		if (!file1.exists()) {
			parent.mkdirs();
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 请求ROOT权限后执行命令（最好开启一个线程）,可适用于真机
	 * 
	 * @param cmd (pm install -r *.apk)
	 *            
	 * @return
	 */
	public static boolean runRootCommand(String cmd) {
		Process process = null;
		DataOutputStream os = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp + "\n");
				if ("Success".equalsIgnoreCase(temp)) {
					Log.e("","----------" + sb.toString());
					return true;
				}
			}
			process.waitFor();
		} catch (Exception e) {
			Log.e("","异常：" + e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
				if (br != null) {
					br.close();
				}
				process.destroy();
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

}
