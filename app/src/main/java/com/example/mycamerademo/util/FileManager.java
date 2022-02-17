package com.example.mycamerademo.util;

import android.text.TextUtils;

import com.cz.jcamera.util.file.FileUtil;
import com.cz.jcamera.util.file.StorageUtils;
import com.example.mycamerademo.App;

import java.io.File;


/**
 * 文件管理
 *
 * @author wuczh
 * @date 2021/8/9
 */
public class FileManager {

    private FileManager() {
    }

    /**
     * 存储卡是否可用
     */
    private static boolean isStorageCanUse = false;
    /**
     * app主文件夹路径
     */
    private static String mAppFolderPath = null;
    /**
     * 缓存路径
     */
    private static String mCacheFolderPath = null;

    public static void init() {
        initPath();
        if (isStorageCanUse) {
            initFolder();
        }
    }

    /**
     * 初始化路径
     */
    private static void initPath() {
        String rootPath = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            File file = App.get().getExternalFilesDir("");
            if (file != null) {
                rootPath = file.getAbsolutePath();
            }
        }
        if (TextUtils.isEmpty(rootPath)) {
            rootPath = StorageUtils.getInternalStoragePath(App.get());// 先获取内置存储路径
            if (TextUtils.isEmpty(rootPath)) {// 内置为空再获取外置
                rootPath = StorageUtils.getExternalStoragePath(App.get());
            }
        }

        if (TextUtils.isEmpty(rootPath)) {// 没有存储卡
            isStorageCanUse = false;
            return;
        }
        // 成功获取到存储路径
        isStorageCanUse = true;
        if (!rootPath.endsWith(File.separator)) {
            rootPath += File.separator;
        }
        mAppFolderPath = rootPath + "MyCameraDemo" + File.separator;// 主文件夹路径
        mCacheFolderPath = mAppFolderPath + "Cache" + File.separator;// 缓存路径
    }

    /**
     * 初始化文件夹
     */
    private static void initFolder() {
        try {
            FileUtil.createFolder(mAppFolderPath);// 主文件夹路径
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储是否可用
     */
    public static boolean isStorageCanUse() {
        return isStorageCanUse;
    }

    /**
     * 获取app主文件夹路径
     */
    public static String getAppFolderPath() {
        return fixPath(mAppFolderPath);
    }

    /**
     * 获取缓存路径
     */
    public static String getCacheFolderPath() {
        return fixPath(mCacheFolderPath);
    }

    /**
     * 修复文件夹路径
     *
     * @param path 文件夹路径
     */
    private static String fixPath(String path) {
        if (TextUtils.isEmpty(path)) {
            // 路径为空说明未初始化
            init();
        }
        if (isStorageCanUse && !FileUtil.isFileExists(path)) {
            //存储可用 && 路径下的文件夹不存在 说明文件夹被删除
            initFolder();
        }
        return path;
    }
}
