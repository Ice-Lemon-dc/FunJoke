package com.dc.funjoke;

import android.app.Application;

import com.dc.framelibrary.ExceptionCrashHandler;

/**
 * @author 43497
 * @date 2018/4/2
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionCrashHandler.getInstance().init(this);

    }
}
