package com.cz.jcamera.util.file;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    private static final String TAG = "ID";

    public static String saveBitmap(String dir, Bitmap b) {
        long dataTake = System.currentTimeMillis();
        String jpegName = dir + File.separator + "picture_" + dataTake + ".jpg";
        try {
            FileUtil.createSavePath(dir);//判断有没有这个文件夹，没有的话需要创建
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 创建文件夹
     *
     * @param filePath 文件夹路径
     */
    public static boolean createFolder(@NonNull String filePath) {
        File folder = createFile(filePath);
        return folder != null && !folder.exists() && folder.mkdirs();
    }


    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean deleteFile(String filePath) {
        boolean result = false;
        File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File item : files) {
                    if (item.isFile()) {
                        item.delete();
                    } else if (item.isDirectory()) {
                        deleteFile(item.getAbsolutePath());
                    }
                }
                result = file.delete();
            } else if (file.isFile()) {
                result = file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断传入的地址是否已经有这个文件夹，没有的话需要创建
     */
    public static void createSavePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 该路径下的文件是否存在
     *
     * @param filePath 文件路径
     */
    public static boolean isFileExists(@NonNull String filePath) {
        return isFileExists(createFile(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }


    /**
     * 创建文件
     *
     * @param filePath 文件路径
     */
    public static File createFile(@NonNull String filePath) {
        try {
            return new File(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * str 不等于:null , "" ,"null" 返回true
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !(str == null || "".equals(str));
    }


}
