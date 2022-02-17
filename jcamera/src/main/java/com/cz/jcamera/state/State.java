package com.cz.jcamera.state;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.cz.jcamera.CameraInterface;


public interface State {

    /**
     * 开始
     * @param holder
     * @param screenProp
     */
    void start(SurfaceHolder holder, float screenProp);

    /**
     * 停止
     */
    void stop();

    /**
     * 聚焦(点击)
     * @param x
     * @param y
     * @param callback
     */
    void foucs(float x, float y, CameraInterface.FocusCallback callback);

    /**
     * 切换摄像头
     * @param holder
     * @param screenProp
     */
    void swtich(SurfaceHolder holder, float screenProp);

    void restart();

    /**
     * 拍照
     */
    void capture();

    /**
     * 录制视频
     * @param surface
     * @param screenProp
     */
    void record(Surface surface, float screenProp);

    /**
     * 停止录制
     * @param isShort  是否录制时间过短
     * @param time     当前录制的时间
     */
    void stopRecord(boolean isShort, long time);

    /**
     * 取消
     * @param holder
     * @param screenProp  宽高比
     */
    void cancle(SurfaceHolder holder, float screenProp);

    /**
     * 确认
     */
    void confirm();

    /**
     *
     * @param zoom
     * @param type  录制/拍照状态
     */
    void zoom(float zoom, int type);

    void flash(String mode);
}
