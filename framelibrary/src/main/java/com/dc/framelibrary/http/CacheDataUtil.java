package com.dc.framelibrary.http;

import android.util.Log;

import com.dc.baselibrary.util.MD5Util;
import com.dc.framelibrary.db.DaoSupportFactory;
import com.dc.framelibrary.db.IDaoSupport;

import java.util.List;

/**
 *
 * @author 43497
 * @date 2018/5/9
 */

public class CacheDataUtil {

    /**
     * 获取数据
     */
    public static String getCacheResultJson(String finalUrl) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        // 需要缓存，从数据库拿缓存，问题又来了，OkHttpEngine  BaseLibrary
        // 数据库缓存在 FrameLibrary
        List<CacheData> cacheDatas = dataDaoSupport.querySupport()
                // finalUrl http:w 报错  finalUrl -> MD5处理
                .selection("mUrlKey = ?").selectionArgs(MD5Util.string2MD5(finalUrl)).query();

        if (cacheDatas.size() != 0) {
            // 代表有数据
            CacheData cacheData = cacheDatas.get(0);
            String resultJson = cacheData.getResultJson();

            return resultJson;
        }
        return null;
    }

    /**
     * 缓存数据
     */
    public static long cacheData(String finalUrl, String resultJson) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().
                getDao(CacheData.class);
        dataDaoSupport.delete("mUrlKey=?", MD5Util.string2MD5(finalUrl));
        long number = dataDaoSupport.insert(new CacheData(MD5Util.string2MD5(finalUrl), resultJson));
        Log.e("TAG", "number --> " + number);
        return number;
    }
}