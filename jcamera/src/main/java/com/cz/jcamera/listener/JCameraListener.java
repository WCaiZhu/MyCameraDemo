package com.cz.jcamera.listener;

import android.graphics.Bitmap;

public interface JCameraListener {

    /**
     * 拍照成功
     *
     * @param bitmap
     */
    void captureSuccess(Bitmap bitmap);

    /**
     * 录制成功
     *
     * @param url        视频存储路径
     * @param firstFrame 第一帧图片
     * @param videoSize  视频尺寸
     */
    void recordSuccess(String url, Bitmap firstFrame, int[] videoSize);

}
