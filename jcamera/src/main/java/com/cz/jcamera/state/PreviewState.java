package com.cz.jcamera.state;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.cz.jcamera.CameraInterface;
import com.cz.jcamera.JCameraView;

/**
 *预览状态(为完成拍照和录制的其他状态)
 */
public class PreviewState implements State {
    public static final String TAG = "PreviewState";

    private CameraMachine machine;

    public PreviewState(CameraMachine machine) {
        this.machine = machine;
    }

    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
    }

    @Override
    public void stop() {
        CameraInterface.getInstance().doStopPreview();
    }


    @Override
    public void foucs(float x, float y, CameraInterface.FocusCallback callback) {
        if (machine.getView().handlerFoucs(x, y)) {
            CameraInterface.getInstance().handleFocus(machine.getContext(), x, y, callback);
        }
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().switchCamera(holder, screenProp);
    }

    @Override
    public void restart() {
    }

    /**
     * 点击拍照按钮后,进行拍照后回调展示照片且设置相机状态为拍照
     */
    @Override
    public void capture() {
        CameraInterface.getInstance().takePicture(new CameraInterface.TakePictureCallback() {
            @Override
            public void captureResult(Bitmap bitmap, boolean isVertical) {
                machine.getView().showPicture(bitmap, isVertical);
                machine.setState(machine.getBorrowPictureState());
                Log.i("","capture");
            }
        });
    }

    @Override
    public void record(Surface surface, float screenProp) {
        CameraInterface.getInstance().startRecord(surface, screenProp, null);
    }

    @Override
    public void stopRecord(final boolean isShort,final long time) {
        CameraInterface.getInstance().stopRecord(isShort, (url, firstFrame) -> {
            if (isShort) {
                machine.getView().resetState(JCameraView.TYPE_SHORT);
            } else {
                machine.getView().playVideo(firstFrame, url);
                machine.setState(machine.getBorrowVideoState());
            }
        });
    }

    @Override
    public void cancle(SurfaceHolder holder, float screenProp) {
//        Log.i("","浏览状态下,没有 cancle 事件");
    }

    @Override
    public void confirm() {
//        Log.i("","浏览状态下,没有 confirm 事件");

    }

    @Override
    public void zoom(float zoom, int type) {
        CameraInterface.getInstance().setZoom(zoom, type);
    }

    @Override
    public void flash(String mode) {
        CameraInterface.getInstance().setFlashMode(mode);
    }
}
