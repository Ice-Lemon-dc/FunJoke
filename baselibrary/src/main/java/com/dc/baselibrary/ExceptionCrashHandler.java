package com.dc.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 43497 on 2018/4/17.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ExceptionCrashHandler";

    private static ExceptionCrashHandler mInstance;
    private Context mContext;
    //获取系统默认的异常
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        Log.e(TAG, "报异常了");

        //写到本地文件

        String crashFileName = saveInfoToSD(ex);

        cacheCrashFile(crashFileName);

        //上传文件不在这里处理  保存当前文件 等应用再次启动再上传

        //那系统默认处理
        mDefaultExceptionHandler.uncaughtException(t, ex);
    }

    /**
     * 保存获取的软件信息，设备信息和出错信息保存在SDCard中
     *
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        //手机信息 应用信息
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        //崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);

            if (dir.exists()) {
                deleteDir(dir);
            }

            if (!dir.exists()) {
                dir.mkdir();
            }

            try {
                fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息
     *
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            map.put("versionName", packageInfo.versionName);
            map.put("versionCode", packageInfo.versionCode + "");
            map.put("MODEL", Build.MODEL);
            map.put("SDK_INT", Build.VERSION.SDK_INT + "");
            map.put("PRODUCT", Build.PRODUCT);
            map.put("MOBILE_INFO", getMobileInfo());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                //属性都是静态可以传null
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 获取系统未捕获的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                child.delete();
            }
        }
        return true;
    }

    private String getAssignTime(String dateFormatStr){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }

    /**
     * 缓存崩溃日志文件
     * @param fileName
     */
    private void cacheCrashFile(String fileName){
        SharedPreferences sp = mContext.getSharedPreferences("crash",Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME",fileName).commit();
    }

    /**
     * 获取崩溃文件名称
     * @return
     */
    public File getCrashFile(){
        String crashFileName = mContext.getSharedPreferences("crash",Context.MODE_PRIVATE).getString("CRASH_FILE_NAME","");
        return new File(crashFileName);
    }
}

