package com.cz.jcamera.state;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.cz.jcamera.CameraInterface;
import com.cz.jcamera.view.CameraView;

/**
 * 相机所处状态
 */
public class CameraMachine implements State {
    private final String TAG = "CameraMachine";

    private Context context;
    private State state;
    private CameraView view;
//    private CameraInterface.CameraOpenOverCallback cameraOpenOverCallback;

    private State previewState;       //浏览状态
    private State borrowPictureState; //拍照处理状态
    private State borrowVideoState;   //录制视频处理状态

    private int[] videoSize = new int[]{0,0};

    public CameraMachine(Context context, CameraView view, CameraInterface.CameraOpenOverCallback cameraOpenOverCallback) {
        this.context = context;
        previewState = new PreviewState(this);
        borrowPictureState = new BorrowPictureState(this);
        borrowVideoState = new BorrowVideoState(this);
        //默认设置为空闲状态
        this.state = previewState;
//        this.cameraOpenOverCallback = cameraOpenOverCallback;
        this.view = view;
    }

    public CameraView getView() {
        return view;
    }

    public Context getContext() {
        return context;
    }

    public void setState (State state) {
        this.state = state;
    }

    //获取浏览图片状态
    State getBorrowPictureState() {
        return borrowPictureState;
    }

    //获取浏览视频状态
    State getBorrowVideoState() {
        return borrowVideoState;
    }

    //获取空闲状态
    State getPreviewState() {
        return previewState;
    }

    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        Log.i(TAG,getState()+">>start....");
        state.start(holder, screenProp);
    }

    @Override
    public void stop() {
        Log.i(TAG,getState()+">>stop....");
        state.stop();
    }

    @Override
    public void foucs(float x, float y, CameraInterface.FocusCallback callback) {
        Log.i(TAG,getState()+">>foucs....");
        state.foucs(x, y, callback);
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {
        Log.i(TAG,getState()+">>swtich....");
        state.swtich(holder, screenProp);
    }

    @Override
    public void restart() {
        Log.i(TAG,getState()+">>restart....");
        state.restart();
    }

    @Override
    public void capture() {
        Log.i(TAG,getState()+">>capture....");
        state.capture();
    }

    @Override
    public void record(Surface surface, float screenProp) {
        Log.i(TAG,getState()+">>record....");
        state.record(surface, screenProp);
    }

    @Override
    public void stopRecord(boolean isShort, long time) {
        Log.i(TAG,getState()+">>stopRecord....");
        state.stopRecord(isShort, time);
    }

    @Override
    public void cancle(SurfaceHolder holder, float screenProp) {
        Log.i(TAG,getState()+">>cancle....");
        state.cancle(holder, screenProp);
    }

    @Override
    public void confirm() {
        Log.i(TAG,getState()+">>confirm....");
        state.confirm();
    }


    @Override
    public void zoom(float zoom, int type) {
        Log.i(TAG,getState()+">>zoom....");
        state.zoom(zoom, type);
    }

    @Override
    public void flash(String mode) {
        Log.i(TAG,getState()+">>flash....");
        state.flash(mode);
    }

    public State getState() {
        return this.state;
    }

    public int[] getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(int[] videoSize) {
        this.videoSize = videoSize;
    }
}
