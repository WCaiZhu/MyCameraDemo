package com.cz.jcamera.listener;

import android.graphics.Bitmap;

public interface JCameraListener {

    void captureSuccess(Bitmap bitmap);

    /**
     * @param url        视频存储路径
     * @param firstFrame 第一帧图片
     * @param videoSize  视频尺寸
     */
    void recordSuccess(String url, Bitmap firstFrame, int[] videoSize);

}
