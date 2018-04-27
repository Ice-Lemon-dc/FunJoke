package com.dc.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author 43497
 * @date 2018/4/19
 */

public interface EngineCallBack {

    /**
     * 开始执行，在执行之前会回掉的方法
     * @param context
     * @param params
     */
    void onPreExecute(Context context, Map<String, Object> params);

    void onError(Exception e);

    void onSuccess(String result);

    public final EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
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
