package com.example.mycamerademo;

import android.app.Application;

import com.example.mycamerademo.util.FileManager;


/**
 * @author Wuczh
 * @date 2021/12/27
 */
public class App extends Application {

    private static App sInstance;

    public static App get() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        FileManager.init();
    }
}
