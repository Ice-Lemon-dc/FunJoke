package com.dc.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author 43497
 * @date 2018/4/19
 */

public interface EngineCallBack {

    // 开始执行，在执行之前会回掉的方法
     void onPreExecute(Context context, Map<String,Object> params);

    // 错误
     void onError(Exception e);

    // 成功  返回对象会出问题   成功  data对象{"",""}  失败  data:""
    void onSuccess(String result);


    // 默认的
    EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
