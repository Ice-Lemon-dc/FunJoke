package com.dc.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 引擎的规范
 *
 * @author 43497
 * @date 2018/4/19
 */

public interface IHttpEngine {

    /***
     * get请求
     * @param isCache 是否进行缓存和先调用缓存的数据
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    void get(boolean isCache, Context context, String url, Map<String, Object> params, EngineCallBack callback);

    /***
     * post请求
     * @param isCache 是否进行缓存和先调用缓存的数据
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    void post(boolean isCache, Context context, String url, Map<String, Object> params, EngineCallBack callback);

    //下载文件

    //上传文件

    //https添加证书

}
