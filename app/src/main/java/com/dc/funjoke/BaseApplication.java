package com.dc.funjoke;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.dc.baselibrary.fixbug.FixDexManager;
import com.dc.framelibrary.ExceptionCrashHandler;

/**
 * @author 43497
 * @date 2018/4/2
 */

public class BaseApplication extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionCrashHandler.getInstance().init(this);
/*

        //初始化阿里的热修复
        mPatchManager = new PatchManager(this);
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            //初始化版本,获取当前应用的版本
            mPatchManager.init(versionName);
            //加载之前的apatch包
            mPatchManager.loadPatch();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
*/

        //初始化自己的热修复
        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            // 加载所有修复的Dex包
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
