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
     * 聚焦
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

    void capture();

    void record(Surface surface, float screenProp);

    void stopRecord(boolean isShort, long time);

    void cancle(SurfaceHolder holder, float screenProp);

    void confirm();

    void zoom(float zoom, int type);

    void flash(String mode);
}
