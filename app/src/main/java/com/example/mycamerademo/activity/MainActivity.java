package com.example.mycamerademo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cz.jcamera.JCameraView;
import com.cz.jcamera.util.file.FileUtil;
import com.example.mycamerademo.R;
import com.example.mycamerademo.bean.CaptureBean;
import com.example.mycamerademo.util.CaptureModuleUtil;
import com.lxj.xpermission.PermissionConstants;
import com.lxj.xpermission.XPermission;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    /**
     * 进行播放
     */
    public static final int REQUEST_PLAY_VIDEO = 2;


    private static final String TAG = "MainActivity";

    /**
     * 图片
     */
    private ImageView mImgPic;
    /**
     * 播放视频
     */
    private ImageView mIbPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgPic = findViewById(R.id.img_pic);
        mIbPlay = findViewById(R.id.ib_play);
    }


    /**
     * 拍照
     *
     * @param view
     */
    public void startCapture(View view) {
        capture(JCameraView.BUTTON_STATE_ONLY_CAPTURE);
    }

    /**
     * 录像
     *
     * @param view
     */
    public void startRecord(View view) {
        capture(JCameraView.BUTTON_STATE_ONLY_RECORDER);
    }


    /**
     * 拍照/录像
     *
     * @param view
     */
    public void startCaptureRecord(View view) {
        capture(JCameraView.BUTTON_STATE_BOTH);
    }


    /**
     * 播放视频
     *
     * @param videoPath
     */
    private void palyVideo(String videoPath) {
        mIbPlay.setVisibility(View.VISIBLE);
        mIbPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
            intent.putExtra(PlayVideoActivity.EXTRA_VIDEO_PATH, videoPath);
            startActivityForResult(intent, REQUEST_PLAY_VIDEO);
        });
    }

    /**
     * @param buttonState 按钮类型
     */
    @SuppressLint("WrongConstant")
    public void capture(int buttonState) {
        String[] permissions = new String[]{PermissionConstants.CAMERA, PermissionConstants.MICROPHONE, PermissionConstants.STORAGE, PermissionConstants.MICROPHONE};

        XPermission.create(this, permissions).callback(new XPermission.SimpleCallback() {
            @Override
            public void onGranted() {
                CaptureModuleUtil.startCaptureImageVideo(MainActivity.this, buttonState);
            }

            @Override
            public void onDenied() {
                Toast.makeText(MainActivity.this, "没有权限，无法使用该功能", Toast.LENGTH_SHORT).show();
            }
        }).request();
    }


    /**
     * 设置图片
     */
    private void setPic(String path) {

        Glide.with(this)
                .load(new File(path))
                .centerCrop()
                .into(mImgPic);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CaptureModuleUtil.isCaptureVideoResult(requestCode, resultCode)) {
            CaptureBean captureBean = CaptureModuleUtil.getCaptureVideoResultBean(data);
            if (captureBean.isPhoto) {
                if (FileUtil.isNotEmpty(captureBean.imageUrl)) {
                    //TODO 获取到图片
                    Toast.makeText(this, "图片文件：" + captureBean.imageUrl, Toast.LENGTH_SHORT).show();
                    setPic(captureBean.imageUrl);
                    mIbPlay.setVisibility(View.GONE);
                }
                return;
            }
            if (FileUtil.isNotEmpty(captureBean.videoPath)) {
                //TODO 获取视频
                Toast.makeText(this, "视频文件：" + captureBean.videoPath, Toast.LENGTH_SHORT).show();
                mImgPic.setVisibility(View.VISIBLE);
                mIbPlay.setVisibility(View.VISIBLE);
                setPic(captureBean.videoPath);
                palyVideo(captureBean.videoPath);
            }

        }

        if (requestCode == REQUEST_PLAY_VIDEO) {
            mImgPic.setVisibility(View.GONE);
            mIbPlay.setVisibility(View.GONE);
        }
    }

}
