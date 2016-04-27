package com.example.androidautoinstall;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

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
        return Environment.getExternalStorageDirectory().toString() + File.separator;
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        File cacheDir = context.getCacheDir();//文件�?��目录为getFilesDir();
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

}
