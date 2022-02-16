package com.example.mycamerademo.cnofig;


import com.example.mycamerademo.util.FileManager;

import java.io.File;

/**
 * 常量
 *
 * @author wuczh
 * @date 2021/8/9
 */
public class Constant {

    /**
     * 图片存储路径
     */
    public static final String PICTURE_PATH = FileManager.getCacheFolderPath()+ File.separator+"picture";
    /**
     * 视频存储路径
     */
    public static final String VIDEO_PATH = FileManager.getCacheFolderPath()+ File.separator+"video";


}
