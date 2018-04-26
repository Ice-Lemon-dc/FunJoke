package com.dc.baselibrary.http;

/**
 * @author 43497
 * @date 2018/4/19
 */

public interface EngineCallBack {

    void onError(Exception e);

    void onSuccess(String result);

    public final EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
