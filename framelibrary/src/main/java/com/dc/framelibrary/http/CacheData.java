package com.dc.framelibrary.http;

/**
 * 缓存的实体类
 *
 * @author 43497
 * @date 2018/5/9
 */

public class CacheData {
    /**
     * 请求链接
     */
    private String mUrlKey;

    /**
     * 后台返回的Json
     */
    private String mResultJson;


    public CacheData(String urlKey, String resultJson) {
        this.mUrlKey = urlKey;
        this.mResultJson = resultJson;
    }

    public String getResultJson() {
        return mResultJson;
    }
}
