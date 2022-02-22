package com.example.mycamerademo.bean;

/**
 * @author Wuczh
 * @date 2022/2/22
 */
public class CaptureBean {
    /**
     * 是否是拍照
     */
    public boolean isPhoto;
    /**
     * 拍照图片路径
     */
    public String imageUrl;
    /**
     * 视频路径
     */
    public String videoPath;
    /**
     * 首帧图
     */
    public String firstFrame;
    /**
     * 视频宽度
     */
    public int videoWidth;
    /**
     * 视频高度
     */
    public int videoHeight;
    /**
     * 录制时长
     */
    public Long totalTime;
}
