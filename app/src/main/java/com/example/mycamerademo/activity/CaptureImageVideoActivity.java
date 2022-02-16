package com.example.mycamerademo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import com.cz.jcamera.JCameraView;
import com.cz.jcamera.listener.ClickListener;
import com.cz.jcamera.listener.ErrorListener;
import com.cz.jcamera.listener.JCameraListener;
import com.cz.jcamera.util.file.FileUtil;
import com.example.mycamerademo.R;
import com.example.mycamerademo.cnofig.Constant;

/**
 * 如果是聊天界面跳转到此界面，遵循下面两个要求
 * a)拍照：
 * 1、拍完直接发送：
 * wifi：发送原图
 * 否则：发送标清图
 * b)短视频：
 * 拍完直接发送。
 */
public class CaptureImageVideoActivity extends Activity {

    /**
     * 按钮类型
     */
    public static final String EXTRA_BUTTON_STATE = "extra_button_state";

    private JCameraView jCameraView;
    private String firstF = "";//首帧图
    private String videoUrl = "";//视频路径
    private String imageUrl = "";//拍路径
    private boolean isPhoto = false;//是否是照片
    private int[] videoSize = new int[2];//视频尺寸
    private PowerManager.WakeLock mWakeLock;
    /**
     * 按钮类型
     */
    private int mButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_video);
        initView();
        addListener();
    }

    private void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏

        mButtonState = getIntent().getIntExtra(EXTRA_BUTTON_STATE, JCameraView.BUTTON_STATE_ONLY_CAPTURE);
        jCameraView = findViewById(R.id.jCameraView);
        //设置视频保存路径，默认路径Environment.getExternalStorageDirectory().getPath()
        jCameraView.setSaveVideoPath(Constant.VIDEO_PATH);
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(mButtonState);
        //设置提示文字
        jCameraView.setTip(getTipMsg());
        //设置视频质量
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);

    }

    private String getTipMsg() {
        switch (mButtonState) {
            case JCameraView.BUTTON_STATE_ONLY_CAPTURE:
                return "轻触拍照";
            case JCameraView.BUTTON_STATE_ONLY_RECORDER:
                return "长按录像";
            case JCameraView.BUTTON_STATE_BOTH:
                return "轻触拍照，长按摄像";
        }
        return "轻触拍照";
    }

    private void addListener() {
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {//拍照成功
                isPhoto = true;
                //拍照成功后获取图片bitmap
                Log.i("ID", "bitmap = " + bitmap.getWidth());
                imageUrl = FileUtil.saveBitmap(Constant.PICTURE_PATH, bitmap);
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame, int[] vSize) {
                //获取成功录像后的视频路径
                isPhoto = false;
                firstF = FileUtil.saveBitmap(Constant.PICTURE_PATH, firstFrame);
                videoUrl = url;
                videoSize = vSize;
            }
        });
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
                Log.i("ID", "camera error");
            }

            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                Log.i("ID", "AudioPermissionError");
            }
        });
        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        jCameraView.setConfirmClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent();
                intent.putExtra("isPhoto", isPhoto);
                if (isPhoto) {
                    intent.putExtra("imageUrl", imageUrl);
                } else {
                    intent.putExtra("videoPath", videoUrl);
                    intent.putExtra("firstFrame", firstF);
                    intent.putExtra("videoWidth", videoSize[0]);
                    intent.putExtra("videoHeight", videoSize[1]);
                    intent.putExtra("totalTime", jCameraView.recordTime / 1000);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onResume() {
        super.onResume();
        if (jCameraView.getScreenCnt() == 0 || !jCameraView.isCapEndPreview()) {
            jCameraView.onResume();
        } else if (jCameraView.getScreenCnt() > 0) {
            jCameraView.onScreenOn();
        }
        if (mWakeLock == null) {
            //获取唤醒锁,保持屏幕常亮
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "CaptureImageVideoActivity");
            mWakeLock.acquire();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (jCameraView.isRecording()) {
            jCameraView.onRecordingScreenOff();
        } else if (jCameraView.isCapEndPreview()) {
            jCameraView.onCaptureScreenOff();
        } else {
            jCameraView.onPause();
        }
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jCameraView.onDestroy();
    }
}
