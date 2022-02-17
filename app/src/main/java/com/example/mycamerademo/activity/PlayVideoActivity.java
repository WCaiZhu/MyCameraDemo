package com.example.mycamerademo.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cz.jcamera.util.file.FileUtil;
import com.example.mycamerademo.R;

import java.io.IOException;

/**
 * 播放视频
 */
public class PlayVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    /**
     * 视频路径
     */
    public static final String EXTRA_VIDEO_PATH = "extra_video_path";

    /**
     * 视频路径
     */
    private String mVideoPath;

    private MediaPlayer mMediaPlayer;

    private SurfaceView mSurfaceView;

    private RelativeLayout mRvTitle;
    private TextView mTvBack;
    private TextView mTvDelete;

    private SurfaceHolder surfaceHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paly_video);

        initView();
        setListner();
        /**
         * 本地播放
         */
        mVideoPath = getIntent().getStringExtra(EXTRA_VIDEO_PATH);
        if (TextUtils.isEmpty(mVideoPath)) {
            return;
        }


    }

    /**
     * 初始界面
     */
    private void initView() {
        mSurfaceView = findViewById(R.id.sfv_show);
        mRvTitle = findViewById(R.id.rv_title);
        mTvBack = findViewById(R.id.tv_back);
        mTvDelete = findViewById(R.id.tv_delete);
        //初始化SurfaceHolder类，SurfaceView的控制器
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    /**
     * 设置点击
     */
    private void setListner() {
        mSurfaceView.setOnClickListener(v -> mRvTitle.setVisibility(mRvTitle.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));

        //返回
        mTvBack.setOnClickListener(v -> finish());

        //删除
        mTvDelete.setOnClickListener(v -> showMsgDialog());

    }


    public void showMsgDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayVideoActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定要删除这段视频吗?");
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            if (FileUtil.deleteFile(mVideoPath)) {
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        builder.setNegativeButton("取消", null);

        builder.create().show();
    }


    /**
     * 播放视频
     *
     * @param url
     */
    private void playVideo(final String url) {
        new Thread(() -> {
            try {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                } else {
                    mMediaPlayer.reset();
                }
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.setDisplay(surfaceHolder);
                mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnVideoSizeChangedListener((mp, width, height) -> updateVideoViewSize(mMediaPlayer.getVideoWidth(), mMediaPlayer
                        .getVideoHeight()));
                mMediaPlayer.setOnPreparedListener(mp -> mMediaPlayer.start());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateVideoViewSize(float videoWidth, float videoHeight) {
        if (videoWidth > videoHeight) {
            FrameLayout.LayoutParams videoViewParam;
            int height = (int) ((videoHeight / videoWidth) * getScreenWidth(this));
            videoViewParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
            videoViewParam.gravity = Gravity.CENTER;
            mSurfaceView.setLayoutParams(videoViewParam);
        }
    }


    /**
     * 释放资源
     */
    private void stopVideo() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVideo();
    }


    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        playVideo(mVideoPath);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
