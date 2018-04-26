package com.dc.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 引擎的规范
 * @author 43497
 * @date 2018/4/19
 */

public interface IHttpEngine {

    /**
     * get请求
     * @param context
     * @param url
     * @param params
     * @param callBack
     */
    void get(Context context, String url, Map<String,Object> params, EngineCallBack callBack);

    /**
     * post请求
     * @param context
     * @param url
     * @param params
     * @param callBack
     */
    void post(Context context, String url, Map<String,Object> params, EngineCallBack callBack);


    //下载文件

    //上传文件

    //https添加证书

}
