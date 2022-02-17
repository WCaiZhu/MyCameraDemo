package com.cz.jcamera.listener;

public interface CaptureListener {
    /**
     * 拍照
     */
    void takePictures();

    /**
     * 回调录制时间过短
     *
     * @param time 录制时间
     */
    void recordShort(long time);

    /**
     * 开始录制
     */
    void recordStart();

    /**
     * 录制结束
     *
     * @param time 当前录制的时间
     */
    void recordEnd(long time);

    /**
     * 录制时的缩放
     * @param zoom
     */
    void recordZoom(float zoom);

    /**
     * 没有录音权限时报错
     */
    void recordError();
}
