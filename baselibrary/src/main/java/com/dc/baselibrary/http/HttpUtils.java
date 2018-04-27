package com.dc.baselibrary.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *  自己的一套实现
 * @author 43497
 * @date 2018/4/19
 */

public class HttpUtils{

    //URL
    private String mUrl;
    //请求方式
    private int mType = GET_TYPE;
    public static final int POST_TYPE = 0X0011;
    public static final int GET_TYPE = 0X0012;

    private Map<String, Object> mParams;

    private Context mContext;
    private HttpUtils(Context context){
        mContext = context;
        mParams = new HashMap<>();
    }
    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    public HttpUtils url(String url){
        this.mUrl = url;
        return this;
    }

    //post提交
    public HttpUtils post(){
        mType = POST_TYPE;
        return this;
    }

    //get提交
    public HttpUtils get(){
        mType = GET_TYPE;
        return this;
    }

    //添加参数
    public HttpUtils addParam(String key, Object val){
        mParams.put(key,val);
        return this;
    }
    public HttpUtils addParams(Map<String, Object> params){
        mParams.putAll(params);
        return this;
    }

    //添加回调
    public void execute(EngineCallBack callBack){
        if (callBack == null){
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }

        // 每次执行都会进入这个方法：这里添加是行不通的
        // 1.baseLibrary里面这里面不包含业务逻辑
        // 2.如果有多条业务线，
        // 让callBack回调去
        callBack.onPreExecute(mContext,mParams);

        if (mType == POST_TYPE){
            post(mContext, mUrl, mParams, callBack);
        }
        if (mType == GET_TYPE){
            get(mContext, mUrl, mParams, callBack);
        }

    }

    public void execute(){
        execute(null);
    }
    //默认使用OkHttpEngine
    private static IHttpEngine mhttpEngine = new OkHttpEngine();

    //初始化引擎
    public static void init(IHttpEngine httpEngine){
        mhttpEngine = httpEngine;
    }

    public HttpUtils exchangeEngine(IHttpEngine httpEngine){
        mhttpEngine = httpEngine;
        return this;
    }

    private void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack) {
        mhttpEngine.get(context, url, params, callBack);
    }

    private void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack) {
        mhttpEngine.post(context, url, params, callBack);
    }

    //拼接参数
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() < 0){
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")){
            sb.append("?");
        }else if (!url.endsWith("&")){
            sb.append("&");
        }

        for (Map.Entry<String, Object> entry : params.entrySet()){
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb.toString());

        return sb.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
