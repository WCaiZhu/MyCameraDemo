package com.cz.jcamera.view;

import android.graphics.Bitmap;

public interface CameraView {
    /**
     * 重置状态
     *
     * @param type
     */
    void resetState(int type);

    /**
     * 完成状态
     *
     * @param type
     */
    void confirmState(int type);

    /**
     * 展示图片
     *
     * @param bitmap     图片Bitmap
     * @param isVertical 是否竖屏展示
     */
    void showPicture(Bitmap bitmap, boolean isVertical);


    /**
     * 播放视频
     *
     * @param firstFrame 第一帧图片
     * @param url        图片路径
     */
    void playVideo(Bitmap firstFrame, String url);

    /**
     * 停止播放
     */
    void stopVideo();

    /**
     * 设置提示文字
     * @param tip
     */
    void setTip(String tip);

    void startPreviewCallback();

    /**
     * 点击界面
     * @param x
     * @param y
     * @return
     */
    boolean handlerFoucs(float x, float y);
}
