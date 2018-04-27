package com.dc.framelibrary;

import android.content.Context;

import com.dc.baselibrary.http.EngineCallBack;
import com.dc.baselibrary.http.HttpUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 43497 on 2018/4/27.
 */

public abstract class HttpCallBack<T> implements EngineCallBack{

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //与项目逻辑相关 公用必带的参数
        params.put("app_name","joke_essay");
        params.put("version_name","5.7.0");
        params.put("ac","wifi");
        params.put("device_id","30036118478");
        params.put("device_brand","Xiaomi");
        params.put("update_version_code","5701");
        params.put("manifest_version_code","570");
        params.put("longitude","113.000366");
        params.put("latitude","28.171377");
        params.put("device_platform","android");

        onPreExecute();
    }

    // 开始执行了
    public void onPreExecute(){

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        // data:{"name","darren"}   data:"请求失败"
        T objResult = (T) gson.fromJson(result,
                HttpUtils.analysisClazzInfo(this));
        onSuccess(objResult);
    }

    public abstract void onSuccess(T result);
}
