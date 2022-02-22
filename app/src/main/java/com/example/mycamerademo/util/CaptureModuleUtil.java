package com.example.mycamerademo.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.widget.Toast;

import com.example.mycamerademo.R;
import com.example.mycamerademo.activity.CaptureImageVideoActivity;
import com.example.mycamerademo.bean.CaptureBean;

/**
 * @author Wuczh
 * @date 2022/2/22
 */
public class CaptureModuleUtil {

    /**
     * 进行拍照录像 请求码
     */
    private static final int REQUEST_CAPTURE_VIDEO = 1001;
    /**
     * 返回码
     */
    public static final int RESULTCODE_CAPTURE_VIDEO = 1002;
    /**
     * 按钮类型
     */
    public static final String EXTRA_BUTTON_STATE = "extra_button_state";


    /**
     * 打开拍照录像界面
     *
     * @param activity    activity
     * @param buttonState 按钮类型
     * @return
     */
    public static boolean startCaptureImageVideo(Activity activity, int buttonState) {
        Intent intent = new Intent(activity, CaptureImageVideoActivity.class);
        intent.putExtra(EXTRA_BUTTON_STATE, buttonState);
        try {
            activity.startActivityForResult(intent, REQUEST_CAPTURE_VIDEO);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.start_capture_record_fail), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * 是否是拍照结果返回
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @return
     */
    public static boolean isCaptureVideoResult(int requestCode, int resultCode) {
        return requestCode == CaptureModuleUtil.REQUEST_CAPTURE_VIDEO && resultCode == CaptureModuleUtil.RESULTCODE_CAPTURE_VIDEO;
    }

    /**
     * 获取扫描结果数据
     *
     * @param intent onActivityResult的intent
     */
    public static CaptureBean getCaptureVideoResultBean(Intent intent) {
        if (intent == null) {
            return null;
        }
        CaptureBean bean = new CaptureBean();
        bean.isPhoto = intent.getBooleanExtra("isPhoto", false);
        bean.imageUrl = intent.getStringExtra("imageUrl");
        bean.videoPath = intent.getStringExtra("videoPath");
        bean.firstFrame = intent.getStringExtra("firstFrame");
        bean.videoWidth = intent.getIntExtra("videoWidth", 0);
        bean.videoHeight = intent.getIntExtra("videoHeight", 0);
        bean.totalTime = intent.getLongExtra("totalTime", 0);
        return bean;
    }

}
